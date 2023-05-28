package milkstgo.resultadoservice.repositories;

import milkstgo.resultadoservice.entities.ResultadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultadoRepository extends JpaRepository<ResultadoEntity, Integer> {
}
