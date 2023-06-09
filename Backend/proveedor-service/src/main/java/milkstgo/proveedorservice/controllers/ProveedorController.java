package milkstgo.proveedorservice.controllers;

import milkstgo.proveedorservice.entities.ProveedorEntity;
import milkstgo.proveedorservice.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<?> createProveedor(@RequestParam("nombre") String nombre,
                                                  @RequestParam("codigo") String codigo,
                                                  @RequestParam("categoria") String categoria,
                                                  @RequestParam("retencion") String retencion){
        proveedorService.createProveedor(nombre, codigo, categoria, retencion);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProveedorEntity>> getProveedores(){
        List<ProveedorEntity> proveedores = proveedorService.getAllProveedor();
        return  ResponseEntity.ok(proveedores);
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
