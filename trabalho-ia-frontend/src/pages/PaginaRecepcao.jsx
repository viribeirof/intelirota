import { useNavigate } from 'react-router-dom';
import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import gifKruskall from "../imgs/Kruskall.gif";
import gifAEstrela from "../imgs/AEstrela.gif";
import gifGenetico from "../imgs/genetico.gif";

export default function SelecaoAlgoritmo() {
  const navigate = useNavigate();

  const [algoritmoAtivo, setAlgoritmoAtivo] = useState(null);

  return (
    <div
      className="bg-dark text-light"
      style={{
        minHeight: '100vh',
        width: '100vw',
        position: 'absolute',
        top: 0,
        left: 0,
        overflowX: 'hidden'
      }}
    >
      <div className="container py-4">

        <header className="d-flex justify-content-between align-items-center mb-5">
          <h2 className="m-0 fw-bold text-info">INTELLIROTA</h2>

          <button
            type="button"
            onClick={() => navigate('/telaPrincipal')}
            className="btn btn-primary px-4 py-2 fw-bold shadow-sm"
          >
            COMECE JÁ
          </button>
        </header>

        <main className="mt-5">
          <h3 className="fw-semibold mb-5 text-center text-uppercase" style={{ letterSpacing: '2px' }}>
            UMA NOVA FORMA DE DEFINIR SUA ROTA
          </h3>

          <div className="row mt-4">
            <div className="col-md-4 d-flex flex-column align-items-center gap-4 border-end border-secondary">

              <div
                className={`card text-bg-dark shadow ${algoritmoAtivo === 'A_ESTRELA' ? 'border-info' : 'border-secondary'}`}
                style={{ width: '20rem', cursor: 'pointer', transition: 'all 0.3s', borderWidth: algoritmoAtivo === 'A_ESTRELA' ? '2px' : '1px' }}
                onClick={() => setAlgoritmoAtivo('A_ESTRELA')}
              >
                <div className="card-body d-flex flex-column align-items-center p-4">
                  <h5 className={`card-title fw-bold mb-4 ${algoritmoAtivo === 'A_ESTRELA' ? 'text-info' : ''}`}>ALGORITMO A*</h5>
                  <img
                    src={gifAEstrela}
                    alt="Animação A*"
                    style={{ width: '150px', borderRadius: '10px' }}
                  />
                </div>
              </div>

              <div
                className={`card text-bg-dark shadow ${algoritmoAtivo === 'KRUSKAL' ? 'border-info' : 'border-secondary'}`}
                style={{ width: '20rem', cursor: 'pointer', transition: 'all 0.3s', borderWidth: algoritmoAtivo === 'KRUSKAL' ? '2px' : '1px' }}
                onClick={() => setAlgoritmoAtivo('KRUSKAL')}
              >
                <div className="card-body d-flex flex-column align-items-center p-4">
                  <h5 className={`card-title fw-bold mb-4 ${algoritmoAtivo === 'KRUSKAL' ? 'text-info' : ''}`}>ALGORITMO DE KRUSKAL</h5>
                  <img
                    src={gifKruskall}
                    alt="Animação de Kruskal"
                    style={{ width: '150px', borderRadius: '10px' }}
                  />
                </div>
              </div>

              <div
                className={`card text-bg-dark shadow ${algoritmoAtivo === 'GENETICO' ? 'border-info' : 'border-secondary'}`}
                style={{ width: '20rem', cursor: 'pointer', transition: 'all 0.3s', borderWidth: algoritmoAtivo === 'GENETICO' ? '2px' : '1px' }}
                onClick={() => setAlgoritmoAtivo('GENETICO')}
              >
                <div className="card-body d-flex flex-column align-items-center p-4">
                  <h5 className={`card-title fw-bold mb-4 ${algoritmoAtivo === 'GENETICO' ? 'text-info' : ''}`}>ALGORITMO GENÉTICO</h5>
                  <img
                    src={gifGenetico}
                    alt="Animação de Genético"
                    style={{ width: '150px', borderRadius: '10px' }}
                  />
                </div>
              </div>
            </div>

            <div className="col-md-8 p-5 d-flex flex-column justify-content-center">

              <h1 className="text-info mb-5">Como funcionam as rotas?</h1>

              {algoritmoAtivo === null && (
                <div className="text-center mt-4 animation-fade-in">
                  <h3 className="text- fw-bold mb-4">Bem-vindo ao IntelliRota!</h3>
                  <p className="fs-5 text-light mb-4" style={{ lineHeight: '1.8' }}>
                    O IntelliRota é um sistema avançado de logística multimodal criado para otimizar o transporte de cargas por todas as capitais do Brasil. Através de técnicas de Inteligência Artificial, nosso sistema é capaz de simular infraestruturas ferroviárias e roteamentos com precisão matemática.
                  </p>
                  <hr className="border-secondary w-50 mx-auto my-4" />
                  <p className="fs-5 text-secondary fst-italic">
                    Clique em um dos algoritmos ao lado para entender a teoria por trás da nossa IA, ou clique em "COMECE JÁ" no topo para ver o mapa em ação!
                  </p>
                </div>
              )}

              {algoritmoAtivo === 'A_ESTRELA' && (
                <div className="text-center mt-4">
                  <p className="fs-4 text-light fst-italic">
                   O A* é um algoritmo de busca que encontra o caminho mais curto entre dois pontos, usando uma heurística para estimar a distância restante. Ele é eficiente e rápido, ideal para rotas urbanas e aplicativos de GPS, pois evita caminhos que visivelmente se afastam do destino. No intellirota, o A* é perfeito para encontrar a rota mais rápida entre duas cidades, considerando as estradas disponíveis e o tráfego. Vale destacar que o A* faz rotas considerando apenas as rodovias, sem utilizar caminhos por ferrovias.
                  </p>
                </div>
              )}

              {algoritmoAtivo === 'KRUSKAL' && (
                <div className="text-center mt-4">
                  <p className="fs-4 text-light fst-italic">
                    O Algoritmo de Kruskal encontra a árvore geradora mínima de um grafo, conectando todos os vértices com o menor custo total. Diferente do A* (que vai do ponto A ao ponto B), o Kruskal garante que todas as cidades do mapa estejam conectadas usando a menor quantidade de distância possível. No intellirota, o kruskal é utilizado para interligar todas as capitais com ferrovias.
                  </p>
                </div>
              )}

              {algoritmoAtivo === 'GENETICO' && (
                <div className="text-center mt-4">
                  <p className="fs-4 text-light fst-italic">
                    O Algoritmo Genético é uma técnica de otimização inspirada no processo de evolução natural. Ele trabalha com uma população de soluções candidatas, aplicando operadores de seleção, cruzamento e mutação para evoluir a população ao longo das gerações, buscando a melhor solução para o problema em questão. No intellirota, o Algoritmo Genético é utilizado para encontrar a melhor rota considerando tanto rodovias quanto ferrovias, otimizando a distância total percorrida e o tempo gasto. Ele é especialmente útil para problemas complexos de roteamento, onde múltiplas soluções possíveis existem e a busca por uma solução ótima pode ser desafiadora.
                  </p>
                </div>
              )}

            </div>

          </div>
        </main>
        <footer className="mt-5 pt-4 border-top border-secondary text-center text-secondary pb-3">
          <p className="mb-1 fw-semibold text-light">IntelliRota - Inteligência Artificial</p>
          <p className="mb-0 small">IFSC - Instituto Federal de Santa Catarina (Câmpus Lages) | 2026</p>
        </footer>

      </div>
    </div>
  );
}