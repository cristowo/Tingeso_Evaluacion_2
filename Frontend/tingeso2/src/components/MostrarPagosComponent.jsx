import React, { Component } from 'react';
import PagoService from '../services/PagoService';

class MostrarPagosComponent extends Component {
	constructor(props) {
		super(props);
		this.state = {
		  codigo: this.props.match.params.codigo,
		  pagos: []
		};
	  }
	
	  componentDidMount() {
        PagoService.mostrarPagos(this.state.codigo).then( res => {
            this.setState({pagos: res.data});
        })
	  }
    

    render() {
        return(
            <div>
                <h2 className="text-center">Pagos List</h2>
                <div className="row">
				<table>
					<thead>
						<tr>
						<th>Id de pago</th>
						<th>Quincena</th>
						<th>Código de proveedor</th>
						<th>Nombre de proveedor</th>
						<th>Total de kilos de leche</th>
						<th>Número de días de envío de leche</th>
						<th>Promedio diario de kilos de leche</th>
						<th>Porcentaje de variación de leche</th>
						<th>Pago por leche</th>
						<th>Descuento por variación de leche</th>
						<th>Porcentaje de grasa</th>
						<th>Porcentaje de variación de grasa</th>
						<th>Pago por grasa</th>
						<th>Descuento por variación de grasa</th>
						<th>Porcentaje de sólidos totales</th>
						<th>Porcentaje de variación de sólidos totales</th>
						<th>Pago por sólidos totales</th>
						<th>Descuento por variación de sólidos totales</th>
						<th>Bonificación por frecuencia</th>
						<th>Pago total</th>
						<th>Monto de retención</th>
						<th>Monto final</th>
						</tr>
					</thead>
					<tbody>
						{this.state.pagos.map(pago => (
						<tr key={pago.id_pago}>
							<td>{pago.id_pago}</td>
							<td>{pago.quincena}</td>
							<td>{pago.codigoProveedor}</td>
							<td>{pago.nombreProveedor}</td>
							<td>{pago.totalKlsLeche}</td>
							<td>{pago.numDiasEnvioLeche}</td>
							<td>{pago.promedioDiarioKlsLeche}</td>
							<td>{pago.porcentajeVariacionLeche}</td>
							<td>{pago.pagoPorLeche}</td>
							<td>{pago.descuentoVariacionLeche}</td>
							<td>{pago.porcentajeGrasa}</td>
							<td>{pago.porcentajeVariacionGrasa}</td>
							<td>{pago.pagoPorGrasa}</td>
							<td>{pago.descuentoVariacionGrasa}</td>
							<td>{pago.porcentajeSolidosTotales}</td>
							<td>{pago.porcentajeVariacionST}</td>
							<td>{pago.pagoPorSolidosTotales}</td>
							<td>{pago.descuentoVariacionST}</td>
							<td>{pago.bonificacionPorFrecuencia}</td>
							<td>{pago.pagoTotal}</td>
							<td>{pago.montoRetencion}</td>
							<td>{pago.montoFinal}</td>
						</tr>
						))}
					</tbody>
					</table>
                </div>
            </div>      
        );
    }
}

export default MostrarPagosComponent;