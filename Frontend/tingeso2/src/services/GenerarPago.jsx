import axios from 'axios';

const url = 'http://localhost:8080/pagos';

class GenerarPagoService{
    generarPago(){
        return axios.post(url);
    }
}