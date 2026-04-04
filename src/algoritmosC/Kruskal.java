package algoritmosC;

import java.util.*;

public class Kruskal {
    public ResultadoKruskal encontraAGM(Grafo grafo){
        List<Aresta> arestasOrdenadas = new ArrayList<>(grafo.getArestas());
        Collections.sort(arestasOrdenadas);

        ConjuntoDisjunto conjuntoDisjunto = new ConjuntoDisjunto(grafo.getNumeroDeVertices());
        List<Aresta> arestasSelecionadas = new ArrayList<>();
        int pesoTotal = 0;

        for (Aresta aresta : arestasOrdenadas){
            boolean uniu = conjuntoDisjunto.unir(aresta.getOrigem(), aresta.getDestino());
            if (uniu){
                arestasSelecionadas.add(aresta);
                pesoTotal += aresta.getPeso();
            }
        }
        if (arestasSelecionadas.size() != grafo.getNumeroDeVertices() - 1){
            throw new IllegalArgumentException("O grafo nao eh conexo entao nao existe AGM");
        }

        return new ResultadoKruskal(arestasSelecionadas, pesoTotal);
    }
}
