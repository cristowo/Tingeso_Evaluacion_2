import React from 'react';
import { BrowserRouter, Route} from 'react-router-dom';
import MostrarProveedoresComponent from './components/MostrarProveedoresComponent';
import CrearProveedorComponent from './components/CrearProveedorComponent';
import MostrarPagosComponent from './components/MostrarPagosComponent';

function App() {
  
  return (
    <div>
      <BrowserRouter>
          <Route path="/proveedores" component={MostrarProveedoresComponent} />
          <Route path="/proveedores/register" component={CrearProveedorComponent} />
          <Route path="/pagos/:codigo" component={MostrarPagosComponent} />
      </BrowserRouter>
    </div>
  );
}

export default App;
