package es.perseofic.coordle.repositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.perseofic.coordle.modelo.CoordleDia;

public interface CoordleDiaRepositorio extends JpaRepository<CoordleDia, Long> {

	@Query(value = "SELECT * FROM coordledia c WHERE c.id ~* :query", nativeQuery = true)
	List<CoordleDia> search(@Param("query") String query);

	Optional<CoordleDia> findFirstByOrderByIdDesc();

}
