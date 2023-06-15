import axios from 'axios';

const url = 'http://localhost:8080/resultados';

class SubirAcopioService{
    subirAcopio(resultados){
        return axios.post(url, resultados);
    }
}