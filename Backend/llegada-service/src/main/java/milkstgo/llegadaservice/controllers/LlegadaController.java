package milkstgo.llegadaservice.controllers;

import milkstgo.llegadaservice.entities.LlegadaEntity;
import milkstgo.llegadaservice.services.LlegadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RestController
@RequestMapping("/llegadas")
public class LlegadaController {
    @Autowired
    private LlegadaService llegadaService;

    @PostMapping("/File")
    public void upload(@RequestParam("file") MultipartFile archivo, RedirectAttributes redirectAttributes){
        llegadaService.guardar(archivo);
        redirectAttributes.addFlashAttribute("mensaje", llegadaService.leerCsv("Acopio.csv"));
    }

    @GetMapping("/informacionArchivo")
    public String listar(Model model){
        ArrayList<LlegadaEntity> datos = llegadaService.obtenerDatos();
        model.addAttribute("datos", datos);
        return "informacionArchivo";
    }
}
