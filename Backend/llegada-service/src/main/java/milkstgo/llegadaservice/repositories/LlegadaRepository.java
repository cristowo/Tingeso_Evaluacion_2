package milkstgo.llegadaservice.repositories;

import milkstgo.llegadaservice.entities.LlegadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface LlegadaRepository extends JpaRepository<LlegadaEntity, Integer> {
    @Query(value = "SELECT COUNT(DISTINCT l.fecha) FROM LlegadaEntity l where l.proveedor = :codigo")
    int countByProveedor(@Param("codigo") String codigo);

    @Query("select l from LlegadaEntity l where l.proveedor = :codigo")
    ArrayList<LlegadaEntity> findAllLlegadasByCodigoProveedor(@Param("codigo") String codigo);

    @Query("select count(l) from LlegadaEntity l where l.proveedor = :codigo and l.turno = :turno")
    Integer countTurnosById(@Param("codigo") String codigo, @Param("turno") String turno);
}
