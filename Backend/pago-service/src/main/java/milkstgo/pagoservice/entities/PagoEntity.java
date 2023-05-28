package milkstgo.pagoservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pago;
    private Integer id_resultado;
    private String quincena;
    private String codigoProveedor;
    private String nombreProveedor;
    private double totalKlsLeche;
    private int numDiasEnvioLeche;
    private double promedioDiarioKlsLeche;
    private double porcentajeVariacionLeche;
    private double porcentajeGrasa;
    private double porcentajeVariacionGrasa;
    private double porcentajeSolidosTotales;
    private double porcentajeVariacionST;
    private double pagoPorLeche;
    private double pagoPorGrasa;
    private double pagoPorSolidosTotales;
    private double bonificacionPorFrecuencia;
    private double descuentoVariacionLeche;
    private double descuentoVariacionGrasa;
    private double descuentoVariacionST;
    private double pagoTotal;
    private double montoRetencion;
    private double montoFinal;
}
