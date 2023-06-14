package milkstgo.resultadoservice.controllers;

import milkstgo.resultadoservice.entities.ResultadoEntity;
import milkstgo.resultadoservice.services.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RestController
@RequestMapping("/resultados")
public class ResultadoController {
    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("/{codigo}")
    public ResponseEntity<ResultadoEntity> getResultadoByCodigo(@PathVariable("codigo") String codigo) {
        return ResponseEntity.ok(resultadoService.obtenerResultadosByCodigo(codigo));
    }

    @PostMapping("/file")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile archivo){
        resultadoService.guardarResultado(archivo);
        return ResponseEntity.ok(resultadoService.leerCsv("Resultados.csv"));
    }

    @GetMapping("/info")
    public ResponseEntity<ArrayList<ResultadoEntity>> listar(){
        return ResponseEntity.ok(resultadoService.obtenerDatosResultado());
    }
}
