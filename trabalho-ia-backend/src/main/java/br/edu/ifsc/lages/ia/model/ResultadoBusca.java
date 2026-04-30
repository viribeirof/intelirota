package br.edu.ifsc.lages.ia.model;

import java.util.List;

//classe para representar o resultado da busca do A*
public class ResultadoBusca {
    private List<String> rota;
    private double custoTotal;
    private int estadosVisitados;
    private int estadosExpandidos;
    
    public ResultadoBusca(List<String> rota, double custoTotal, int estadosVisitados, int estadosExpandidos) {
        this.rota = rota;
        this.custoTotal = custoTotal;
        this.estadosVisitados = estadosVisitados;
        this.estadosExpandidos = estadosExpandidos;
    }

    public List<String> getRota() {
        return rota;
    }

    public double getCustoTotal() {
        return custoTotal;
    }

    public int getEstadosVisitados() {
        return estadosVisitados;
    }

    public int getEstadosExpandidos() {
        return estadosExpandidos;
    }
}