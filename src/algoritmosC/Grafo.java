package algoritmosC;

import java.util.*;
//essa classe monta o grafo, entao tmb é importante pra kruskal
public class Grafo {
    private List<Aresta> arestas;
    private Map<String, Integer> cidadeParaIndice;
    private List<String> indiceParaCidade;
    private Map<Integer, List<Aresta>> adjacencias;

    public Grafo(){
        this.arestas = new ArrayList<>();
        this.cidadeParaIndice = new HashMap<>();
        this.indiceParaCidade = new ArrayList<>();
        this.adjacencias = new HashMap<>();
    }

    public void adicionaAresta(String origem, String destino, int peso){
        int indiceOrigem = obterOuCadastrarVertice(origem);
        int indiceDestino = obterOuCadastrarVertice(destino);

        Aresta aresta = new Aresta(indiceOrigem, indiceDestino, peso);
        arestas.add(aresta);

        adjacencias.get(indiceOrigem).add(new Aresta(indiceOrigem, indiceDestino, peso));
        adjacencias.get(indiceDestino).add(new Aresta(indiceDestino, indiceOrigem,peso));
    }

    private int obterOuCadastrarVertice(String nomeCidade){
        nomeCidade = nomeCidade.trim();

        if(cidadeParaIndice.containsKey(nomeCidade)){
            return cidadeParaIndice.get(nomeCidade);
        }

        int novoIndice = indiceParaCidade.size();
        cidadeParaIndice.put(nomeCidade, novoIndice);
        indiceParaCidade.add(nomeCidade);
        adjacencias.put(novoIndice, new ArrayList<>());

        return novoIndice;
    }

    public int getNumeroDeVertices(){
        return indiceParaCidade.size();
    }

    public List<Aresta> getArestas(){
        return Collections.unmodifiableList(arestas);
    }

    public List<Aresta> getAdjacencias(int indiceCidade) {
        return Collections.unmodifiableList(adjacencias.getOrDefault(indiceCidade, new ArrayList<>()));
    }

    public String getNomeCidade(int indice) {
        return indiceParaCidade.get(indice);
    }

    public int getIndiceCidade(String nomeCidade){
        Integer indice = cidadeParaIndice.get(nomeCidade.trim());
        if(indice == null){
            throw new IllegalArgumentException("Cidade nao encontrada: " + nomeCidade);
        }
        return indice;
    }

    public List<String> getCidades(){
        return Collections.unmodifiableList(indiceParaCidade);
    }

    public void imprimirAGM (ResultadoKruskal resultado, String cidadeRaiz){
        int raiz = getIndiceCidade(cidadeRaiz);

        Map<Integer, List<Conexao>> adjacencias = new LinkedHashMap<>();
        for (int i = 0; i < getNumeroDeVertices(); i++){
            adjacencias.put(i, new ArrayList<>());
        }

        for (Aresta aresta : resultado.getArestasDaAGM()){
            adjacencias.get(aresta.getOrigem()).add(new Conexao(aresta.getDestino(), aresta.getPeso()));
            adjacencias.get(aresta.getDestino()).add(new Conexao(aresta.getOrigem(), aresta.getPeso()));
        }

        boolean[] visitado = new boolean[getNumeroDeVertices()];
        visitado[raiz] = true;

        imprimirDFS(raiz, adjacencias, visitado, 1);
    }

    public void imprimirDFS(int atual, Map<Integer, List<Conexao>> adjacencias, boolean[] visitado, int nivel){
        for (Conexao conexao : adjacencias.get(atual)){
            if (!visitado[conexao.destino]){
                visitado[conexao.destino] = true;

                String tab = "  ".repeat(nivel);
                System.out.println(tab+ "-> " + getNomeCidade(conexao.destino) + " ("+ conexao.peso + " km)");
                imprimirDFS(conexao.destino, adjacencias, visitado, nivel + 1);
            }
        }
    }

    private static class Conexao{
        int destino;
        int peso;

        Conexao (int destino, int peso){
            this.destino = destino;
            this.peso = peso;
        }
    }

}
