import axios from 'axios';

const url = 'http://localhost:8080/pagos';

class PagoService{
    mostrarPagos(codigo){
        return axios.get(url + '/' + codigo);
    }
    generarPago(){
        return axios.post(url);
    }
}
const instance = new PagoService();
export default instance;