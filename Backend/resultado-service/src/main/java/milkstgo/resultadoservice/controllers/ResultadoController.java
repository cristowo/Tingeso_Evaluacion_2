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
    public String upload(@RequestParam("file") MultipartFile archivo, RedirectAttributes redirectAttributes){
        resultadoService.guardarResultado(archivo);
        redirectAttributes.addFlashAttribute("mensajeResultado", resultadoService.leerCsv("Resultados.csv"));
        return "redirect:/subirArchivo";
    }

    @GetMapping("/info")
    public ArrayList<ResultadoEntity> listar(Model model){
        ArrayList<ResultadoEntity> datos = resultadoService.obtenerDatosResultado();
        //model.addAttribute("datos", datos);
        //return "informacionArchivoResultado";
        return datos;
    }
}
