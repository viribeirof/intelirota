package br.edu.ifsc.lages.ia.model;

//classe para representar as arestas de ferrovia entre cidades
public class ArestaFerrovia implements Comparable<ArestaFerrovia> {
    private String origem;
    private String destino;
    private double distancia;
    private double custoConstrucao;

    public ArestaFerrovia(String origem, String destino, double distancia) {
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
        this.custoConstrucao = distancia * 2000000.0;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public double getCustoConstrucao() {
        return custoConstrucao;
    }

    //metodo para ordenar as arestas de ferrovia pelo custo 
    @Override
    public int compareTo(ArestaFerrovia outra) {
        return Double.compare(this.custoConstrucao, outra.custoConstrucao);
    }

    @Override
    public String toString(){
        return origem + " - " + destino + " (Distancia: " + distancia + " km";
    }
    
}
