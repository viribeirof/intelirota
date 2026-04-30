package br.edu.ifsc.lages.ia.config;
import br.edu.ifsc.lages.ia.model.DemandaCarga;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//classe responsável por carregar as cargas do arquivo CSV
@Component
public class CargasConfig {
    private List<DemandaCarga> cargasAnexo = new ArrayList<>();

    @PostConstruct
    public void carregarCargas() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/cargas_anexo.csv"), StandardCharsets.UTF_8))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] dados = line.split(",");
                if (dados.length == 3) {
                    String origem = dados[0].trim();
                    String destino = dados[1].trim();
                    int carga = Integer.parseInt(dados[2].trim());
                    cargasAnexo.add(new DemandaCarga(origem, destino, carga));
                }
            }
            System.out.println("Cargas carregadas com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar cargas: " + e.getMessage());
        }
    }

    public List<DemandaCarga> getCargas() {
        return cargasAnexo;
    }
}