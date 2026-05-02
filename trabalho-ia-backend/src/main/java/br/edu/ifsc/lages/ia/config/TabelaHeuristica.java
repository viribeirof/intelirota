package br.edu.ifsc.lages.ia.config;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//classe para ler o csv com as heuristicas para o A* 
@Component
public class TabelaHeuristica{

    private Map<String, Map<String, Double>> matrizHeuristica = new HashMap<>();

    //metodo executado automatico para ler o csv
    @PostConstruct
    public void carregarCsv() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/heuristica.csv"), StandardCharsets.UTF_8))) {
            
            String linhaCabecalho = br.readLine();
            if (linhaCabecalho == null) return;

            String separador = linhaCabecalho.contains(";") ? ";" : ",";
            String[] destinos = linhaCabecalho.split(separador);

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(separador);
                String origem = valores[0].trim(); 

                for (int i = 1; i < valores.length; i++) {
                    if (!valores[i].trim().isEmpty()) {
                        String destino = destinos[i].trim();
                        double distancia = Double.parseDouble(valores[i].trim().replace(",", "."));
                        adicionarDistancia(origem, destino, distancia);
                    }
                }
            }
            System.out.println("Tabela de heurísticas carregada com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo CSV de heurísticas: " + e.getMessage());
        }
    }

    //metodo para adicionar a distancia entre duas cidades na matriz heuristica, criando a linha da cidade de origem se ela ainda não existir
    private void adicionarDistancia(String origem, String destino, double distancia) {
        matrizHeuristica.computeIfAbsent(origem, k -> new HashMap<>()).put(destino, distancia);
    }

    //metodo para obter a distancia heuristica entre duas cidades, retornando 0 se não houver uma distancia definida ou se as cidades forem iguais
    public double getDistanciaLinhaReta(String origem, String destino) {
        if (origem.equalsIgnoreCase(destino)) {
            return 0.0;
        }
        
        Map<String, Double> destinos = matrizHeuristica.get(origem);
        if (destinos != null && destinos.containsKey(destino)) {
            return destinos.get(destino);
        }
        return 0.0; 
    }
}