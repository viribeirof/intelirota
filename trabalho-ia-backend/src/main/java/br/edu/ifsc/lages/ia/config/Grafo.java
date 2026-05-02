package br.edu.ifsc.lages.ia.config;

import br.edu.ifsc.lages.ia.model.Conexao;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

//classe para representar o grafo real das fronteiras, lendo as conexões do csv
@Component
public class Grafo {

    private Map<String, List<Conexao>> grafoReal = new HashMap<>();

    //metodo executado automatico para ler o csv com as conexões reais entre cidades
    @PostConstruct
    public void carregarConexoes() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/conexoes_reais.csv"), StandardCharsets.UTF_8))) {
            
            br.readLine(); 
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                String cidade1 = dados[0].trim();
                String cidade2 = dados[1].trim();
                double distancia = Double.parseDouble(dados[2].trim());

                adicionarAresta(cidade1, cidade2, distancia);
                adicionarAresta(cidade2, cidade1, distancia);
            }
            System.out.println("Grafo rodoviário real carregado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar conexões reais: " + e.getMessage());
        }
    }

    //metodo para adicionar uma aresta ao grafo, criando a lista de conexões se a cidade ainda não tiver conexões
    private void adicionarAresta(String origem, String destino, double distancia) {
        grafoReal.computeIfAbsent(origem, k -> new ArrayList<>())
                 .add(new Conexao(destino, distancia));
    }

    public Map<String, List<Conexao>> getGrafoReal() {
        return grafoReal;
    }
}