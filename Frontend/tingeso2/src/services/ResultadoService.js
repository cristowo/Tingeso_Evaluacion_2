import axios from 'axios';

const url = 'http://localhost:8080/resultados';

class ResultadoService{
    subirAcopio(resultados){
        return axios.post(url+'/file', resultados);
    }

    obtenerResultados(){
        return axios.get(url+'/info');
    }
}
const resultadoService = new ResultadoService();
export default resultadoService;