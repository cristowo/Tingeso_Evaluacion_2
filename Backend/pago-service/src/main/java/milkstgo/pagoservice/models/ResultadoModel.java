package milkstgo.pagoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoModel {
    private String proveedor;
    private Integer porcentaje_grasa;
    private Integer porcentaje_solido;
}
