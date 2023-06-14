import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MostrarProveedoresComponent from './components/MostrarProveedoresComponent';

function App() {
  
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/proveedores" element={<MostrarProveedoresComponent />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
