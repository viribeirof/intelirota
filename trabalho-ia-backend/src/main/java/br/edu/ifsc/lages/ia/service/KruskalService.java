package br.edu.ifsc.lages.ia.service;

import java.util.Map; 
import org.springframework.stereotype.Service;
import java.util.*;
import br.edu.ifsc.lages.ia.model.Conexao;
import br.edu.ifsc.lages.ia.model.ArestaFerrovia;

//classe para implementar o algoritmo de Kruskal para gerar a ferrovia 
@Service
public class KruskalService {
    
    //metodo para gerar a ferrovia usando o algoritmo de Kruskal, extraindo as arestas do grafo real, ordenando por custo de construção e construindo a ferrovia mínima conectando todas as cidades, retornando a ferrovia gerada e o custo total
    public Map<String, Object> gerarFerrovia(Map<String, List<Conexao>> grafoReal) {
        List<ArestaFerrovia> arestas = extrairArestas(grafoReal);
        Collections.sort(arestas);

        Map<String, String> pai = new HashMap<>();
        List<ArestaFerrovia> resultado = new ArrayList<>();
        double custoTotal = 0.0;

        //inicializa o conjunto disjunto para cada cidade, colocando cada cidade como seu próprio pai
        for (String cidade : grafoReal.keySet()) {
            pai.put(cidade, cidade);
        }

        //para cada aresta, verifica se as cidades de origem e destino pertencem a conjuntos disjuntos diferentes, adicionando a aresta ao resultado e unindo os conjuntos caso sejam, até conectar todas as cidades ou esgotar as arestas
        for (ArestaFerrovia aresta : arestas) {
            String raizOrigem = encontrar(pai, aresta.getOrigem());
            String raizDestino = encontrar(pai, aresta.getDestino());

            //se as raízes das cidades de origem e destino forem diferentes, adiciona a aresta ao resultado, atualiza o custo total e une os conjuntos disjuntos unindo as raízes
            if (!raizOrigem.equals(raizDestino)) {
                resultado.add(aresta);
                custoTotal += aresta.getCustoConstrucao();
                pai.put(raizOrigem, raizDestino);
            }
        }

        //constrói a resposta contendo a ferrovia gerada e o custo total, retornando como um mapa
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("ferrovia", resultado);
        resposta.put("custoTotal", custoTotal);
        return resposta;
    }

    //metodo recursivo para encontrar a raiz de um conjunto disjunto, utilizando compressão de caminho para otimizar futuras consultas
    private String encontrar(Map<String, String> pai, String cidade) {
        if (pai.get(cidade).equals(cidade)) {
            return cidade;
        }
        return encontrar(pai, pai.get(cidade));
    }

    //metodo para extrair as arestas do grafo real, evitando duplicatas, criando uma lista de arestas de ferrovia com o custo de construção baseado na distância e no custo por km da ferrovia
    private List<ArestaFerrovia> extrairArestas(Map<String, List<Conexao>> grafoReal) {
        List<ArestaFerrovia> arestas = new ArrayList<>();
        Set<String> visitadas = new HashSet<>();
        for (String origem : grafoReal.keySet()) {
            for (Conexao conexao : grafoReal.get(origem)) {
                String destino = conexao.getDestino();
                if (!visitadas.contains(destino + "-" + origem)) {
                    arestas.add(new ArestaFerrovia(origem, destino, conexao.getDistancia()));
                    visitadas.add(origem + "-" + destino);
                }
            }
        }
        return arestas;
    }
}
