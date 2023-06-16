import React, { Component } from 'react';
import {Grid,Row, Col } from 'rsuite';

export default class HomeComponent extends Component {

    redireccionar(direccion){
        window.location.href = direccion;
    }

    render() {
        return (
            <Grid fluid align="center">
                <Row>
                    <Col xs={6} >
                        <h1>Ver Proveedores</h1>
                        <button onClick={ () => this.redireccionar('/proveedores')}>Ver Proveedores</button>
                    </Col>
                    <Col xs={6}>
                        <h1>Crear Proveedor</h1>
                        <button onClick={ () => this.redireccionar('/proveedores/register')}>Crear Proveedor</button>
                    </Col>
                    <Col xs={6}>
                        <h1>Subir Acopio</h1>
                        <button onClick={ () => this.redireccionar('/llegadas')}>Subir Acopio</button>
                    </Col>
                    <Col xs={6}>
                        <h1>Subir Resultados</h1>
                        <button onClick={ () => this.redireccionar('/resultados')}>Subir Resultados</button>
                    </Col>
                </Row>
                <br/><br/><br/><br/><br/><br/>
                <Row>
                    <Col xs={8}></Col>
                    <Col xs={8}>
                        <h1>Generar Pagos</h1>
                        <button onClick={ () => this.redireccionar('/pagos/generar')}>Generar Pagos</button>
                    </Col> 
                </Row>
            </Grid>
        );
    }
}
