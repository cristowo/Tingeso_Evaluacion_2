package milkstgo.llegadaservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "llegadas")
public class LlegadaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Llegada;
    private Date fecha;
    private String turno;
    private Integer kg_leche;
    private String proveedor;
}
