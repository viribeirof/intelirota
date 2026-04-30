package br.edu.ifsc.lages.ia.model;

public class DemandaCarga {

    private String origem;
    private String destino;
    private int quantidadeCargas;

    public DemandaCarga(String origem, String destino, int quantidadeCargas) {
        this.origem = origem;
        this.destino = destino;
        this.quantidadeCargas = quantidadeCargas;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getQuantidadeCargas() {
        return quantidadeCargas;
    }

    public void setQuantidadeCargas(int quantidadeCargas) {
        this.quantidadeCargas = quantidadeCargas;
    }
}