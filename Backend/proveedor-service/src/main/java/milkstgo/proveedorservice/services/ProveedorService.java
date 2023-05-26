package milkstgo.proveedorservice.services;

import milkstgo.proveedorservice.entities.ProveedorEntity;
import milkstgo.proveedorservice.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public void createProveedor(String codigo, String nombre, String categoria, String retencion){
        ProveedorEntity proveedor = new ProveedorEntity(codigo, nombre, categoria, retencion);
        proveedorRepository.save(proveedor);
    }

    public List<ProveedorEntity> getAllProveedor(){
        return proveedorRepository.findAllProveedores();
    }

    public ProveedorEntity getProveedor(String codigo){
        return proveedorRepository.findProveedorByCodigo(codigo);
    }
    
    public void deleteProveedor(String codigo, Boolean condicon){
        ProveedorEntity proveedor = proveedorRepository.findProveedorByCodigo(codigo);
        //condiciones
        if(condicon == Boolean.TRUE ){
            //delete pagos
        }
        proveedorRepository.delete(proveedor);
    }

}
