package es.perseofic.coordle.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.perseofic.coordle.modelo.CapitalJSON;

public interface CapitalJSONRepositorio extends JpaRepository<CapitalJSON, Long> {

	@Query(value = "SELECT * FROM capitaljson c WHERE c.capitalName ~* :query", nativeQuery = true)
	List<CapitalJSON> search(@Param("query") String query);

}
