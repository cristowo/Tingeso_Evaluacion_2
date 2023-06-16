import React, { Component } from 'react';
import ProveedorService from '../services/ProveedorService';
import { Form, ButtonToolbar, Button, Input } from 'rsuite';

class CrearProveedorComponent extends Component {
    constructor() {
        super();
        this.state = {
            nombre: '',
            codigo: '',
            categoria: '',
            retencion: ''
        };
    }

    changeNombreHandler = (event) => {
        this.setState({ nombre: event.target.value });
    }

    changeCodigoHandler = (event) => {
        this.setState({ codigo: event.target.value });
    }

    changeCategoriaHandler = (event) => {
        this.setState({ categoria: event.target.value });
    }

    changeRetencionHandler = (event) => {
        this.setState({ retencion: event.target.value });
    }

    saveProveedor = (e) => {
        e.preventDefault();
        ProveedorService.crearProveedor(this.state.nombre, this.state.codigo, this.state.categoria, this.state.retencion).then(res => {
            window.alert("Se creo el proveedor correctamente");
            window.location.href = "/proveedores";
        });
    }

    render() {
        return(
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'}}>
            <h1 align="center">Crear Proveedor</h1>
        <div style={{width: '1000px'}}></div>
            <Form>
            <Form.Group onChange={this.changeNombreHandler}>
              <Form.ControlLabel>Nombre</Form.ControlLabel>
              <Form.Control name="nombre" />
            </Form.Group>
            <Form.Group onChange={this.changeCodigoHandler}>
              <Form.ControlLabel>Codigo</Form.ControlLabel>
              <Form.Control name="codigo" minLength={5} maxLength={5} />
            </Form.Group>
            <Form.Group onChange={this.changeCategoriaHandler}>
              <Form.ControlLabel>Categoria</Form.ControlLabel>
              <Form.Control name="categoria" />
            </Form.Group>
            <Form.Group onChange={this.changeRetencionHandler}>
              <Form.ControlLabel>Retencion</Form.ControlLabel>
              <Form.Control name="retencion" />
            </Form.Group>
            <Form.Group>
              <ButtonToolbar>
                <Button appearance="primary" onClick={this.saveProveedor}>Enviar</Button>
              </ButtonToolbar>
            </Form.Group>
          </Form>
        </div>
        );
    }

}
export default CrearProveedorComponent;