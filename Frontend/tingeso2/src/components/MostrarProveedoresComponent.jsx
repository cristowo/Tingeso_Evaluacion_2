import React, { Component } from 'react';
import ProveedorService from '../services/ProveedorService';

class MostrarProveedoresComponent extends Component {
  constructor() {
    super();
    this.state = {
      proveedores: [],
    };
  }

  viewPago(codigo){
    window.location.href = '/pagos/' + codigo;
  }

  componentDidMount() {
    ProveedorService.mostrarProveedores()
      .then((response) => {
        this.setState({ proveedores: response.data });
      })
      .catch((error) => {
        console.log(error);
      });
  }

  render() {
    return (
    <div> 
        <h2 className="text-center">Proveedores List</h2> 
        
        <div className="row">
            <table className="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>Proveedor ID</th>
                        <th>Nombre</th>
                        <th>Codigo</th>
                        <th>Categoria</th>
                        <th>Retencion</th>
                        <th>Pagos</th>
                    </tr>
                </thead>
                <tbody>{
                    this.state.proveedores.map(
                        proveedor =>
                            <tr key={proveedor.id}>
                                <td>{proveedor.id}</td>
                                <td>{proveedor.nombre}</td>
                                <td>{proveedor.codigo}</td>
                                <td>{proveedor.categoria}</td>
                                <td>{proveedor.retencion}</td>
                                <td><button onClick={ () => this.viewPago(proveedor.codigo)}>ver</button> </td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    </div>
    );
  }
}

export default MostrarProveedoresComponent;
