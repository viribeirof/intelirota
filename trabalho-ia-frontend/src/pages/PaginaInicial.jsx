import React from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import logoIntellirota from '../imgs/IntellirotaLogo.png';

export default function PaginaInicial() {
  const navigate = useNavigate();

  return (
    <>
      <style>
        {`
          .fade-in-up {
            animation: fadeInUp 0.8s ease-out forwards;
            opacity: 0;
            transform: translateY(20px);
          }
          .delay-1 { animation-delay: 0.2s; }
          .delay-2 { animation-delay: 0.4s; }
          .delay-3 { animation-delay: 0.6s; }

          @keyframes fadeInUp {
            to {
              opacity: 1;
              transform: translateY(0);
            }
          }

          .btn-glow {
            transition: all 0.3s ease;
          }
          .btn-glow:hover {
            box-shadow: 0 0 20px rgba(13, 202, 240, 0.6);
            transform: translateY(-3px);
          }
        `}
      </style>

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
        <div className="row g-0 flex-grow-1" style={{ minHeight: '100vh' }}>

          <div className="col-lg-7 d-none d-lg-flex p-0 position-relative bg-black align-items-center justify-content-center">
            <img
              src={logoIntellirota}
              alt="Logo do sistema Intellirota"
              className="w-100 h-100"
              style={{ objectFit: 'cover', minHeight: '100vh', opacity: 0.85 }}
            />
            <div 
              className="position-absolute top-0 start-0 w-100 h-100" 
              style={{ background: 'linear-gradient(90deg, rgba(0,0,0,0) 40%, rgba(33,37,41,1) 100%)' }}
            ></div>
          </div>

          <div className="col-lg-5 col-12 p-5 d-flex flex-column justify-content-center bg-dark text-light border-start border-secondary z-1 shadow-lg">

            <div className="mb-auto mt-4 fade-in-up">
              <h1 className="fw-bold text-info mb-1" style={{ letterSpacing: '2px', fontSize: '2.5rem' }}>INTELLIROTA</h1>
              <p className="text-secondary fw-semibold text-uppercase" style={{ fontSize: '0.85rem', letterSpacing: '2px' }}>
                Cálculo Inteligente de Rotas e Conexões
              </p>
            </div>

            <div className="my-5">
              <h2 className="fs-3 fw-bold text-light mb-4 fade-in-up delay-1" style={{ lineHeight: '1.4' }}>
                Calcule a melhor rota e otimize sua entrega.
              </h2>
              <p className="text-secondary mb-5 fade-in-up delay-2" style={{ fontSize: '1rem', lineHeight: '1.6' }}>
                Descubra o caminho de menor custo utilizando algoritmos de roteamento e otimize a infraestrutura multimodal do Brasil com Inteligência Artificial.
              </p>

              <button
                type="button"
                className="btn btn-info btn-lg w-100 py-3 fw-bold text-dark btn-glow fade-in-up delay-3"
                onClick={() => navigate("/telaRecepcao")}
                style={{ letterSpacing: '1px' }}
              >
                ACESSAR O SISTEMA ➔
              </button>
            </div>

            <div className="mt-auto pt-4 border-top border-secondary text-secondary fade-in-up delay-3" style={{ fontSize: '0.8rem' }}>
              <p className="mb-0">IFSC - Instituto Federal de Santa Catarina | Lages (2026)</p>
            </div>

          </div>
        </div>
      </div>
    </>
  );
}