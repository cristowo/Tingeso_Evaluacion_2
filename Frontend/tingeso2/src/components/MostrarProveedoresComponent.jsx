import React, { Component } from 'react';
import ProveedorService from '../services/ProveedorService';
import Table from 'rsuite/Table';
import { Column, HeaderCell, Cell } from 'rsuite-table';

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
      <h1 align="center">Proveedores List</h1>
      <Table data={this.state.proveedores} autoHeight={true} align="center">
        <Column width={60} >
          <HeaderCell>Codigo</HeaderCell>
          <Cell dataKey="codigo" />
        </Column>
        <Column width={100}>
          <HeaderCell>Nombre</HeaderCell>
          <Cell dataKey="nombre" />
        </Column>
        <Column width={100}>
          <HeaderCell>Categoria</HeaderCell>
          <Cell dataKey="categoria" />
        </Column>
        <Column width={100}>
          <HeaderCell>Retencion</HeaderCell>
          <Cell dataKey="retencion" />
        </Column>
        <Column width={100}>
          <HeaderCell>Pagos</HeaderCell>
            <Cell dataKey="codigo">
              {(proveedor) => {
                return (
                  <button onClick={() => this.viewPago(proveedor.codigo)}>Ver</button>
                );
              }}
            </Cell>
        </Column>
      </Table>
      </div>
    );
  }
}

export default MostrarProveedoresComponent;