package milkstgo.pagoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LlegadaModel {
    private Date fecha;
    private String turno;
    private Integer kg_leche;
    private String proveedor;
}
