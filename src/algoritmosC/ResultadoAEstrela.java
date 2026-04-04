package algoritmosC;

import java.util.*;

public class ResultadoAEstrela {
    private List<Integer> caminho;
    private int custoTotal;

    public ResultadoAEstrela(List<Integer> caminho, int custoTotal){
        this.caminho = List.copyOf(caminho);
        this.custoTotal = custoTotal;
    }

    public List<Integer> getCaminho(){
        return Collections.unmodifiableList(caminho);
    }

    public int getCustoTotal() {
        return custoTotal;
    }
}
