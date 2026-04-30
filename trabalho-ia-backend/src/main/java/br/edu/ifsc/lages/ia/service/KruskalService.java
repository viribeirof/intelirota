package br.edu.ifsc.lages.ia.service;

import java.util.Map; 
import org.springframework.stereotype.Service;
import java.util.*;
import br.edu.ifsc.lages.ia.model.Conexao;
import br.edu.ifsc.lages.ia.model.ArestaFerrovia;

//classe para implementar o algoritmo de Kruskal para gerar a ferrovia 
@Service
public class KruskalService {
    
    public Map<String, Object> gerarFerrovia(Map<String, List<Conexao>> grafoReal) {
        List<ArestaFerrovia> arestas = extrairArestas(grafoReal);
        Collections.sort(arestas);

        Map<String, String> pai = new HashMap<>();
        List<ArestaFerrovia> resultado = new ArrayList<>();
        double custoTotal = 0.0;

        for (String cidade : grafoReal.keySet()) {
            pai.put(cidade, cidade);
        }

        for (ArestaFerrovia aresta : arestas) {
            String raizOrigem = encontrar(pai, aresta.getOrigem());
            String raizDestino = encontrar(pai, aresta.getDestino());

            if (!raizOrigem.equals(raizDestino)) {
                resultado.add(aresta);
                custoTotal += aresta.getCustoConstrucao();
                pai.put(raizOrigem, raizDestino);
            }
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("ferrovia", resultado);
        resposta.put("custoTotal", custoTotal);
        return resposta;
    }

    private String encontrar(Map<String, String> pai, String cidade) {
        if (pai.get(cidade).equals(cidade)) {
            return cidade;
        }
        return encontrar(pai, pai.get(cidade));
    }

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
