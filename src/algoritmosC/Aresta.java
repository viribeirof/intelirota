package algoritmosC;

public class Aresta implements Comparable<Aresta> {
    private int origem;
    private int destino;
    private int peso;

    public Aresta(int origem, int destino, int peso){
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public int getOrigem() {
        return origem;
    }

    public int getDestino(){
        return destino;
    }

    public int getPeso(){
        return peso;
    }

    @Override
    public int compareTo(Aresta outra){
        return Integer.compare(this.peso, outra.peso);
    }

    @Override
    public String toString(){
        return "Algoritmos.Aresta: " +
                "Origem: " + origem +
                "Destino: " + destino +
                "Peso: " + peso;
    }
}
