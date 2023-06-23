import axios from 'axios';

const url = 'http://gateway-service:8080/proveedores';

class ProveedoresService {
  mostrarProveedores() {
    return axios.get(url);
  }

  crearProveedor(nombre, codigo, categoria, retencion){
    return axios.post(`${url}?nombre=${nombre}&codigo=${codigo}&categoria=${categoria}&retencion=${retencion}`);
  }
}

const instance = new ProveedoresService();
export default instance;