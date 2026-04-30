package br.edu.ifsc.lages.ia.model;

//classe para representar os estados do A*
public class Estado implements Comparable<Estado> {
    String cidade;
    double custoG; 
    double custoF; 
    Estado pai;

    public Estado(String cidade, Estado pai, double custoG, double custoF) {
        this.cidade = cidade;
        this.pai = pai;
        this.custoG = custoG;
        this.custoF = custoF;
    }

    public String getCidade() {
        return cidade;
    }

    public Estado getPai() {
        return pai;
    }

    public double getCustoG() {
        return custoG;
    }

    public double getCustoF() {
        return custoF;
    }

    @Override
    public int compareTo(Estado outro) {
        return Double.compare(this.custoF, outro.custoF);
    }
    
}
