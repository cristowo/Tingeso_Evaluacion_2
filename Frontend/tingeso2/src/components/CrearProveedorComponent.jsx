import React, { Component } from 'react';
import ProveedorService from '../services/ProveedorService';

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
            window.location.href = "/proveedores";
        });
    }

    render() {
        return(
            <div>
                <h2 className="text-center">Ingrese nuevo proveedor</h2> 
                <label>Nombre: </label>
                <input type="text" name="nombre" className="form-control" value={this.state.nombre} onChange={this.changeNombreHandler} />
                <label>Codigo: </label>
                <input type="text" name="codigo" className="form-control" value={this.state.codigo} onChange={this.changeCodigoHandler} />
                <label>Categoria: </label>
                <input type="text" name="categoria" className="form-control" value={this.state.categoria} onChange={this.changeCategoriaHandler} />
                <label>Retencion: </label>
                <input type="text" name="retencion" className="form-control" value={this.state.retencion} onChange={this.changeRetencionHandler} />
                <button className="btn btn-success" onClick={this.saveProveedor}>Guardar</button>
            </div>
        );
    }

}
export default CrearProveedorComponent;