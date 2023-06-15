import React from 'react';
import { BrowserRouter, Route, Switch} from 'react-router-dom';
import Navbar from './components/NavbarComponent';
import MostrarProveedoresComponent from './components/MostrarProveedoresComponent';
import CrearProveedorComponent from './components/CrearProveedorComponent';
import MostrarPagosComponent from './components/MostrarPagosComponent';
import HomeComponent from './components/HomeComponent';

function App() {
  
  return (
    <div>
      <Navbar />
      <BrowserRouter>
          <Switch>
          <Route path="/proveedores/register" component={CrearProveedorComponent} />
            <Route path="/proveedores" component={MostrarProveedoresComponent} />
            <Route path="/pagos/:codigo" component={MostrarPagosComponent} />
            <Route path="/" component={HomeComponent} />
          </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
