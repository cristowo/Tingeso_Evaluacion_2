import React, { Component } from 'react';

export default class HomeComponent extends Component {

    redireccionar(direccion){
        window.location.href = direccion;
    }

    render() {
        return (
            <div style={{ display: 'flex'}}>
            <div style={{ marginRight: '15apx' }}>
                <h1>Ver Proveedores</h1>
                <button onClick={ () => this.redireccionar('/proveedores')}>Ver Proveedores</button>
            </div>
            <div style={{ marginRight: '15px' }}>
                <h1>Crear Proveedor</h1>
                <button onClick={ () => this.redireccionar('/proveedores/register')}>Crear Proveedor</button>
            </div>
            <div style={{ marginRight: '15px' }}>
                <h1>Subir Acopio</h1>
                <button onClick={ () => this.redireccionar('/llegadas')}>Subir Acopio</button>
            </div>
            <div style={{ marginRight: '15px' }}>
                <h1>Subir Resultados</h1>
                <button onClick={ () => this.redireccionar('/resultados')}>Subir Resultados</button>
            </div>
            <div style={{ marginRight: '15px' }}> 
                <h1>Generar Pagos</h1>
                <button onClick={ () => this.redireccionar('/pagos/generar')}>Generar Pagos</button>
            </div>
            </div>
        );
    }
}
