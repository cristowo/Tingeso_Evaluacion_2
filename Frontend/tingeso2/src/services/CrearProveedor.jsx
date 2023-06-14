import axios from 'axios';

const url = 'http://localhost:8080/proveedores';

class CrearProveedorService{
    
    crearProveedor(){
        return axios.post(url);
    }
}