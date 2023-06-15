import React, { Component } from 'react';
import pagoService from "../services/PagoService";
import llegadaService from "../services/LlegadaService";
import resultadoService from "../services/ResultadoService";

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
                <h1>Generar Pago</h1>
                <button onClick={ () => this.generar()}>Generar Pago</button>

                <h1>Lista de Acopios Cargados Actuales</h1>
                <table>
                    <thead>
                        <tr>
                            <th>Codigo</th>
                            <th>Turno</th>
                            <th>Kg de leche</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.llegadas.map(
                                llegada =>
                                    <tr key={llegada.id_Llegada}>
                                        <td>{llegada.proveedor}</td>
                                        <td>{llegada.turno}</td>
                                        <td>{llegada.kg_leche}</td>
                                    </tr>
                            )
                        }
                    </tbody>
                </table>

                <h1>Lista de Resultados Cargados Actuales</h1>
                <table>
                    <thead>
                        <tr>
                            <th>Codigo</th>
                            <th>% Grasa</th>
                            <th>% Solidos Totales</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.resultados.map(
                                resultado =>
                                    <tr key={resultado.id_Resultado}>
                                        <td>{resultado.proveedor}</td>
                                        <td>{resultado.porcentaje_grasa}</td>
                                        <td>{resultado.porcentaje_solidos}</td>
                                    </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        );
    }
}
export default GenerarPagoComponent;