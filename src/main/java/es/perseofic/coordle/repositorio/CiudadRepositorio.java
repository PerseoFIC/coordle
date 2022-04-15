package es.perseofic.coordle.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.perseofic.coordle.modelo.Ciudad;

public interface CiudadRepositorio extends JpaRepository<Ciudad, Long> {

	@Query(value = "SELECT * FROM ciudad c WHERE c.nombre ~* :query OR c.estado ~* :query", nativeQuery = true)
	List<Ciudad> search(@Param("query") String query);

}
