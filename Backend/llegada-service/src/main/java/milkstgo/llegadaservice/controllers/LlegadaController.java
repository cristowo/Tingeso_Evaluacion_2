package milkstgo.llegadaservice.controllers;

import milkstgo.llegadaservice.entities.LlegadaEntity;
import milkstgo.llegadaservice.services.LlegadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RestController
@RequestMapping("/llegadas")
public class LlegadaController {
    @Autowired
    private LlegadaService llegadaService;

    @GetMapping("/{codigo}")
    public ResponseEntity<ArrayList<LlegadaEntity>> getLlegdasByCodigo(@PathVariable("codigo") String codigo) {
        return ResponseEntity.ok(llegadaService.getLlegadasByCodigo(codigo));
    }
    @GetMapping("/totalDays/{codigo}")
    public ResponseEntity<Integer> getTotalDays(@PathVariable("codigo") String codigo) {
        return ResponseEntity.ok(llegadaService.getTotalDays(codigo));
    }
    @GetMapping("/totalTurnos/{codigo}/{turno}")
    public ResponseEntity<Integer> getTotalTurnos(@PathVariable("codigo") String codigo, @PathVariable("turno") String turno) {
        return ResponseEntity.ok(llegadaService.countTurnosById(codigo, turno));
    }
    @PostMapping("/file")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile archivo){
        return ResponseEntity.ok(llegadaService.guardar(archivo));
    }
    @GetMapping("/info")
    public ResponseEntity<ArrayList<LlegadaEntity>> listar(){
        return ResponseEntity.ok(llegadaService.obtenerDatos());
    }
}
