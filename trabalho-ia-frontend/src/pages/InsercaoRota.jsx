import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import ReactFlow, { useNodesState, useEdgesState, Background, Controls } from 'reactflow';
import 'reactflow/dist/style.css';
import { useNavigate } from 'react-router-dom';

const capitais = [
  "Aracaju", "Belém", "Belo Horizonte", "Boa Vista", "Brasília",
  "Campo Grande", "Cuiabá", "Curitiba", "Florianópolis", "Fortaleza",
  "Goiânia", "João Pessoa", "Macapá", "Maceió", "Manaus",
  "Natal", "Palmas", "Porto Alegre", "Porto Velho", "Recife",
  "Rio Branco", "Rio de Janeiro", "Salvador", "São Luís",
  "São Paulo", "Teresina", "Vitória"
];

const nodeStyle = {
  background: '#212529',
  color: '#fff',
  border: '1px solid #6c757d',
  borderRadius: '8px',
  fontSize: '12px',
  padding: '10px'
};


const edgeStyle = { stroke: '#6c757d', strokeWidth: 2 };


const initialNodes = [
  { id: 'Boa Vista', position: { x: 250, y: 50 }, data: { label: 'Boa Vista' }, style: nodeStyle },
  { id: 'Macapa', position: { x: 450, y: 50 }, data: { label: 'Macapá' }, style: nodeStyle },
  { id: 'Manaus', position: { x: 200, y: 150 }, data: { label: 'Manaus' }, style: nodeStyle },
  { id: 'Belem', position: { x: 500, y: 100 }, data: { label: 'Belém' }, style: nodeStyle },
  { id: 'Porto Velho', position: { x: 200, y: 300 }, data: { label: 'Porto Velho' }, style: nodeStyle },
  { id: 'Rio Branco', position: { x: 100, y: 350 }, data: { label: 'Rio Branco' }, style: nodeStyle },
  { id: 'Palmas', position: { x: 500, y: 280 }, data: { label: 'Palmas' }, style: nodeStyle },

  { id: 'Sao Luis', position: { x: 600, y: 120 }, data: { label: 'São Luís' }, style: nodeStyle },
  { id: 'Teresina', position: { x: 600, y: 200 }, data: { label: 'Teresina' }, style: nodeStyle },
  { id: 'Fortaleza', position: { x: 700, y: 150 }, data: { label: 'Fortaleza' }, style: nodeStyle },
  { id: 'Natal', position: { x: 750, y: 180 }, data: { label: 'Natal' }, style: nodeStyle },
  { id: 'Joao Pessoa', position: { x: 770, y: 210 }, data: { label: 'João Pessoa' }, style: nodeStyle },
  { id: 'Recife', position: { x: 770, y: 240 }, data: { label: 'Recife' }, style: nodeStyle },
  { id: 'Maceio', position: { x: 750, y: 270 }, data: { label: 'Maceió' }, style: nodeStyle },
  { id: 'Aracaju', position: { x: 730, y: 300 }, data: { label: 'Aracaju' }, style: nodeStyle },
  { id: 'Salvador', position: { x: 700, y: 350 }, data: { label: 'Salvador' }, style: nodeStyle },

  { id: 'Cuiaba', position: { x: 350, y: 400 }, data: { label: 'Cuiabá' }, style: nodeStyle },
  { id: 'Brasilia', position: { x: 500, y: 400 }, data: { label: 'Brasília' }, style: nodeStyle },
  { id: 'Goiania', position: { x: 450, y: 420 }, data: { label: 'Goiânia' }, style: nodeStyle },
  { id: 'Campo Grande', position: { x: 350, y: 500 }, data: { label: 'Campo Grande' }, style: nodeStyle },

  { id: 'Belo Horizonte', position: { x: 600, y: 450 }, data: { label: 'B. Horizonte' }, style: nodeStyle },
  { id: 'Vitoria', position: { x: 700, y: 480 }, data: { label: 'Vitória' }, style: nodeStyle },
  { id: 'Rio de Janeiro', position: { x: 650, y: 550 }, data: { label: 'Rio de Janeiro' }, style: nodeStyle },
  { id: 'Sao Paulo', position: { x: 550, y: 580 }, data: { label: 'São Paulo' }, style: nodeStyle },

  { id: 'Curitiba', position: { x: 500, y: 650 }, data: { label: 'Curitiba' }, style: nodeStyle },
  { id: 'Florianopolis', position: { x: 530, y: 700 }, data: { label: 'Florianópolis' }, style: nodeStyle },
  { id: 'Porto Alegre', position: { x: 450, y: 780 }, data: { label: 'Porto Alegre' }, style: nodeStyle },
];

const initialEdges = [
  { id: 'e-boavista-manaus', source: 'Boa Vista', target: 'Manaus', style: edgeStyle },
  { id: 'e-boavista-belem', source: 'Boa Vista', target: 'Belem', style: edgeStyle },
  { id: 'e-macapa-belem', source: 'Macapa', target: 'Belem', style: edgeStyle },
  { id: 'e-manaus-portovelho', source: 'Manaus', target: 'Porto Velho', style: edgeStyle },
  { id: 'e-manaus-riobranco', source: 'Manaus', target: 'Rio Branco', style: edgeStyle },
  { id: 'e-riobranco-portovelho', source: 'Rio Branco', target: 'Porto Velho', style: edgeStyle },
  
  { id: 'e-portovelho-cuiaba', source: 'Porto Velho', target: 'Cuiaba', style: edgeStyle },
  { id: 'e-belem-palmas', source: 'Belem', target: 'Palmas', style: edgeStyle },
  { id: 'e-belem-saoluis', source: 'Belem', target: 'Sao Luis', style: edgeStyle },


  { id: 'e-saoluis-teresina', source: 'Sao Luis', target: 'Teresina', style: edgeStyle },
  { id: 'e-teresina-fortaleza', source: 'Teresina', target: 'Fortaleza', style: edgeStyle },
  { id: 'e-teresina-salvador', source: 'Teresina', target: 'Salvador', style: edgeStyle },
  { id: 'e-fortaleza-natal', source: 'Fortaleza', target: 'Natal', style: edgeStyle },
  { id: 'e-fortaleza-recife', source: 'Fortaleza', target: 'Recife', style: edgeStyle },
  { id: 'e-natal-joaopessoa', source: 'Natal', target: 'Joao Pessoa', style: edgeStyle },
  { id: 'e-joaopessoa-recife', source: 'Joao Pessoa', target: 'Recife', style: edgeStyle },
  { id: 'e-recife-maceio', source: 'Recife', target: 'Maceio', style: edgeStyle },
  { id: 'e-maceio-aracaju', source: 'Maceio', target: 'Aracaju', style: edgeStyle },
  { id: 'e-aracaju-salvador', source: 'Aracaju', target: 'Salvador', style: edgeStyle },

  { id: 'e-salvador-palmas', source: 'Salvador', target: 'Palmas', style: edgeStyle },
  { id: 'e-salvador-bh', source: 'Salvador', target: 'Belo Horizonte', style: edgeStyle },
  { id: 'e-salvador-vitoria', source: 'Salvador', target: 'Vitoria', style: edgeStyle },
  { id: 'e-palmas-brasilia', source: 'Palmas', target: 'Brasilia', style: edgeStyle },
  { id: 'e-palmas-cuiaba', source: 'Palmas', target: 'Cuiaba', style: edgeStyle },
  { id: 'e-cuiaba-goiania', source: 'Cuiaba', target: 'Goiania', style: edgeStyle },
  { id: 'e-cuiaba-campogrande', source: 'Cuiaba', target: 'Campo Grande', style: edgeStyle },
  { id: 'e-goiania-brasilia', source: 'Goiania', target: 'Brasilia', style: edgeStyle },
  { id: 'e-goiania-sp', source: 'Goiania', target: 'Sao Paulo', style: edgeStyle },

  { id: 'e-brasilia-bh', source: 'Brasilia', target: 'Belo Horizonte', style: edgeStyle },
  { id: 'e-bh-sp', source: 'Belo Horizonte', target: 'Sao Paulo', style: edgeStyle },
  { id: 'e-bh-rj', source: 'Belo Horizonte', target: 'Rio de Janeiro', style: edgeStyle },
  { id: 'e-vitoria-rj', source: 'Vitoria', target: 'Rio de Janeiro', style: edgeStyle },
  { id: 'e-rj-sp', source: 'Rio de Janeiro', target: 'Sao Paulo', style: edgeStyle },
  { id: 'e-campogrande-sp', source: 'Campo Grande', target: 'Sao Paulo', style: edgeStyle },

  { id: 'e-sp-curitiba', source: 'Sao Paulo', target: 'Curitiba', style: edgeStyle },
  { id: 'e-curitiba-floripa', source: 'Curitiba', target: 'Florianopolis', style: edgeStyle },
  { id: 'e-floripa-portoalegre', source: 'Florianopolis', target: 'Porto Alegre', style: edgeStyle },
  { id: 'e-portoalegre-floripa', source: 'Porto Alegre', target: 'Florianopolis', style: edgeStyle }
];



export default function InsercaoRota() {
  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);
  const [estaCalculando, setEstaCalculando] = React.useState(false);

  const animarCaminho = () => {
    setEstaCalculando(true);

    setEdges((eds) =>
      eds.map((edge) => ({
        ...edge,
        animated: false,
        style: edgeStyle,
      }))
    );


    const caminhoSimulado = ['e-sp-curitiba', 'e-curitiba-floripa', 'e-vitoria-rj'];

    let passo = 0;

    const intervalo = setInterval(() => {
      if (passo >= caminhoSimulado.length) {
        clearInterval(intervalo);
        setEstaCalculando(false);
        return;
      }

      const idArestaAtual = caminhoSimulado[passo];

      setEdges((eds) =>
        eds.map((edge) => {
          if (edge.id === idArestaAtual) {
            return {
              ...edge,
              animated: true, // Faz os pontinhos correrem pela linha
              style: { stroke: '#0dcaf0', strokeWidth: 4 }, // Azul neon e mais grossa
            };
          }
          return edge;
        })
      );

      passo++;
    }, 800);
  };

  return (
    <div
      className="container-fluid p-0 bg-dark text-light"
      style={{ minHeight: '100vh', width: '100vw', position: 'absolute', top: 0, left: 0, overflowX: 'hidden' }}
    >
      <div className="row g-0 h-100">

        <div className="col-md-4 p-5 border-end border-secondary d-flex flex-column">
          
          <h2 className="m-0 fw-bold text-info" >INTELLIROTA</h2>

          <div className="mt-5 flex-grow-1">

            <div className="mb-5">
              <label htmlFor="cidadeSaida" className="form-label fw-semibold text-secondary">
                SELECIONE A CIDADE DE SAÍDA:
              </label>
              <select
                id="cidadeSaida"
                className="form-select form-select-lg bg-dark text-light border-secondary shadow-sm"
                defaultValue=""
              >
                <option value="" disabled>Escolha uma capital...</option>
                {capitais.map((capital, index) => (
                  <option key={`saida-${index}`} value={capital}>
                    {capital}
                  </option>
                ))}
              </select>
            </div>

                
            <div className="mb-5">
              <label htmlFor="cidadeDestino" className="form-label fw-semibold text-secondary">
                SELECIONE A CIDADE DE DESTINO:
              </label>
              <select
                id="cidadeDestino"
                className="form-select form-select-lg bg-dark text-light border-secondary shadow-sm"
                defaultValue=""
              >
                <option value="" disabled>Escolha uma capital...</option>
                {capitais.map((capital, index) => (
                  <option key={`destino-${index}`} value={capital}>
                    {capital}
                  </option>
                ))}
              </select>
            </div>
          </div>

          <button
            className="btn btn-primary btn-lg w-100 fw-bold shadow-sm py-3 mt-4"
            onClick={animarCaminho}
            disabled={estaCalculando}
          >
            {estaCalculando ? 'CALCULANDO ROTA...' : 'TRAÇAR ROTA'}
          </button>

        </div>

        <div className="col-md-8 p-4 d-flex align-items-center justify-content-center">
          <div
            className="w-100 bg-black bg-opacity-25 rounded shadow border border-secondary"
            style={{ height: '90vh' }}
          >
            <ReactFlow 
              nodes={nodes} 
              edges={edges}
              onNodesChange={onNodesChange}
              onEdgesChange={onEdgesChange}
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