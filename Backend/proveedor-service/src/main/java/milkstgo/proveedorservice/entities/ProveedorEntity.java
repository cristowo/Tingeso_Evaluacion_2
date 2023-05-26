package milkstgo.proveedorservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "proveedores")
@Entity
public class ProveedorEntity {
    @Id
    private String codigo;
    private String nombre;
    private String categoria;
    private String retencion;
}
