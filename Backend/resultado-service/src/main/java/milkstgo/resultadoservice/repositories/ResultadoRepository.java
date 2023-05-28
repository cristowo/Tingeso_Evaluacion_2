package milkstgo.resultadoservice.repositories;

import milkstgo.resultadoservice.entities.ResultadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoRepository extends JpaRepository<ResultadoEntity, Integer> {
    @Query("select p from ResultadoEntity p where p.proveedor = :Codigo")
    ResultadoEntity findResultadosByCodigoProveedor(@Param("Codigo") String Codigo);
}
