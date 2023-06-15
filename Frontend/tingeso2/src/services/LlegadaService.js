import axios from 'axios';

const url = 'http://localhost:8080/llegadas';

class LlegadaService{
    subirAcopio(acopio){
        return axios.post(url+'/file', acopio);
    }
    obtenerAcopios(){
        return axios.get(url+'/info');
    }
}

const instance = new LlegadaService();
export default instance;