import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ReactFlow, {
  useNodesState,
  useEdgesState,
  Background,
  Controls,
  MarkerType,
} from "reactflow";
import "reactflow/dist/style.css";
import { useNavigate } from "react-router-dom";

import {
  calcularRotaRodoviaria,
  calcularRotaHibridaKruskal,
  calcularRotaHibridaGenetico,
  buscarMalhaKruskal,
  buscarMalhaGenetica
} from "../services/api";

const capitais = [
  { valor: "Aracaju", label: "Aracaju" },
  { valor: "Belém", label: "Belém" },
  { valor: "Belo Horizonte", label: "Belo Horizonte" },
  { valor: "Boa Vista", label: "Boa Vista" },
  { valor: "Brasília", label: "Brasília" },
  { valor: "Campo Grande", label: "Campo Grande" },
  { valor: "Cuiabá", label: "Cuiabá" },
  { valor: "Curitiba", label: "Curitiba" },
  { valor: "Florianópolis", label: "Florianópolis" },
  { valor: "Fortaleza", label: "Fortaleza" },
  { valor: "Goiânia", label: "Goiânia" },
  { valor: "João Pessoa", label: "João Pessoa" },
  { valor: "Macapá", label: "Macapá" },
  { valor: "Maceió", label: "Maceió" },
  { valor: "Manaus", label: "Manaus" },
  { valor: "Natal", label: "Natal" },
  { valor: "Palmas", label: "Palmas" },
  { valor: "Porto Alegre", label: "Porto Alegre" },
  { valor: "Porto Velho", label: "Porto Velho" },
  { valor: "Recife", label: "Recife" },
  { valor: "Rio Branco", label: "Rio Branco" },
  { valor: "Rio de Janeiro", label: "Rio de Janeiro" },
  { valor: "Salvador", label: "Salvador" },
  { valor: "São Luís", label: "São Luís" },
  { valor: "São Paulo", label: "São Paulo" },
  { valor: "Teresina", label: "Teresina" },
  { valor: "Vitória", label: "Vitória" },
];

const nodeStyle = {
  background: "#212529",
  color: "#fff",
  border: "1px solid #6c757d",
  borderRadius: "5px",
  padding: "5px 8px", 
  fontSize: "12px",
};

const edgeStyle = {
  stroke: "#6c757d",
  strokeWidth: 2,
};

const rawNodes = [
  { id: "Boa Vista", x: 250, y: 50, label: "Boa Vista" },
  { id: "Macapa", x: 450, y: 50, label: "Macapá" },
  { id: "Manaus", x: 200, y: 150, label: "Manaus" },
  { id: "Belem", x: 500, y: 100, label: "Belém" },
  { id: "Porto Velho", x: 200, y: 300, label: "Porto Velho" },
  { id: "Rio Branco", x: 100, y: 350, label: "Rio Branco" },
  { id: "Palmas", x: 500, y: 280, label: "Palmas" },
  { id: "Sao Luis", x: 600, y: 120, label: "São Luís" },
  { id: "Teresina", x: 600, y: 200, label: "Teresina" },
  { id: "Fortaleza", x: 700, y: 150, label: "Fortaleza" },
  { id: "Natal", x: 750, y: 180, label: "Natal" },
  { id: "Joao Pessoa", x: 770, y: 210, label: "João Pessoa" },
  { id: "Recife", x: 770, y: 240, label: "Recife" },
  { id: "Maceio", x: 750, y: 270, label: "Maceió" },
  { id: "Aracaju", x: 730, y: 300, label: "Aracaju" },
  { id: "Salvador", x: 700, y: 350, label: "Salvador" },
  { id: "Cuiaba", x: 350, y: 400, label: "Cuiabá" },
  { id: "Brasilia", x: 500, y: 400, label: "Brasília" },
  { id: "Goiania", x: 450, y: 420, label: "Goiânia" },
  { id: "Campo Grande", x: 350, y: 500, label: "Campo Grande" },
  { id: "Belo Horizonte", x: 600, y: 450, label: "B. Horizonte" },
  { id: "Vitoria", x: 700, y: 480, label: "Vitória" },
  { id: "Rio de Janeiro", x: 650, y: 550, label: "Rio de Janeiro" },
  { id: "Sao Paulo", x: 550, y: 580, label: "São Paulo" },
  { id: "Curitiba", x: 500, y: 650, label: "Curitiba" },
  { id: "Florianopolis", x: 530, y: 700, label: "Florianópolis" },
  { id: "Porto Alegre", x: 450, y: 780, label: "Porto Alegre" },
];

const scale = 1.35; 
const initialNodes = rawNodes.map(n => ({
  id: n.id,
  position: { x: n.x * scale, y: n.y * scale },
  data: { label: n.label },
  style: nodeStyle
}));

function criarIdAresta(origem, destino) {
  return `e-${origem}-${destino}`;
}

function criarAresta(origem, destino) {
  return {
    id: criarIdAresta(origem, destino),
    source: origem,
    target: destino,
    style: edgeStyle,
    animated: false
  };
}

const initialEdges = [
  criarAresta("Aracaju", "Maceio"), criarAresta("Aracaju", "Salvador"),
  criarAresta("Belem", "Sao Luis"), criarAresta("Belo Horizonte", "Salvador"),
  criarAresta("Belo Horizonte", "Goiania"), criarAresta("Belo Horizonte", "Brasilia"),
  criarAresta("Boa Vista", "Belem"), criarAresta("Brasilia", "Goiania"),
  criarAresta("Campo Grande", "Cuiaba"), criarAresta("Campo Grande", "Belo Horizonte"),
  criarAresta("Campo Grande", "Goiania"), criarAresta("Cuiaba", "Goiania"),
  criarAresta("Cuiaba", "Manaus"), criarAresta("Cuiaba", "Porto Velho"),
  criarAresta("Cuiaba", "Belem"), criarAresta("Cuiaba", "Palmas"),
  criarAresta("Curitiba", "Sao Paulo"), criarAresta("Curitiba", "Campo Grande"),
  criarAresta("Florianopolis", "Curitiba"), criarAresta("Fortaleza", "Natal"),
  criarAresta("Fortaleza", "Recife"), criarAresta("Goiania", "Palmas"),
  criarAresta("Goiania", "Salvador"), criarAresta("Joao Pessoa", "Fortaleza"),
  criarAresta("Joao Pessoa", "Natal"), criarAresta("Macapa", "Belem"),
  criarAresta("Maceio", "Recife"), criarAresta("Maceio", "Salvador"),
  criarAresta("Manaus", "Boa Vista"), criarAresta("Manaus", "Belem"),
  criarAresta("Manaus", "Porto Velho"), criarAresta("Palmas", "Belem"),
  criarAresta("Palmas", "Sao Luis"), criarAresta("Palmas", "Teresina"),
  criarAresta("Palmas", "Salvador"), criarAresta("Porto Alegre", "Florianopolis"),
  criarAresta("Porto Velho", "Rio Branco"), criarAresta("Recife", "Joao Pessoa"),
  criarAresta("Recife", "Teresina"), criarAresta("Rio Branco", "Manaus"),
  criarAresta("Rio de Janeiro", "Sao Paulo"), criarAresta("Rio de Janeiro", "Vitoria"),
  criarAresta("Rio de Janeiro", "Belo Horizonte"), criarAresta("Salvador", "Recife"),
  criarAresta("Salvador", "Teresina"), criarAresta("Salvador", "Vitoria"),
  criarAresta("Sao Luis", "Teresina"), criarAresta("Sao Paulo", "Campo Grande"),
  criarAresta("Sao Paulo", "Belo Horizonte"), criarAresta("Teresina", "Fortaleza"),
  criarAresta("Vitoria", "Belo Horizonte")
];

function formatarId(nome) {
  if (!nome) return "";
  return nome.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

function limparModoTransporte(cidade) {
  return cidade.replace(/\s*\(.*?\)\s*/g, "").trim();
}

function formatarMoeda(valor) {
  return Number(valor).toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL",
  });
}

export default function InsercaoRota() {
  const navigate = useNavigate();

  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);

  const [origem, setOrigem] = React.useState("");
  const [destino, setDestino] = React.useState("");
  const [tipoRota, setTipoRota] = React.useState("rodoviaria");

  const [estaCalculando, setEstaCalculando] = React.useState(false);
  const [resultado, setResultado] = React.useState(null);
  const [erro, setErro] = React.useState("");

  const resetarMapa = () => {
    setNodes(initialNodes);
    setEdges(initialEdges);
  };

  const onEdgeClick = (event, clickedEdge) => {
    setEdges((eds) =>
      eds.map((edge) => {
        if (edge.id === clickedEdge.id) {
          if (edge.label) {
            return { ...edge, label: undefined };
          } else if (edge.data && edge.data.labelOculta) {
            return { ...edge, label: edge.data.labelOculta };
          }
        }
        return edge;
      })
    );
  };

  async function calcularRota() {
    if (!origem || !destino) {
      setErro("Selecione a cidade de saída e a cidade de destino.");
      return;
    }
    if (origem === destino) {
      setErro("A cidade de saída e destino precisam ser diferentes.");
      return;
    }

    setEstaCalculando(true);
    setErro("");
    setResultado(null);

    try {
      let dados;
      if (tipoRota === "rodoviaria") {
        dados = await calcularRotaRodoviaria(origem, destino);
      } else if (tipoRota === "hibrida-kruskal") {
        dados = await calcularRotaHibridaKruskal(origem, destino);
      } else if (tipoRota === "hibrida-genetico") {
        dados = await calcularRotaHibridaGenetico(origem, destino);
      }

      dados.tipo = 'rota';
      dados.titulo = 'Caminho Encontrado';
      setResultado(dados);
      animarCaminho(dados.rota);
    } catch (error) {
      setErro(error.message);
    } finally {
      setEstaCalculando(false);
    }
  }

  function animarCaminho(rotaRecebida) {
    if (!rotaRecebida || rotaRecebida.length < 2) return;

    setNodes(initialNodes); 

    setEdges([]);
    let arestasAparecendo = [];
    const passos = [];

    for (let i = 0; i < rotaRecebida.length - 1; i++) {
      const origemTrecho = limparModoTransporte(rotaRecebida[i]);
      const destinoTrecho = limparModoTransporte(rotaRecebida[i + 1]);
      const isFerrovia = rotaRecebida[i + 1].toUpperCase().includes("FERROVIA");
      
      passos.push({
        source: formatarId(origemTrecho),
        target: formatarId(destinoTrecho),
        origemNome: origemTrecho,
        destinoNome: destinoTrecho,
        isFerrovia: isFerrovia,
        modo: isFerrovia ? "Ferrovia" : "Rodovia"
      });
    }

    let passo = 0;
    const intervalo = setInterval(() => {
      if (passo >= passos.length) {
        clearInterval(intervalo);
        return;
      }
      const passoAtual = passos[passo];
      const corDaLinha = passoAtual.isFerrovia ? "#198754" : "#0dcaf0"; 

      const novaAresta = {
        id: `e-rota-${passo}`,
        source: passoAtual.source,
        target: passoAtual.target,
        animated: true,
        data: { labelOculta: `${passoAtual.origemNome} ➔ ${passoAtual.destinoNome} (${passoAtual.modo})` },
        labelStyle: { fill: '#fff', fontWeight: 'bold', fontSize: 10 },
        labelBgStyle: { fill: '#212529', fillOpacity: 0.9, stroke: corDaLinha, strokeWidth: 1 },
        labelBgPadding: [6, 4],
        labelBgBorderRadius: 4,
        style: { stroke: corDaLinha, strokeWidth: 4, cursor: 'pointer' },
        markerEnd: { type: MarkerType.ArrowClosed, color: corDaLinha }
      };

      arestasAparecendo = [...arestasAparecendo, novaAresta];
      setEdges(arestasAparecendo);
      
      passo++;
    }, 500); 
  }

  async function handleBuscarKruskal() {
    setEstaCalculando(true);
    setErro("");
    setResultado(null);

    try {
      const dados = await buscarMalhaKruskal();
      const malhaArray = dados.ferrovia || dados.malha;
      const custoObra = dados.custoTotal || dados.custoTotalObra;

      const trechosFormatados = malhaArray.map(t => `${t.u || t.origem} ↔ ${t.v || t.destino}`);

      setResultado({
        tipo: 'malha',
        titulo: 'Malha Kruskal (MST)',
        custoObra: custoObra,
        qtdTrechos: malhaArray.length,
        trechos: trechosFormatados 
      });
      desenharMalha(malhaArray);
    } catch (error) {
      setErro(error.message);
    } finally {
      setEstaCalculando(false);
    }
  }

  async function handleBuscarGenetico() {
    setEstaCalculando(true);
    setErro("");
    setResultado({ tipo: 'loading', titulo: 'Evoluindo Genético...' });

    try {
      const dados = await buscarMalhaGenetica();
      const malhaArray = dados.ferrovia;
      const custoObra = dados.custoConstrucao || dados.custoImplantacao;

      if (malhaArray && malhaArray.length > 0) {
        const trechosFormatados = malhaArray.map(t => `${t.u || t.origem} ↔ ${t.v || t.destino}`);

        setResultado({
          tipo: 'malha',
          titulo: 'Malha Otimizada (Genético)',
          custoObra: custoObra,
          qtdTrechos: malhaArray.length,
          trechos: trechosFormatados
        });
        desenharMalha(malhaArray);
      } else {
        setResultado({ tipo: 'info', titulo: 'Genético: Construção Inviável' });
      }
    } catch (error) {
      setErro(error.message);
      setResultado(null);
    } finally {
      setEstaCalculando(false);
    }
  }

  function desenharMalha(malhaRecebida) {
    if (!malhaRecebida) return;
    setNodes(initialNodes); 
    const novasArestas = malhaRecebida.map((trecho, index) => {
      const nomeOrigem = trecho.origem || trecho.u;
      const nomeDestino = trecho.destino || trecho.v;
      const origemTrecho = formatarId(trecho.u || trecho.origem);
      const destinoTrecho = formatarId(trecho.v || trecho.destino);

      return {
        id: `e-ferrovia-${index}`,
        source: origemTrecho,
        target: destinoTrecho,
        animated: false,
        data: { labelOculta: `${nomeOrigem} ↔ ${nomeDestino} (${trecho.distancia} km)` },
        labelStyle: { fill: '#fff', fontWeight: 'bold', fontSize: 10 },
        labelBgStyle: { fill: '#212529', fillOpacity: 0.9, stroke: '#146c43', strokeWidth: 1 },
        labelBgPadding: [6, 4],
        labelBgBorderRadius: 4,
        style: { stroke: "#198754", strokeWidth: 4, cursor: 'pointer' }
      };
    });

    setEdges(novasArestas);
  }

  return (
    <div className="container-fluid p-0 bg-dark text-light" style={{ minHeight: "100vh", width: "100vw", position: "absolute", top: 0, left: 0, overflowX: "hidden" }}>
      <div className="row g-0 h-100">
        
        <div className="col-md-4 p-5 border-end border-secondary d-flex flex-column" style={{ overflowY: 'auto', maxHeight: '100vh' }}>
          <div className="d-flex justify-content-between align-items-center">
            <h2 className="m-0 fw-bold text-info cursor-pointer" onClick={() => navigate("/telaRecepcao")} style={{ cursor: "pointer" }}>INTELLIROTA</h2>
            <button className="btn btn-sm btn-outline-secondary" onClick={() => { resetarMapa(); setResultado(null); }}>↻ Limpar Mapa</button>
          </div>

          <div className="mt-4 flex-grow-1">
            
            <h6 className="text-secondary fw-bold mb-3 border-bottom border-secondary pb-2">BUSCA DE ROTA (A*)</h6>
            
            <div className="mb-3">
              <label htmlFor="cidadeSaida" className="form-label fw-semibold text-secondary" style={{ fontSize: '0.85rem' }}>SAÍDA:</label>
              <select id="cidadeSaida" className="form-select bg-dark text-light border-secondary" value={origem} onChange={(e) => setOrigem(e.target.value)}>
                <option value="" disabled>Escolha uma capital...</option>
                {capitais.map((capital) => <option key={`saida-${capital.valor}`} value={capital.valor}>{capital.label}</option>)}
              </select>
            </div>

            <div className="mb-3">
              <label htmlFor="cidadeDestino" className="form-label fw-semibold text-secondary" style={{ fontSize: '0.85rem' }}>DESTINO:</label>
              <select id="cidadeDestino" className="form-select bg-dark text-light border-secondary" value={destino} onChange={(e) => setDestino(e.target.value)}>
                <option value="" disabled>Escolha uma capital...</option>
                {capitais.map((capital) => <option key={`destino-${capital.valor}`} value={capital.valor}>{capital.label}</option>)}
              </select>
            </div>

            <div className="mb-4">
              <label htmlFor="tipoRota" className="form-label fw-semibold text-secondary" style={{ fontSize: '0.85rem' }}>MODAL DE TRANSPORTE:</label>
              <select id="tipoRota" className="form-select bg-dark text-light border-secondary" value={tipoRota} onChange={(e) => setTipoRota(e.target.value)}>
                <option value="rodoviaria">A* Apenas Rodoviário</option>
                <option value="hibrida-kruskal">A* Híbrido (Usar malha Kruskal)</option>
                <option value="hibrida-genetico">A* Híbrido (Usar malha Genético)</option>
              </select>
            </div>

            <button className="btn btn-primary w-100 fw-bold shadow-sm py-2 mb-4" onClick={calcularRota} disabled={estaCalculando}>
              {estaCalculando ? "PROCESSANDO..." : "TRAÇAR ROTA"}
            </button>

            <h6 className="text-secondary fw-bold mt-4 mb-3 border-bottom border-secondary pb-2">INFRAESTRUTURA FERROVIÁRIA</h6>
            <div className="d-grid gap-2">
              <button className="btn btn-outline-primary fw-bold" onClick={handleBuscarKruskal} disabled={estaCalculando}>
                MALHA FERROVIÁRIA KRUSKAL
              </button>
              <button className="btn btn-outline-primary fw-bold" onClick={handleBuscarGenetico} disabled={estaCalculando}>
                MALHA FERROVIÁRIA GENÉTICO
              </button>
            </div>

            {erro && <div className="alert alert-danger mt-4">{erro}</div>}

            {resultado && (
              <div className="card text-bg-dark border-secondary mt-4 shadow">
                <div className="card-body">
                  <h5 className="card-title text-info border-bottom border-secondary pb-2">{resultado.titulo}</h5>
                  
                  {resultado.tipo === 'rota' && (
                    <>
                      <p className="mb-2"><strong>Caminho:</strong></p>
                      <p style={{ fontSize: "0.85rem" }} className="text-info">{resultado.rota.join(" → ")}</p>
                      <p className="mb-1"><strong>Custo do Frete:</strong> {formatarMoeda(resultado.custoTotal)}</p>
                      <p className="mb-1"><strong>Estados visitados:</strong> {resultado.estadosVisitados}</p>
                      <p className="mb-0"><strong>Estados expandidos:</strong> {resultado.estadosExpandidos}</p>
                    </>
                  )}

                  {resultado.tipo === 'malha' && (
                    <>
                      <p className="mb-2"><strong>Custo Total da Obra:</strong></p>
                      <h4 className="text-success">{formatarMoeda(resultado.custoObra)}</h4>
                      <p className="mb-2 mt-3"><strong>Trechos Construídos ({resultado.qtdTrechos}):</strong></p>
                      
                      <div style={{ maxHeight: "150px", overflowY: "auto", fontSize: "0.85rem" }} className="bg-black bg-opacity-25 p-2 rounded border border-secondary">
                        <ul className="mb-0 ps-3 text-info" style={{ listStyleType: "circle" }}>
                          {resultado.trechos.map((t, i) => (
                            <li key={`trecho-${i}`} className="py-1">{t}</li>
                          ))}
                        </ul>
                      </div>
                    </>
                  )}

                  {resultado.tipo === 'info' && (
                    <p className="text-warning mb-0">O Algoritmo Genético considerou que nenhuma obra de ferrovia é viável para este orçamento.</p>
                  )}

                  {resultado.tipo === 'loading' && (
                    <div className="text-center text-primary p-3">
                      <div className="spinner-border spinner-border-sm me-2" role="status"></div>
                      A evoluir populações... Isso pode demorar uns segundos
                    </div>
                  )}

                </div>
              </div>
            )}
          </div>
        </div>

        <div className="col-md-8 p-4 d-flex align-items-center justify-content-center">
          <div className="w-100 bg-black bg-opacity-25 rounded shadow border border-secondary" style={{ height: "90vh" }}>
            <ReactFlow
              nodes={nodes}
              edges={edges}
              onNodesChange={onNodesChange}
              onEdgesChange={onEdgesChange}
              onEdgeClick={onEdgeClick}
              fitView
              attributionPosition="bottom-right"
            >
              <Background color="#6c757d" gap={16} />
              <Controls />
            </ReactFlow>
          </div>
        </div>
      </div>
    </div>
  );
}