package br.edu.ifsc.lages.ia.model;

//classe para representar os estados do A* hibrido, incluindo o modo de transporte utilizado
public class EstadoHibrido implements Comparable<EstadoHibrido> {
    String cidade;
    double custoG; 
    double custoF; 
    EstadoHibrido pai;
    String modoTransporte;

    public EstadoHibrido(String cidade, EstadoHibrido pai, double custoG, double custoF, String modoTransporte) {
        this.cidade = cidade;
        this.pai = pai;
        this.custoG = custoG;
        this.custoF = custoF;
        this.modoTransporte = modoTransporte;
    }

    public String getCidade() {
        return cidade;
    }

    public EstadoHibrido getPai() {
        return pai;
    }

    public double getCustoG() {
        return custoG;
    }

    public double getCustoF() {
        return custoF;
    }

    public String getModoTransporte() {
        return modoTransporte;
    }

    @Override
    public int compareTo(EstadoHibrido outro) {
        return Double.compare(this.custoF, outro.custoF);
    }
    
}
