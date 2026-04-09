package br.edu.ifsc.lages.ia.trabalhoia;


public class Estado implements Comparable<Estado> {

    private final String cidade;
    private final double g;
    private final double h;
    private final double f;

    public Estado(String cidade, double g, double h) {
        this.cidade = cidade;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public String getCidade() {
        return cidade;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public double getF() {
        return f;
    }

    @Override
    public int compareTo(Estado outro) {
        return Double.compare(this.f, outro.f);
    }
}