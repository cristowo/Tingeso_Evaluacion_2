import axios from 'axios';

const url = 'http://localhost:8080/llegadas';

class SubirAcopioService{
    subirAcopio(acopio){
        return axios.post(url, acopio);
    }
}