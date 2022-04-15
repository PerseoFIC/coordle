package es.perseofic.coordle.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.perseofic.coordle.modelo.Intento;

public interface IntentoRepositorio extends JpaRepository<Intento, Long> {

	@Query(value = "SELECT * FROM intento c WHERE c.latitud ~* :query OR c.latitud ~* :query", nativeQuery = true)
	List<Intento> search(@Param("query") String query);

}
