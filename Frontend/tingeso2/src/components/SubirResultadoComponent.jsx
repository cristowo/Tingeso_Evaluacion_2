import React, { Component } from 'react';
import ResultadoService from '../services/ResultadoService';
import { Grid, Row } from 'rsuite';

class SubirResultadoComponent extends Component {
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
        ResultadoService.subirResultado(data)
            .then(res => {
                window.alert("Se subio el resultado correctamente");
                window.location.href = '/';
            })
    }

    render() {
        return (
            <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'}}>
            <h1>Subir Resultado</h1>
                <Grid fluid align="center">
                    <Row style={{ marginBottom: '20px', marginTop: '20px'}}>
                        <input type="file" onChange={this.onChangeHandler} />
                    </Row>
                    <Row>
                        <button type="button" onClick={this.onClickHandler}>Upload</button>
                    </Row>
                </Grid>
            </div>
        );
    }

}
export default SubirResultadoComponent;