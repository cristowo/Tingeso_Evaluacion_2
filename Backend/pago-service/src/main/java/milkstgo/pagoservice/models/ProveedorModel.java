package milkstgo.pagoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorModel {
    private String codigo;
    private String nombre;
    private String categoria;
    private String retencion;
}
