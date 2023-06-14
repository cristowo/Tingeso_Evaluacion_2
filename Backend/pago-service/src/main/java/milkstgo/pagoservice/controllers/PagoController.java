package milkstgo.pagoservice.controllers;

import milkstgo.pagoservice.entities.PagoEntity;
import milkstgo.pagoservice.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    @Autowired
    PagoService pagoService;
    @PostMapping
    public ResponseEntity<?> iniciar(){
        pagoService.iniciar();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PagoEntity>> getall(){
        return ResponseEntity.ok(pagoService.getAll());
    }
}
