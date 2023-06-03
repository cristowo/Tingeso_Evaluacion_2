package milkstgo.resultadoservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "resultados")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_resultado;
    private String proveedor;
    private Integer porcentaje_grasa;
    private Integer porcentaje_solido;
}
