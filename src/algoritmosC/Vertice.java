package algoritmosC;
//se nao me engano, essa classe só é usada pelo A*, então pode ser descartada pra krukal
public class Vertice implements Comparable<Vertice>{
    private int indiceCidade;
    private int custoReal;
    private int estimativaCusto;
    private int melhorCusto;

    public Vertice(int indiceCidade, int custoReal, int estimativaCusto){
        this.indiceCidade = indiceCidade;
        this.custoReal = custoReal;
        this.estimativaCusto = estimativaCusto;
        this.melhorCusto = custoReal + estimativaCusto;
    }

    public int getIndiceCidade(){
        return indiceCidade;
    }

    public int getCustoReal(){
        return custoReal;
    }

    public int getEstimativaCusto(){
        return estimativaCusto;
    }

    public int getMelhorCusto(){
        return melhorCusto;
    }

    @Override
    public int compareTo(Vertice outro){
        return Integer.compare(this.melhorCusto, outro.melhorCusto);
    }

}
