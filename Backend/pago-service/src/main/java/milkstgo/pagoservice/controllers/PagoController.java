package milkstgo.pagoservice.controllers;

import milkstgo.pagoservice.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/pago")
public class PagoController {
    @Autowired
    PagoService pagoService;
    @PostMapping()
    public String upload(){
        pagoService.imprimirCodigoProveedores();
        return "redirect:/opcionesGestion";
    }
}
