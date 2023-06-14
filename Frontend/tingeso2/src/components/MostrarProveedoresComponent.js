import React, { Component } from 'react';
import MostrarProveedoresService from '../services/MostrarProveedores';

class MostrarProveedoresComponent extends Component {
  constructor() {
    super();
    this.state = {
      proveedores: [],
    };
  }

  componentDidMount() {
    MostrarProveedoresService.mostrarProveedores()
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
                        <th>Retencion?</th>
                    </tr>
                </thead>
                <tbody>{
                    this.state.proveedores.map(
                        proveedor =>
                            <tr>
                                <td>{proveedor.id}</td>
                                <td>{proveedor.nombre}</td>
                                <td>{proveedor.codigo}</td>
                                <td>{proveedor.categoria}</td>
                                <td>{proveedor.retencion}</td>
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
