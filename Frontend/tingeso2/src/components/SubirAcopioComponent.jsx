import React, { Component } from 'react';
import LlegadaService from '../services/LlegadaService';

class SubirAcopioComponent extends Component {
    constructor() {
        super();
        this.state = {
            file: null
        }
        this.onChangeHandler = this.onChangeHandler.bind(this);
    }

    onChangeHandler = event => {
        this.setState({
            file: event.target.files[0]
        })
    }

    onClickHandler = () => {
        const data = new FormData()
        data.append('file', this.state.file)
        LlegadaService.subirAcopio(data)
            .then(res => {
                window.alert("Se subio el acopio correctamente");
                window.location.href = '/';
            })
    }

    render() {
        return (
            <div>
                <h1>Subir Acopio</h1>
                <input type="file" onChange={this.onChangeHandler} />
                <button type="button" onClick={this.onClickHandler}>Upload</button>
            </div>
        );
    }

}
export default SubirAcopioComponent;