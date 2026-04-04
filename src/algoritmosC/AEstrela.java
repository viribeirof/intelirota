package algoritmosC;

import java.util.*;

public class AEstrela {
    public ResultadoAEstrela encontrarCaminho(Grafo grafo, String cidadeOrigem, String cidadeDestino){
        int origem = grafo.getIndiceCidade(cidadeOrigem);
        int destino = grafo.getIndiceCidade(cidadeDestino);

        int n = grafo.getNumeroDeVertices();

        int[] gScore = new int[n];
        int[] anterior = new int[n];
        boolean[] fechado = new boolean[n];

        Arrays.fill(gScore, Integer.MAX_VALUE);
        Arrays.fill(anterior, -1);

        gScore[origem] = 0;

        PriorityQueue<Vertice> abertos = new PriorityQueue<>();
        abertos.add(new Vertice(origem, 0, heuristica(grafo, origem, destino)));

        while (!abertos.isEmpty()){
            Vertice atual = abertos.poll();
            int cidadeAtual = atual.getIndiceCidade();

            if(fechado[cidadeAtual]){
                continue;
            }

            fechado[cidadeAtual] = true;

            if(cidadeAtual == destino){
                List<Integer> caminho = reconstruirCaminho(anterior, destino);
                return new ResultadoAEstrela(caminho, gScore[destino]);
            }

            for (Aresta aresta : grafo.getAdjacencias(cidadeAtual)){
                int vizinho = aresta.getDestino();

                if(fechado[vizinho]){
                    continue;
                }

                if (gScore[cidadeAtual] == Integer.MAX_VALUE){
                    continue;
                }

                int tentativaG = gScore[cidadeAtual] + aresta.getPeso();

                if(tentativaG < gScore[vizinho]){
                    gScore[vizinho] = tentativaG;
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
