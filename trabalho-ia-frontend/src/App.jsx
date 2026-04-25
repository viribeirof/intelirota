import { BrowserRouter, Routes, Route } from "react-router-dom";
import Inicio from "./pages/PaginaInicial";
import Segunda from "./pages/PaginaRecepcao";
import Terceira from "./pages/InsercaoRota";

function App() {

  return (
    <BrowserRouter>
      <div className="container">
        <Routes>
          <Route path="/" element={<Inicio />} />
          <Route path="/telaRecepcao" element={<Segunda />} />
          <Route path="/telaPrincipal" element={<Terceira />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;