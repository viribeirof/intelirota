package br.edu.ifsc.lages.ia.controller;

import br.edu.ifsc.lages.ia.config.Grafo;
import br.edu.ifsc.lages.ia.config.CargasConfig;
import br.edu.ifsc.lages.ia.model.ArestaFerrovia;
import br.edu.ifsc.lages.ia.model.DemandaCarga;
import br.edu.ifsc.lages.ia.model.ResultadoBusca;
import br.edu.ifsc.lages.ia.model.Conexao;
import br.edu.ifsc.lages.ia.service.AService;
import br.edu.ifsc.lages.ia.service.KruskalService;
import br.edu.ifsc.lages.ia.service.GeneticoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")

public class RotaController {
    private final AService aService;
    private final KruskalService kruskalService;
    private final Grafo grafo;
    private final CargasConfig cargas;
    private final GeneticoService geneticoService;

    public RotaController(
        AService aService,
        KruskalService kruskalService,
        GeneticoService geneticoService,
        Grafo grafo,
        CargasConfig cargas) {
        this.aService = aService;
        this.kruskalService = kruskalService;
        this.grafo = grafo;
        this.cargas = cargas;
        this.geneticoService = geneticoService;
    }

    //endpoint para buscar a rota rodoviaria usando o algoritmo A*
    @GetMapping("/rota/rodoviaria")
    public ResponseEntity<ResultadoBusca> buscarRotaRodoviaria(
        @RequestParam String origem,
        @RequestParam String destino) {
        
        ResultadoBusca resultado = aService.buscar(origem, destino, grafo.getGrafoReal());
        
        if(resultado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    //endpoint para gerar a ferrovia usando o algoritmo de Kruskal
    @GetMapping("/ferrovia/kruskal")
    public ResponseEntity<Map<String, Object>> gerarFerroviaKruskal() {
        Map<String, Object> resultado = kruskalService.gerarFerrovia(grafo.getGrafoReal());
        return ResponseEntity.ok(resultado);
    }

    //endpoint para buscar a rota híbrida usando o algoritmo de Kruskal para gerar a ferrovia e o algoritmo A* para buscar a rota
    @GetMapping("/rota/hibrida/kruskal")
    public ResponseEntity<ResultadoBusca> buscarRotaHibridaKruskal(
            @RequestParam String origem,
            @RequestParam String destino) {
        
        Map<String, Object> respostaKruskal = kruskalService.gerarFerrovia(grafo.getGrafoReal());
        
        @SuppressWarnings("unchecked")
        List<ArestaFerrovia> ferrovias = (List<ArestaFerrovia>) respostaKruskal.get("ferrovia");

        ResultadoBusca resultado = aService.buscarRotaComFerrovia(origem, destino, grafo.getGrafoReal(), ferrovias);
        
        if(resultado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    //endpoint para gerar a ferrovia usando o algoritmo genético, considerando um orçamento máximo de 60% do custo total da ferrovia gerada pelo algoritmo de Kruskal
    @GetMapping("/ferrovia/genetico")
    public ResponseEntity<Map<String, Object>> gerarFerroviaGenetico() {
        Map<String, Object> respostaKruskal = kruskalService.gerarFerrovia(grafo.getGrafoReal());

        double custoKruskal = (double) respostaKruskal.get("custoTotal");
        double orcamentoMaximo = custoKruskal * 0.60;

        List<ArestaFerrovia> arestasCandidatas = extrairArestasCandidatas(grafo.getGrafoReal());
        List<DemandaCarga> demandas = converterCargasParaDemandas(cargas.getCargas());

        Map<String, Object> resultado = geneticoService.otimizar(
                grafo.getGrafoReal(),
                arestasCandidatas,
                demandas,
                orcamentoMaximo
        );

        resultado.put("custoKruskal", custoKruskal);

        return ResponseEntity.ok(resultado);
    }

    //endpoint para buscar a rota híbrida usando o algoritmo genético para gerar a ferrovia e o algoritmo A* para buscar a rota
    @GetMapping("/rota/hibrida/genetico")
    public ResponseEntity<ResultadoBusca> buscarRotaHibridaGenetico(
            @RequestParam String origem,
            @RequestParam String destino
    ) {
        Map<String, Object> respostaKruskal = kruskalService.gerarFerrovia(grafo.getGrafoReal());
        double custoKruskal = (double) respostaKruskal.get("custoTotal");
        double orcamentoMax = custoKruskal * 0.60;  

        List<ArestaFerrovia> arestasCandidatas = extrairArestasCandidatas(grafo.getGrafoReal());
        List<DemandaCarga> demandas = converterCargasParaDemandas(cargas.getCargas());

        Map<String, Object> respostaGenetico = geneticoService.otimizar(grafo.getGrafoReal(), arestasCandidatas, demandas, orcamentoMax);

        @SuppressWarnings("unchecked")
        List<ArestaFerrovia> ferrovias = (List<ArestaFerrovia>) respostaGenetico.get("ferrovia");

        ResultadoBusca resultado = aService.buscarRotaComFerrovia(origem, destino, grafo.getGrafoReal(), ferrovias);

        if(resultado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    //endpoint para listar as cidades disponíveis no grafo
    @GetMapping("/cidades")
    public ResponseEntity<List<String>> listarCidades(){
        List<String> cidades = new ArrayList<>(grafo.getGrafoReal().keySet());
        Collections.sort(cidades);
        return ResponseEntity.ok(cidades);
    }

    private List<ArestaFerrovia> extrairArestasCandidatas(Map<String, List<Conexao>> grafoReal){
        List<ArestaFerrovia> arestas = new ArrayList<>();
        Set<String> visitadas = new HashSet<>();

        //percorre o grafo real e extrai as conexões para criar as arestas candidatas, evitando duplicatas
        for (String origem : grafoReal.keySet()){
            for (Conexao conexao : grafoReal.get(origem)){
                String destino = conexao.getDestino();

                String chave1 = origem + "-" + destino;
                String chave2 = destino + "-" + origem;

                if(!visitadas.contains(chave1) && !visitadas.contains(chave2)){
                    arestas.add(new ArestaFerrovia(origem, destino, conexao.getDistancia()));
                    visitadas.add(chave1);
                }
            }
        }
        return arestas;
    }

    //converte as cargas para demandas, considerando a quantidade de cargas como a demanda
    private List<DemandaCarga> converterCargasParaDemandas(List<DemandaCarga> rotasCarga) {
        List<DemandaCarga> demandas = new ArrayList<>();

        for (DemandaCarga rota : rotasCarga) {
            demandas.add(new DemandaCarga(
                    rota.getOrigem(),
                    rota.getDestino(),
                    (int) rota.getQuantidadeCargas()
            ));
        }

        return demandas;
    }

}