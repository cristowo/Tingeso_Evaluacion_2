import axios from 'axios';

const url = 'http://localhost:8080/proveedores';

class MostrarProveedoresService {
  mostrarProveedores() {
    return axios.get(url);
  }
}

const instance = new MostrarProveedoresService();
export default instance;