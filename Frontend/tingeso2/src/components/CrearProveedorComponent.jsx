import React, { Component } from 'react';
import ProveedorService from '../services/ProveedorService';
import { Form, ButtonToolbar, Button, SelectPicker } from 'rsuite';

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

    changeCategoriaHandler = (value) => {
      this.setState({ categoria: value });
    }    

    changeRetencionHandler = (value) => {
        this.setState({ retencion: value });
    }

    saveProveedor = (e) => {
        e.preventDefault();
        ProveedorService.crearProveedor(this.state.nombre, this.state.codigo, this.state.categoria, this.state.retencion).then(res => {
            window.alert("Se creo el proveedor correctamente");
            window.location.href = "/proveedores";
        });
    }
    
    
    render() {
        const dataRetencion = ['Si', 'No'].map(
          retencion => ({ label: retencion, value: retencion })
        );
        
        const dataCategoria = ['A', 'B', 'C', 'D'].map(
          categoria => ({ label: categoria, value: categoria })
        );
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

            <Form.Group>
              <p>Categoria</p>
              <SelectPicker data={dataCategoria} searchable={false} style={{ width: 300 }} onChange={this.changeCategoriaHandler}/>
            </Form.Group>

            <Form.Group>
              <p>Retencion</p>
              <SelectPicker data={dataRetencion} searchable={false} style={{ width: 300 }} onChange={this.changeRetencionHandler}/>
            </Form.Group>

            <Form.Group>
              <ButtonToolbar>
                <Button appearance="primary" style={{ width: 300 }} onClick={this.saveProveedor}>Crear Proveedor</Button>
              </ButtonToolbar>
            </Form.Group>
          </Form>
        </div>
        );
    }

}
export default CrearProveedorComponent;