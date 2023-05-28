package milkstgo.proveedorservice.controllers;

import milkstgo.proveedorservice.entities.ProveedorEntity;
import milkstgo.proveedorservice.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/nuevo") public String nuevo(){return "vista";}

    @PostMapping()
    public String createProveedor(@RequestParam("nombre") String nombre,
                                  @RequestParam("codigo") String codigo,
                                  @RequestParam("categoria") String categoria,
                                  @RequestParam("retencion") String retencion){
        proveedorService.createProveedor(nombre, codigo, categoria, retencion);
        return "redirect:/post-createxd";
    }

    @GetMapping()
    public String getProveedores(Model model){
        List<ProveedorEntity> proveedores = proveedorService.getAllProveedor();
        model.addAttribute("proveedores", proveedores);
        return "listaProveedores";
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProveedorEntity> getProveedorByCodigo(@PathVariable("codigo") String codigo) {
        return ResponseEntity.ok(proveedorService.getProveedor(codigo));
    }

    @DeleteMapping("/{codigo}")
    public void deleteProveedor(@PathVariable("codigo") String codigo){
        proveedorService.deleteProveedor(codigo, Boolean.TRUE);
    }
}
