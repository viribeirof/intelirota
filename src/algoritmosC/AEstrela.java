package algoritmosC;

import java.util.*;
//pode ser descartada
public class AEstrela {
    public ResultadoAEstrela encontrarCaminho(Grafo grafo, String cidadeOrigem, String cidadeDestino){
        int origem = grafo.getIndiceCidade(cidadeOrigem);
        int destino = grafo.getIndiceCidade(cidadeDestino);

        int n = grafo.getNumeroDeVertices();

        int[] custoAcumulado = new int[n];
        int[] anterior = new int[n];
        boolean[] noVisitado = new boolean[n];

        Arrays.fill(custoAcumulado, Integer.MAX_VALUE);
        Arrays.fill(anterior, -1);

        custoAcumulado[origem] = 0;

        //pega o menor custo
        PriorityQueue<Vertice> abertos = new PriorityQueue<>();
        abertos.add(new Vertice(origem, 0, heuristica(grafo, origem, destino)));

        while (!abertos.isEmpty()){
            Vertice atual = abertos.poll();
            int cidadeAtual = atual.getIndiceCidade();

            if(noVisitado[cidadeAtual]){
                continue;
            }

            noVisitado[cidadeAtual] = true;

            if(cidadeAtual == destino){
                List<Integer> caminho = reconstruirCaminho(anterior, destino);
                return new ResultadoAEstrela(caminho, custoAcumulado[destino]);
            }

            for (Aresta aresta : grafo.getAdjacencias(cidadeAtual)){
                int vizinho = aresta.getDestino();

                if(noVisitado[vizinho]){
                    continue;
                }

                if (custoAcumulado[cidadeAtual] == Integer.MAX_VALUE){
                    continue;
                }

                int tentativaG = custoAcumulado[cidadeAtual] + aresta.getPeso();

                if(tentativaG < custoAcumulado[vizinho]){
                    custoAcumulado[vizinho] = tentativaG;
                    anterior[vizinho] = cidadeAtual;

                    int h = heuristica(grafo, vizinho, destino);
                    abertos.add(new Vertice(vizinho, tentativaG, h));
                }
            }
        }
        throw new IllegalArgumentException("Nao existe caminho entre "+ cidadeOrigem + " e " + cidadeDestino);
    }

    private List<Integer> reconstruirCaminho(int[] anterior, int destino){
        List<Integer> caminho = new ArrayList<>();
        int atual = destino;

        while (atual != -1){
            caminho.add(atual);
            atual = anterior[atual];
        }

        Collections.reverse(caminho);
        return caminho;
    }

    private int heuristica(Grafo grafo, int atual, int destino){
        return 0;
    }
}
