package algoritmosC;

import java.util.*;

public class ResultadoKruskal {
    private List<Aresta> arestasDaAGM;
    private int pesoTotal;

    public ResultadoKruskal(List<Aresta> arestasDaAGM, int pesoTotal){
        this.arestasDaAGM = List.copyOf(arestasDaAGM);
        this.pesoTotal = pesoTotal;
    }

    public List<Aresta> getArestasDaAGM(){
        return Collections.unmodifiableList(arestasDaAGM);
    }

    public int getPesoTotal(){
        return pesoTotal;
    }
}
