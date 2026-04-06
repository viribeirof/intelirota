package algoritmosC;
//aqui um exemplo de tudo funcionando junto
public class Main {
    static void main() {
        Grafo grafo = new Grafo();

        grafo.adicionaAresta("Recife", "Joao Pessoa", 116);
        grafo.adicionaAresta("Joao Pessoa", "Natal", 181);
        grafo.adicionaAresta("Goiania", "Brasilia", 207);
        grafo.adicionaAresta("Maceio", "Recife", 257);
        grafo.adicionaAresta("Aracaju", "Maceio", 271);
        grafo.adicionaAresta("Florianopolis", "Curitiba", 307);
        grafo.adicionaAresta("Salvador", "Aracaju", 323);
        grafo.adicionaAresta("Curitiba", "Sao Paulo", 416);
        grafo.adicionaAresta("Sao Paulo", "Rio de Janeiro", 435);
        grafo.adicionaAresta("Teresina", "Sao Luis", 436);
        grafo.adicionaAresta("Rio de Janeiro", "Belo Horizonte", 442);
        grafo.adicionaAresta("Porto Alegre", "Florianopolis", 463);
        grafo.adicionaAresta("Porto Velho", "Rio Branco", 509);
        grafo.adicionaAresta("Belo Horizonte", "Vitoria", 514);
        grafo.adicionaAresta("Rio de Janeiro", "Vitoria", 517);
        grafo.adicionaAresta("Fortaleza", "Natal", 524);
        grafo.adicionaAresta("Belem", "Macapa", 527);
        grafo.adicionaAresta("Belem", "Sao Luis", 576);
        grafo.adicionaAresta("Salvador", "Maceio", 579);
        grafo.adicionaAresta("Sao Paulo", "Belo Horizonte", 592);
        grafo.adicionaAresta("Teresina", "Fortaleza", 603);
        grafo.adicionaAresta("Joao Pessoa", "Fortaleza", 673);
        grafo.adicionaAresta("Campo Grande", "Cuiaba", 703);
        grafo.adicionaAresta("Manaus", "Boa Vista", 747);
        grafo.adicionaAresta("Recife", "Fortaleza", 777);
        grafo.adicionaAresta("Salvador", "Recife", 806);
        grafo.adicionaAresta("Goiania", "Palmas", 824);
        grafo.adicionaAresta("Campo Grande", "Goiania", 839);
        grafo.adicionaAresta("Belo Horizonte", "Goiania", 887);
        grafo.adicionaAresta("Goiania", "Cuiaba", 887);
        grafo.adicionaAresta("Manaus", "Porto Velho", 889);
        grafo.adicionaAresta("Sao Paulo", "Campo Grande", 981);
        grafo.adicionaAresta("Palmas", "Teresina", 1107);
        grafo.adicionaAresta("Recife", "Teresina", 1127);
        grafo.adicionaAresta("Salvador", "Teresina", 1153);
        grafo.adicionaAresta("Vitoria", "Salvador", 1179);
        grafo.adicionaAresta("Palmas", "Belem", 1207);
        grafo.adicionaAresta("Palmas", "Sao Luis", 1250);
        grafo.adicionaAresta("Manaus", "Rio Branco", 1395);
        grafo.adicionaAresta("Belo Horizonte", "Salvador", 1429);
        grafo.adicionaAresta("Cuiaba", "Porto Velho", 1461);
        grafo.adicionaAresta("Salvador", "Palmas", 1475);
        grafo.adicionaAresta("Cuiaba", "Palmas", 1487);
        grafo.adicionaAresta("Goiania", "Salvador", 1646);
        grafo.adicionaAresta("Cuiaba", "Manaus", 2349);
        grafo.adicionaAresta("Cuiaba", "Belem", 2629);
        grafo.adicionaAresta("Belem", "Boa Vista", 2805);
        grafo.adicionaAresta("Belem", "Manaus", 3048);

        Kruskal kruskal = new Kruskal();
        ResultadoKruskal resultadoKruskal = kruskal.encontraAGM(grafo);

        System.out.println("=====AGM em forma de Arvore=====");
        grafo.imprimirAGM(resultadoKruskal, "Goiania");
        System.out.println();
        System.out.println("Peso total da AGM: "+ resultadoKruskal.getPesoTotal() + " km.");

        System.out.println();
        System.out.println("=====Melhor Rota=====");

        AEstrela estrela = new AEstrela();
        ResultadoAEstrela rota = estrela.encontrarCaminho(grafo, "Goiania", "Natal");

        for (int indiceCidade : rota.getCaminho()){
            System.out.println(grafo.getNomeCidade(indiceCidade));
        }
        System.out.println();
        System.out.println("Custo total da rota: " + rota.getCustoTotal() + " km.");


    }
}
