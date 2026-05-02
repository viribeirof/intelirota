package br.edu.ifsc.lages.ia.service;

import br.edu.ifsc.lages.ia.config.TabelaHeuristica;
import br.edu.ifsc.lages.ia.model.Conexao;
import br.edu.ifsc.lages.ia.model.ResultadoBusca;
import br.edu.ifsc.lages.ia.model.Estado;
import br.edu.ifsc.lages.ia.model.EstadoHibrido;
import br.edu.ifsc.lages.ia.model.ArestaFerrovia;
import org.springframework.stereotype.Service;
import java.util.*;

//classe para implementar o algoritmo A* para busca de rotas rodoviarias e hibridas
@Service
public class AService {
    
    private final double custoKm_rodovia = 5.0;
    private final double custoKm_ferrovia = 1.2;
    private final double taxoTransbordo = 1000.0;
    private final TabelaHeuristica tabelaHeuristica;
    private final String Ferrovia = "Ferrovia";
    private final String Rodovia = "Rodovia";
    private final String Inicio = "Início";

    public AService(TabelaHeuristica tabelaHeuristica){
        this.tabelaHeuristica = tabelaHeuristica;
    }

    //metodo para buscar a rota rodoviaria usando o A*
    public ResultadoBusca buscar(String origem, String destino, Map<String, List<Conexao>> grafoReal) {
        PriorityQueue<Estado> fronteira = new PriorityQueue<>();
        Set<String> visitados = new HashSet<>();
        int estadosVisitados = 0;
        int estadosExpandidos = 0;

        double heuristicaInicial = tabelaHeuristica.getDistanciaLinhaReta(origem, destino) * custoKm_rodovia;
        Estado estadoInicial = new Estado(origem, null, 0.0, heuristicaInicial);
        fronteira.add(estadoInicial);

        //enquanto houver estados na fronteira, expande o estado com menor custo f, verificando se é o destino e expandindo seus vizinhos caso contrário
        while (!fronteira.isEmpty()) {
            Estado estadoAtual = fronteira.poll();
            estadosVisitados++;

            if (estadoAtual.getCidade().equalsIgnoreCase(destino)) {
                return construirResultado(estadoAtual, estadosVisitados, estadosExpandidos);
            }

            if (visitados.contains(estadoAtual.getCidade())) {
                continue;
            }
            visitados.add(estadoAtual.getCidade());
            estadosExpandidos++;

            List<Conexao> vizinhos = grafoReal.getOrDefault(estadoAtual.getCidade(), new ArrayList<>());
            //para cada vizinho, calcula o custo g, a heuristica e o custo f, criando um novo estado para o vizinho e adicionando à fronteira
            for (Conexao conexao : vizinhos) {
                if (!visitados.contains(conexao.getDestino())){

                    double custoG = estadoAtual.getCustoG() + conexao.getDistancia() * custoKm_rodovia;
                    double heuristica = tabelaHeuristica.getDistanciaLinhaReta(conexao.getDestino(), destino) * custoKm_rodovia;
                    double custoF = custoG + heuristica;

                    Estado novoEstado = new Estado(conexao.getDestino(), estadoAtual, custoG, custoF);
                    fronteira.add(novoEstado);
                }
            }
        }
        return null;
    }

    //metodo para construir o resultado da busca rodoviaria
    private ResultadoBusca construirResultado(Estado estadoDestino, int estadosVisitados, int estadosExpandidos) {
        List<String> caminho = new ArrayList<>();
        Estado estadoAtual = estadoDestino;
        double custoTotal = estadoDestino.getCustoG();

        //constrói o caminho percorrendo os estados pais desde o destino até a origem, invertendo a ordem para apresentar do início ao fim
        while (estadoAtual != null) {
            caminho.add(estadoAtual.getCidade());
            estadoAtual = estadoAtual.getPai();
        }
        Collections.reverse(caminho);
        return new ResultadoBusca(caminho, custoTotal, estadosVisitados, estadosExpandidos);
    }

    //metodo para buscar a rota hibrida usando o A* e considerando as ferrovias geradas pelo Kruskal
    public ResultadoBusca buscarRotaComFerrovia(String origem, String destino, Map<String, List<Conexao>> grafoReal, List<ArestaFerrovia> ferrovia) {
        Set<String> cidadesFerrovia = new HashSet<>();
        for (ArestaFerrovia aresta : ferrovia) {
            cidadesFerrovia.add(aresta.getOrigem() + "-" + aresta.getDestino());
            cidadesFerrovia.add(aresta.getDestino() + "-" + aresta.getOrigem());
        }
        
        PriorityQueue<EstadoHibrido> fronteira = new PriorityQueue<>();
        Map<String, Double> menorCusto = new HashMap<>();
        int estadosExpandidos = 0;

        double heuristicaInicial = tabelaHeuristica.getDistanciaLinhaReta(origem, destino) * custoKm_ferrovia;
        EstadoHibrido estadoInicial = new EstadoHibrido(origem, null, 0.0, heuristicaInicial, Inicio);
        fronteira.add(estadoInicial);
        menorCusto.put(origem + "-" + Inicio, 0.0);

        //enquanto houver estados na fronteira, expande o estado com menor custo f, verificando se é o destino e expandindo seus vizinhos caso contrário, considerando os modos de transporte rodovia e ferrovia
        while(!fronteira.isEmpty()) {
            EstadoHibrido estadoAtual = fronteira.poll();
            estadosExpandidos++;

            //verifica se o estado atual é o destino, construindo o resultado da busca hibrida caso seja, ou expandindo seus vizinhos caso contrário
            if (estadoAtual.getCidade().equalsIgnoreCase(destino)) {
                return construirResultadoHibrido(estadoAtual, menorCusto.size(), estadosExpandidos);
            }

            List<Conexao> vizinhos = grafoReal.getOrDefault(estadoAtual.getCidade(), new ArrayList<>());
            //para cada vizinho, calcula o custo g, a heuristica e o custo f para os modos de transporte rodovia e ferrovia, criando novos estados para o vizinho e adicionando à fronteira, considerando o custo de transbordo caso haja troca de modo de transporte
            for (Conexao conexao : vizinhos) {
                String vizinho = conexao.getDestino();
                double distancia = conexao.getDistancia();
                double heuristica = tabelaHeuristica.getDistanciaLinhaReta(conexao.getDestino(), destino) * custoKm_ferrovia;
                double custoG_Rodovia = estadoAtual.getCustoG() + (distancia * custoKm_rodovia);
                
                if(estadoAtual.getModoTransporte().equals(Ferrovia)) {
                    custoG_Rodovia += taxoTransbordo;
                }
                processarVizinho(fronteira, menorCusto, estadoAtual, vizinho, custoG_Rodovia, heuristica, Rodovia);

                if(cidadesFerrovia.contains(estadoAtual.getCidade() + "-" + vizinho)) {
                    double custoG_Ferrovia = estadoAtual.getCustoG() + (distancia * custoKm_ferrovia);
                    if(estadoAtual.getModoTransporte().equals(Rodovia)) {
                        custoG_Ferrovia += taxoTransbordo;
                    }
                    processarVizinho(fronteira, menorCusto, estadoAtual, vizinho, custoG_Ferrovia, heuristica, Ferrovia);
                }
            }
        }
         return null;
    }

    //metodo para processar os vizinhos durante a busca hibrida, atualizando a fronteira e o menor custo
    public void processarVizinho(PriorityQueue<EstadoHibrido> fronteira, Map<String, Double> menorCusto, EstadoHibrido estadoAtual, String vizinho, double custoG, double heuristica, String modoTransporte) {
        String chave = vizinho + "-" + modoTransporte;
        //verifica se o custo g para chegar ao vizinho é menor do que o menor custo registrado para esse vizinho e modo de transporte, atualizando a fronteira e o menor custo caso seja
        if (!menorCusto.containsKey(chave) || custoG < menorCusto.get(chave)) {
            menorCusto.put(chave, custoG);
            double custoF = custoG + heuristica;
            EstadoHibrido novoEstado = new EstadoHibrido(vizinho, estadoAtual, custoG, custoF, modoTransporte);
            fronteira.add(novoEstado);
        }
    }

    //metodo para construir o resultado da busca hibrida, incluindo o modo de transporte utilizado em cada etapa
    public ResultadoBusca construirResultadoHibrido(EstadoHibrido estadoDestino, int estadosVisitados, int estadosExpandidos) {
        List<String> caminho = new ArrayList<>();
        EstadoHibrido estadoAtual = estadoDestino;
        double custoTotal = estadoDestino.getCustoG();

        //constrói o caminho percorrendo os estados pais desde o destino até a origem, incluindo o modo de transporte utilizado em cada etapa e invertendo a ordem para apresentar do início ao fim
        while (estadoAtual != null) {
            String transporte = estadoAtual.getModoTransporte().equals(Inicio) ? "Início" : estadoAtual.getModoTransporte();
            caminho.add(estadoAtual.getCidade() + " (" + transporte + ")");
            estadoAtual = estadoAtual.getPai();
        }
        Collections.reverse(caminho);
        return new ResultadoBusca(caminho, custoTotal, estadosVisitados, estadosExpandidos);
    }
}