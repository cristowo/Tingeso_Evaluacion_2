package milkstgo.proveedorservice.repositories;

import milkstgo.proveedorservice.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Integer> {

    @Query("select p from ProveedorEntity p where p.codigo = :codigo")
    ProveedorEntity findProveedorByCodigo(@Param("codigo") String codigo);

    @Query("select p from ProveedorEntity p")
    List<ProveedorEntity> findAllProveedores();

    // Hay que adaptar funcion para reunir a los proveedores que tengan llegada y acopio.
    //@Query("select DISTINCT l.proveedor from LlegadaEntity l, ResultadoEntity r where l.proveedor = r.proveedor")
    //ArrayList<String> findAllProvedoresByLlegadaAndResultado();

}
