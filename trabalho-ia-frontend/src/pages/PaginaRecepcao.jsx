import { useNavigate } from 'react-router-dom'; 
import 'bootstrap/dist/css/bootstrap.min.css';
import imgKruskall from "../imgs/Kruskall.png";
import imgAEstrela from "../imgs/AEstrela.png";
import imgGenetico from "../imgs/AlgoritmoGenetico.png";
export default function SelecaoAlgoritmo() {
  const navigate = useNavigate();

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
      <div className="p-4">
        
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

        <main className="text-center mt-5">
          <h3 className="fw-semibold mb-5 text-uppercase" style={{ letterSpacing: '2px' }}>
            UMA NOVA FORMA DE DEFINIR SUA ROTA
          </h3>

          <div className="d-flex justify-content-center flex-wrap gap-5 mt-4">

            <div className="card text-bg-dark border-secondary shadow" style={{ width: '20rem' }}>
              <div className="card-body d-flex flex-column align-items-center p-4">
                <h5 className="card-title fw-bold mb-4">ALGORITMO A*</h5>
                <img src={imgAEstrela} alt="AEstrela" />


              </div>
            </div>

            <div className="card text-bg-dark border-secondary shadow" style={{ width: '20rem' }}>
              <div className="card-body d-flex flex-column align-items-center p-4">
                <h5 className="card-title fw-bold mb-4">ALGORITMO DE KRUSKAL</h5>
                  <img src={imgKruskall} alt="imagem Kruskall" />
              </div>
            </div>

            <div className="card text-bg-dark border-secondary shadow" style={{ width: '20rem' }}>
              <div className="card-body d-flex flex-column align-items-center p-4">
                <h5 className="card-title fw-bold mb-4">ALGORITMO GENÉTICO</h5>
                <img src={imgGenetico} alt="Algoritmo genetico" />
              </div>
            </div>

          </div>
        </main>

      </div>
    </div>
  );
}