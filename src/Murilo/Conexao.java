
package br.edu.ifsc.lages.ia.trabalhoia;

public class Conexao {
    private String destino;
    private double distancia;
    
    public Conexao(String destino, double distancia) {
        this.destino = destino;
        this.distancia = distancia;
    }

    public String getDestino() {
        return destino;
    }

    public double getDistancia() {
        return distancia;
    }
}
