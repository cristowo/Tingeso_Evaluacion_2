import React, { Component } from 'react';
import {Row, Col } from 'rsuite';
import Table from 'rsuite/Table';
import { Column, HeaderCell, Cell } from 'rsuite-table';
import pagoService from '../services/PagoService';
import llegadaService from '../services/LlegadaService';
import resultadoService from '../services/ResultadoService';

class GenerarPagoComponent extends Component {

  constructor(props) {
    super(props);
    this.state = {
        llegadas: [], 
        resultados: []
    }
  }

  componentDidMount() {
    llegadaService.obtenerAcopios().then((res) => {
        this.setState({ llegadas: res.data});
    });
    resultadoService.obtenerResultados().then((res) => {
        this.setState({ resultados: res.data});
    });
  }
    

  generar(){
    pagoService.generarPago()
        .then(res => {
            window.alert("Se genero el pago correctamente de todos los proveedores registrados");
            window.location.href = '/';
        })
  }

  render() {
    return (
      <div>
        <Row className="show-grid">
          <Col xs={4}></Col>
          <Col xs={8}>
            <h1>Lista de Acopios Cargados Actuales</h1>
            <Table data={this.state.llegadas} height={400}>
            <Column width={60}>
              <HeaderCell>Codigo</HeaderCell>
              <Cell dataKey="proveedor" />
            </Column>
            <Column width={100}>
              <HeaderCell>Turno</HeaderCell>
              <Cell dataKey="turno" />
            </Column>
            <Column width={100}>
              <HeaderCell>Kg de leche</HeaderCell>
              <Cell dataKey="kg_leche" />
            </Column>
            </Table>
          </Col>
          <Col xs={8}>
            <h1>Lista de Resultados Cargados Actuales</h1>
            <Table data={this.state.resultados} height={400}>
            <Column width={60}>
              <HeaderCell>Codigo</HeaderCell>
              <Cell dataKey="proveedor" />
            </Column>
            <Column width={100}>
              <HeaderCell>% Grasa</HeaderCell>
              <Cell dataKey="porcentaje_grasa" />
            </Column>
            <Column width={100}>
              <HeaderCell>% Solidos Totales</HeaderCell>
              <Cell dataKey="porcentaje_solido" />
            </Column>
            </Table>
          </Col>
        </Row>
        <center>
        <h1>Generar Pago</h1>
        <button onClick={ () => this.generar()}>Generar Pago</button>
        </center>
    </div>
    );
  }
}

export default GenerarPagoComponent;
