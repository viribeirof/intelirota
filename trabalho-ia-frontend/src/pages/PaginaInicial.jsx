import React from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import logoIntellirota from '../imgs/IntellirotaLogo.png';

export default function PaginaInicial() {
  const navigate = useNavigate();

  return (
    <div
      className="container-fluid p-0 m-0 bg-dark"
      style={{
        minHeight: '100vh',
        width: '100vw',
        position: 'absolute', 
        top: 0,
        left: 0,
        overflowX: 'hidden' 
      }}
    >
      <div className="row g-0 flex-grow-1">

        <div className="col-md-8 p-0 border-end d-flex align-items-center justify-content-center bg-dark">
          <div
            className="w-100 d-flex align-items-center justify-content-center rounded shadow-sm border bg-dark"
            style={{ minHeight: '400px', height: '100%' }}
          >
            <img
              src={logoIntellirota}
              alt="Logo do sistema Intellirota"
              className="w-100 h-100"
              style={{ objectFit: 'cover', minHeight: '100vh' }}
            />
          </div>
        </div>

        <div className="col-md-4 p-0 d-flex flex-column justify-content-center align-items-center bg-dark">

          <p className="text-center fs-4 fw-semibold text-light mb-5">
            COMECE JÁ E CALCULE A MELHOR ROTA PARA SUA ENTREGA
          </p>

          <button
            type="button"
            className="btn btn-primary btn-lg w-100 py-3 shadow-sm fw-bold"
            onClick={() => navigate("/telaRecepcao")}
          >
            Entrar
          </button>

        </div>
      </div>
    </div>
  );
}