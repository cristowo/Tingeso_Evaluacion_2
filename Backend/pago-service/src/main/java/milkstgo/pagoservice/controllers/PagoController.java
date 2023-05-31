package milkstgo.pagoservice.controllers;

import milkstgo.pagoservice.entities.PagoEntity;
import milkstgo.pagoservice.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    @Autowired
    PagoService pagoService;
    @PostMapping()
    public String upload(){
        pagoService.iniciar();
        return "redirect:/opcionesGestion";
    }

    @GetMapping()
    public List<PagoEntity> getall(){
        return pagoService.getAll();
    }
}
