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

    public Map<String, Object> otimizar(
            Map<String, List<Conexao>> grafoReal,
            List<ArestaFerrovia> arestasCandidatas,
            List<DemandaCarga> demandas,
            double orcamentoMaximo
    ) {
        int tamanhoCromossomo = arestasCandidatas.size();
        List<IndividuoGenetico> populacao = new ArrayList<>();

        for (int i = 0; i < tamanhoPopulacao; i++) {
            IndividuoGenetico individuo = new IndividuoGenetico(tamanhoCromossomo);
            for (int j = 0; j < tamanhoCromossomo; j++) {
                individuo.setGene(j, random.nextDouble() < 0.3);
            }
            populacao.add(individuo);
        }

        IndividuoGenetico melhorIndividuo = null;

        for (int geracao = 0; geracao < geracoes; geracao++) {
            for (IndividuoGenetico individuo : populacao) {
                avaliarAptidao(individuo, arestasCandidatas, demandas, grafoReal, orcamentoMaximo);
            }

            Collections.sort(populacao);

            if (melhorIndividuo == null || populacao.get(0).getAptidao() < melhorIndividuo.getAptidao()) {
                melhorIndividuo = copiarIndividuo(populacao.get(0));
            }

            List<IndividuoGenetico> novaPopulacao = new ArrayList<>();
            novaPopulacao.add(copiarIndividuo(melhorIndividuo));

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

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("ferrovia", ferroviaOtima);
        resultado.put("custoImplantacao", melhorIndividuo.getCustoImplantacao());
        resultado.put("orcamentoMaximo", orcamentoMaximo);
        resultado.put("custoTransporte", melhorIndividuo.getCustoTransporte());
        resultado.put("aptidao", melhorIndividuo.getAptidao());
        return resultado;
    }

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

        if (custoImplantacao > orcamentoMaximo) {
            individuo.setCustoTransporte(Double.MAX_VALUE);
            individuo.setAptidao(Double.MAX_VALUE);
            return;
        }

        double custoTransporte = calcularCustoTransporte(demandas, grafoReal, ferrovia);
        individuo.setCustoTransporte(custoTransporte);
        individuo.setAptidao(custoTransporte);        
    }

    public List<ArestaFerrovia> construirListaFerrovia(
            IndividuoGenetico individuo,
            List<ArestaFerrovia> arestasCandidatas
    ) {
        List<ArestaFerrovia> ferrovia = new ArrayList<>();

        for (int i = 0; i < individuo.getTamanhoCromossomo(); i++) {
            if (individuo.getGene(i)) {
                ferrovia.add(arestasCandidatas.get(i));
            }
        }

        return ferrovia;
    }

    private double calcularCustoImplantacao(List<ArestaFerrovia> ferrovia) {
        double custoImplantacao = 0.0;

        for (ArestaFerrovia aresta : ferrovia) {
            custoImplantacao += aresta.getCustoConstrucao();
        }

        return custoImplantacao;
    }

    private double calcularCustoTransporte(
            List<DemandaCarga> demandas,
            Map<String, List<Conexao>> grafoReal,
            List<ArestaFerrovia> ferrovia
    ) {
        double custoTransporte = 0.0;

        for (DemandaCarga demanda : demandas) {
            ResultadoBusca resultado = aService.buscarRotaComFerrovia(
                    demanda.getOrigem(),
                    demanda.getDestino(),
                    grafoReal,
                    ferrovia
            );

            if (resultado == null) {
                custoTransporte += 1000000000.0;
            } else {
                custoTransporte += resultado.getCustoTotal() * demanda.getQuantidadeCargas();
            }
        }

        return custoTransporte;
    }

    private IndividuoGenetico selecionarIndividuo(List<IndividuoGenetico> populacao, Random random) {
        int tamanhoSort = 3;
        IndividuoGenetico melhor = populacao.get(random.nextInt(populacao.size()));

        for (int i = 1; i < tamanhoSort; i++) {
            IndividuoGenetico candidato = populacao.get(random.nextInt(populacao.size()));
            if (candidato.getAptidao() < melhor.getAptidao()) {
                melhor = candidato;
            }
        }

        return melhor;
    }

    private IndividuoGenetico cruzar(IndividuoGenetico pai1, IndividuoGenetico pai2, Random random) {
        int tamanhoCromossomo = pai1.getTamanhoCromossomo();
        IndividuoGenetico filho = new IndividuoGenetico(tamanhoCromossomo);
        int pontoCorte = random.nextInt(tamanhoCromossomo);

        for (int i = 0; i < tamanhoCromossomo; i++) {
            if( i < pontoCorte) {
                filho.setGene(i, pai1.getGene(i));
            } else {
                filho.setGene(i, pai2.getGene(i));
            }
        }
        return filho;
    }

    private void mutacao(IndividuoGenetico individuo, Random random) {
        for (int i = 0; i < individuo.getTamanhoCromossomo(); i++) {
            if (random.nextDouble() < taxaMutacao) {
                individuo.setGene(i, !individuo.getGene(i));
            }
        }
    }

    private IndividuoGenetico copiarIndividuo(IndividuoGenetico individuo) {
        IndividuoGenetico clone = new IndividuoGenetico(individuo.getGenes().clone());
        clone.setAptidao(individuo.getAptidao());
        clone.setCustoImplantacao(individuo.getCustoImplantacao());
        clone.setCustoTransporte(individuo.getCustoTransporte());
        return clone;
    }

}