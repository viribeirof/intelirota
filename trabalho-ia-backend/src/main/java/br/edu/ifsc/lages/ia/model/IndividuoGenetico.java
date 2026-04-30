package br.edu.ifsc.lages.ia.model;

public class IndividuoGenetico implements Comparable<IndividuoGenetico> {

    private boolean[] genes;
    private double aptidao;
    private double custoImplantacao;
    private double custoTransporte;

    public IndividuoGenetico(int tamanhoCromossomo) {
        this.genes = new boolean[tamanhoCromossomo];
    }

    public IndividuoGenetico(boolean[] genes) {
        this.genes = genes.clone();
    }

    public boolean[] getGenes() {
        return genes;
    }

    public void setGenes(boolean[] genes) {
        this.genes = genes;
    }

    public boolean getGene(int indice) {
        return genes[indice];
    }

    public void setGene(int indice, boolean valor) {
        genes[indice] = valor;
    }

    public int getTamanhoCromossomo() {
        return genes.length;
    }

    public double getAptidao() {
        return aptidao;
    }

    public void setAptidao(double aptidao) {
        this.aptidao = aptidao;
    }

    public double getCustoImplantacao() {
        return custoImplantacao;
    }

    public void setCustoImplantacao(double custoImplantacao) {
        this.custoImplantacao = custoImplantacao;
    }

    public double getCustoTransporte() {
        return custoTransporte;
    }

    public void setCustoTransporte(double custoTransporte) {
        this.custoTransporte = custoTransporte;
    }

    @Override
    public int compareTo(IndividuoGenetico outro) {
        return Double.compare(this.aptidao, outro.aptidao);
    }
}