package algoritmosC;
//essa clase é importante pra kruskal
public class ConjuntoDisjunto {
    private int[] pai;
    private int[] rank;

    public ConjuntoDisjunto(int tamanho){
        //aqui vao ser criados os conjuntos
        if (tamanho <= 0){
            throw new IllegalArgumentException("O tamanho deve ser maior que 0");
        }

        pai = new int[tamanho];
        rank = new int[tamanho];

        for (int i = 0; i < tamanho; i++){
            pai[i] = i;
            rank[i] = 0;
        }
    }

    public int encontrarNo (int no) {
        //esse método acha a raiz do conjunto
        if(pai[no] != no){
            pai[no] = encontrarNo(pai[no]);
        }
        return pai[no];
    }

    public boolean unir (int no1, int no2){
        /*esse metodo é aquele que identifica se os vertices estão no mesmo nó pra montar
        * um grupo grandao, nesse casso as rotas da ferrovia*/
        int raiz1 = encontrarNo(no1);
        int raiz2 = encontrarNo(no2);

        if(raiz1 == raiz2){
            return false;
        }

        if(rank[raiz1] < rank[raiz2]){
            /*aqui é aquela comparação pra ver qual conjunto é menor pra
            alimentar o outro ou ser alimentado*/
            pai[raiz1] = raiz2;
        } else if (rank[raiz1] > rank[raiz2]){
            pai[raiz2] = raiz1;
        } else {
            pai[raiz2] = raiz1;
            rank[raiz1] ++;
        }

        return true;
    }
}
