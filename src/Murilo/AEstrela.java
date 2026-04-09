
package br.edu.ifsc.lages.ia.trabalhoia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class AEstrela {
    
    private final Grafo grafo;
    private final Map<String, Map<String, Integer>> heuristicas;
    
    
    public AEstrela(Grafo grafo, Map<String, Map<String, Integer>> heuristicas) {
    
        this.grafo = grafo;
        this.heuristicas = heuristicas;
    }
    

    public ResultadoBusca buscar(String origem, String destino) {
        PriorityQueue<Estado> fronteira = new PriorityQueue<>();
        Set<String> visitados = new HashSet<>();
        Map<String, Double> custos = new HashMap<>();
        Map<String, String> pais = new HashMap<>();
        
        custos.put(origem, 0.0);
        
        int hInicial = heuristicas.get(origem).get(destino);
        fronteira.add(new Estado (origem, 0, hInicial));
        int estadosExpandidos = 0;
        while(!fronteira.isEmpty()){
            Estado atual =fronteira.poll();
            if(visitados.contains(atual.getCidade())){
                continue;
            }
            visitados.add(atual.getCidade());
            estadosExpandidos++;
            if(atual.getCidade().equals(destino)){
                List<String> caminho = reconstruirCaminho(pais, destino);
                return new ResultadoBusca(caminho, atual.getG(), visitados.size(), estadosExpandidos);
            }
            
            for (Conexao conexao : grafo.getConexoes(atual.getCidade())) {

                String vizinho = conexao.getDestino();

                double novoG = atual.getG() + conexao.getDistancia();

                if (!custos.containsKey(vizinho) || novoG < custos.get(vizinho)) {

                    custos.put(vizinho, novoG);

                    pais.put(vizinho, atual.getCidade());

                    int h = heuristicas.get(vizinho).get(destino);

                    fronteira.add(new Estado(vizinho, novoG, h));
                }
        }
      }
        return null;
    }
    
        private List<String> reconstruirCaminho(Map<String, String> pais, String destino) {

        List<String> caminho = new ArrayList<>();
        String atual = destino;

        while (atual != null) {
            caminho.add(atual);
            atual = pais.get(atual);
        }
        Collections.reverse(caminho);

        return caminho;
    }
   
}


