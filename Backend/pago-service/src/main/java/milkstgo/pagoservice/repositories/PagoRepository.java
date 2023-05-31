package milkstgo.pagoservice.repositories;

import milkstgo.pagoservice.entities.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Integer> {
    @Query("select p from PagoEntity p where p.quincena = :fecha and p.codigoProveedor = :codigo")
    List<PagoEntity> findPagolist(@Param("fecha") String fecha, @Param("codigo") String codigo);

    @Query("select p from PagoEntity p where p.codigoProveedor = :codigo")
    List<PagoEntity> findPagoByCodigo(@Param("codigo") String codigo);

    @Query("select p from PagoEntity p")
    List<PagoEntity> getAll();

}
