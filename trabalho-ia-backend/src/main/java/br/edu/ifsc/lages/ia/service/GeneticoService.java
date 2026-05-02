package br.edu.ifsc.lages.ia.service;

import br.edu.ifsc.lages.ia.model.ArestaFerrovia;
import br.edu.ifsc.lages.ia.model.Conexao;
import br.edu.ifsc.lages.ia.model.DemandaCarga;
import br.edu.ifsc.lages.ia.model.IndividuoGenetico;
import br.edu.ifsc.lages.ia.model.ResultadoBusca;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class GeneticoService {

    private final AService aService;
    private int tamanhoPopulacao = 20;
    private int geracoes = 30;
    private double taxaMutacao = 0.05;
    private Random random = new Random();

    public GeneticoService(AService aService) {
        this.aService = aService;
    }

    //metodo para otimizar a ferrovia usando um algoritmo genético, onde cada indivíduo representa uma combinação de arestas candidatas, avaliando a aptidão com base no custo total de implantação e transporte, e evoluindo a população por meio de seleção, cruzamento e mutação para encontrar a melhor solução dentro do orçamento máximo
    public Map<String, Object> otimizar(
            Map<String, List<Conexao>> grafoReal,
            List<ArestaFerrovia> arestasCandidatas,
            List<DemandaCarga> demandas,
            double orcamentoMaximo) {
        
        int tamanhoCromossomo = arestasCandidatas.size();
        List<IndividuoGenetico> populacao = new ArrayList<>();

        //inicializa a população com indivíduos aleatórios, onde cada gene representa a inclusão ou exclusão de uma aresta candidata na ferrovia, com uma probabilidade de 30% de incluir cada aresta
        for (int i = 0; i < tamanhoPopulacao; i++) {
            IndividuoGenetico individuo = new IndividuoGenetico(tamanhoCromossomo);
            for (int j = 0; j < tamanhoCromossomo; j++) {
                individuo.setGene(j, random.nextDouble() < 0.3);
            }
            populacao.add(individuo);
        }

        IndividuoGenetico melhorIndividuo = null;

        //para cada geração, avalia a aptidão de cada indivíduo na população, ordena por aptidão e mantém o melhor indivíduo encontrado, gerando uma nova população por meio de seleção, cruzamento e mutação, até atingir o número de gerações definido, garantindo diversidade genética e explorando novas soluções
        for (int geracao = 0; geracao < geracoes; geracao++) {
            for (IndividuoGenetico individuo : populacao) {
                avaliarAptidao(individuo, arestasCandidatas, demandas, grafoReal, orcamentoMaximo);
            }

            Collections.sort(populacao);

            //mantém o melhor indivíduo da geração atual para garantir que a melhor solução encontrada seja preservada ao longo das gerações
            if (melhorIndividuo == null || populacao.get(0).getAptidao() < melhorIndividuo.getAptidao()) {
                melhorIndividuo = copiarIndividuo(populacao.get(0));
            }

            List<IndividuoGenetico> novaPopulacao = new ArrayList<>();
            novaPopulacao.add(copiarIndividuo(melhorIndividuo));

            //gera novos indivíduos para a nova população por meio de seleção, cruzamento e mutação, até atingir o tamanho da população definido, garantindo diversidade genética e explorando novas soluções
            while (novaPopulacao.size() < tamanhoPopulacao) {
                IndividuoGenetico pai1 = selecionarIndividuo(populacao, random);
                IndividuoGenetico pai2 = selecionarIndividuo(populacao, random);
                IndividuoGenetico filho = cruzar(pai1, pai2, random);
                mutacao(filho, random);

                novaPopulacao.add(filho);
            }

            populacao = novaPopulacao;

            System.out.println("Melhor aptidão: " + melhorIndividuo.getAptidao());
        }

        List<ArestaFerrovia> ferroviaOtima = construirListaFerrovia(melhorIndividuo, arestasCandidatas);

        //constrói o resultado contendo a ferrovia gerada, o custo de implantação, o orçamento máximo, o custo de transporte e a aptidão da solução, retornando como um mapa
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("ferrovia", ferroviaOtima);
        resultado.put("custoImplantacao", melhorIndividuo.getCustoImplantacao());
        resultado.put("orcamentoMaximo", orcamentoMaximo);
        resultado.put("custoTransporte", melhorIndividuo.getCustoTransporte());
        resultado.put("aptidao", melhorIndividuo.getAptidao());
        return resultado;
    }

    //metodo para avaliar a aptidão de um indivíduo, construindo a ferrovia correspondente, calculando o custo de implantação e transporte, e definindo a aptidão com base no custo total de transporte, penalizando soluções inviáveis que excedam o orçamento máximo
    public void avaliarAptidao(
        IndividuoGenetico individuo,
        List<ArestaFerrovia> arestasCandidatas,
        List<DemandaCarga> demandas,
        Map<String, List<Conexao>> grafoReal,
        double orcamentoMaximo
    ){
        List<ArestaFerrovia> ferrovia = construirListaFerrovia(individuo, arestasCandidatas);

        double custoImplantacao = calcularCustoImplantacao(ferrovia);
        individuo.setCustoImplantacao(custoImplantacao);

        //se o custo de implantação da ferrovia exceder o orçamento máximo, define o custo de transporte e a aptidão como infinito para penalizar soluções inviáveis, caso contrário, calcula o custo de transporte considerando as demandas e a ferrovia gerada, definindo a aptidão como o custo total de transporte para avaliar a qualidade da solução
        if (custoImplantacao > orcamentoMaximo) {
            individuo.setCustoTransporte(Double.MAX_VALUE);
            individuo.setAptidao(Double.MAX_VALUE);
            return;
        }

        double custoTransporte = calcularCustoTransporte(demandas, grafoReal, ferrovia);
        individuo.setCustoTransporte(custoTransporte);
        individuo.setAptidao(custoTransporte);        
    }

    //metodo para construir a lista de arestas de ferrovia correspondente a um indivíduo, incluindo as arestas candidatas cujos genes estão ativados (true) no indivíduo, retornando a ferrovia gerada
    public List<ArestaFerrovia> construirListaFerrovia(
            IndividuoGenetico individuo,
            List<ArestaFerrovia> arestasCandidatas
    ) {
        List<ArestaFerrovia> ferrovia = new ArrayList<>();

        //para cada gene do indivíduo, verifica se está ativado (true) e, caso esteja, adiciona a aresta candidata correspondente à ferrovia gerada, construindo a lista de arestas de ferrovia com base na combinação representada pelo indivíduo
        for (int i = 0; i < individuo.getTamanhoCromossomo(); i++) {
            if (individuo.getGene(i)) {
                ferrovia.add(arestasCandidatas.get(i));
            }
        }

        return ferrovia;
    }

    //metodo para calcular o custo de implantação da ferrovia, somando o custo de construção de cada aresta presente na ferrovia gerada, retornando o custo total de implantação
    private double calcularCustoImplantacao(List<ArestaFerrovia> ferrovia) {
        double custoImplantacao = 0.0;

        for (ArestaFerrovia aresta : ferrovia) {
            custoImplantacao += aresta.getCustoConstrucao();
        }

        return custoImplantacao;
    }

    //metodo para calcular o custo de transporte considerando as demandas, o grafo real e a ferrovia gerada, buscando a rota hibrida para cada demanda e acumulando o custo total de transporte, penalizando soluções inviáveis que não consigam atender a alguma demanda, retornando o custo total de transporte considerando todas as demandas
    private double calcularCustoTransporte(
            List<DemandaCarga> demandas,
            Map<String, List<Conexao>> grafoReal,
            List<ArestaFerrovia> ferrovia
    ) {
        double custoTransporte = 0.0;

        //para cada demanda de carga, busca a rota hibrida considerando a ferrovia gerada e o grafo real, calculando o custo total de transporte multiplicado pela quantidade de cargas da demanda, acumulando o custo total de transporte considerando todas as demandas, penalizando soluções inviáveis que não consigam atender a alguma demanda
        for (DemandaCarga demanda : demandas) {
            ResultadoBusca resultado = aService.buscarRotaComFerrovia(
                    demanda.getOrigem(),
                    demanda.getDestino(),
                    grafoReal,
                    ferrovia
            );

            //se não for possível encontrar uma rota para a demanda, penaliza o custo de transporte com um valor muito alto para refletir a inviabilidade da solução, caso contrário, acumula o custo total de transporte multiplicado pela quantidade de cargas da demanda para calcular o custo total de transporte considerando todas as demandas
            if (resultado == null) {
                custoTransporte += 1000000000.0;
            } else {
                custoTransporte += resultado.getCustoTotal() * demanda.getQuantidadeCargas();
            }
        }

        return custoTransporte;
    }

    //metodo para selecionar um indivíduo da população usando o método de seleção por torneio, onde um número definido de indivíduos é selecionado aleatoriamente da população e o indivíduo com a melhor aptidão entre eles é escolhido como o vencedor do torneio, retornando o indivíduo selecionado para reprodução
    private IndividuoGenetico selecionarIndividuo(List<IndividuoGenetico> populacao, Random random) {
        int tamanhoSort = 3;
        IndividuoGenetico melhor = populacao.get(random.nextInt(populacao.size()));

        //realiza o torneio selecionando aleatoriamente um número definido de indivíduos da população e comparando suas aptidões para escolher o melhor indivíduo entre eles, garantindo que indivíduos com melhor aptidão tenham maior chance de serem selecionados para reprodução, mas mantendo diversidade genética ao permitir a seleção de indivíduos com aptidão inferior
        for (int i = 1; i < tamanhoSort; i++) {
            IndividuoGenetico candidato = populacao.get(random.nextInt(populacao.size()));
            if (candidato.getAptidao() < melhor.getAptidao()) {
                melhor = candidato;
            }
        }

        return melhor;
    }

    //metodo para cruzar dois indivíduos usando um ponto de corte aleatório, onde os genes do filho são herdados do pai1 até o ponto de corte e do pai2 a partir do ponto de corte, retornando o indivíduo filho resultante do cruzamento
    private IndividuoGenetico cruzar(IndividuoGenetico pai1, IndividuoGenetico pai2, Random random) {
        int tamanhoCromossomo = pai1.getTamanhoCromossomo();
        IndividuoGenetico filho = new IndividuoGenetico(tamanhoCromossomo);
        int pontoCorte = random.nextInt(tamanhoCromossomo);

        //para cada gene do filho, verifica o ponto de corte e herda os genes do pai1 até o ponto de corte e do pai2 a partir do ponto de corte
        for (int i = 0; i < tamanhoCromossomo; i++) {
            if( i < pontoCorte) {
                filho.setGene(i, pai1.getGene(i));
            } else {
                filho.setGene(i, pai2.getGene(i));
            }
        }
        return filho;
    }

    //metodo para aplicar mutação em um indivíduo, onde cada gene tem uma probabilidade definida de ser invertido (de true para false ou vice-versa), garantindo diversidade genética na população e explorando novas soluções, sem alterar a estrutura do indivíduo, apenas modificando a presença ou ausência de arestas candidatas na ferrovia gerada
    private void mutacao(IndividuoGenetico individuo, Random random) {
        for (int i = 0; i < individuo.getTamanhoCromossomo(); i++) {
            if (random.nextDouble() < taxaMutacao) {
                individuo.setGene(i, !individuo.getGene(i));
            }
        }
    }

    //metodo para criar uma cópia de um indivíduo, incluindo seus genes, aptidão, custo de implantação e custo de transporte, garantindo que as alterações no clone não afetem o indivíduo original, retornando o clone do indivíduo
    private IndividuoGenetico copiarIndividuo(IndividuoGenetico individuo) {
        IndividuoGenetico clone = new IndividuoGenetico(individuo.getGenes().clone());
        clone.setAptidao(individuo.getAptidao());
        clone.setCustoImplantacao(individuo.getCustoImplantacao());
        clone.setCustoTransporte(individuo.getCustoTransporte());
        return clone;
    }

}