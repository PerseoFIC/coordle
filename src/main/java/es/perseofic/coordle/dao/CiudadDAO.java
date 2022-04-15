package es.perseofic.coordle.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.perseofic.coordle.modelo.Ciudad;
import es.perseofic.coordle.repositorio.CiudadRepositorio;
import es.perseofic.coordle.servicio.CiudadServicio;

@Service
public class CiudadDAO implements CiudadServicio {

	private CiudadRepositorio ciudadRepositorio;

	@Autowired
	public CiudadDAO(CiudadRepositorio ciudadRepositorio) {
		this.ciudadRepositorio = ciudadRepositorio;
	}

	public Ciudad save(Ciudad ciudad) {
		return ciudadRepositorio.save(ciudad);
	}

	public Iterable<Ciudad> list() {
		return this.list(Optional.empty());
	}

	public Iterable<Ciudad> list(Optional<String> query) {
		return query.map((String q) -> ciudadRepositorio.search(q)).orElseGet(() -> ciudadRepositorio.findAll());
	}

	public Optional<Ciudad> get(long id) {
		return ciudadRepositorio.findById(id);
	}

	public Optional<Ciudad> update(long id, Ciudad ciudad) {
		if (!ciudadRepositorio.existsById(id)) {
			return Optional.empty();
		}
		ciudad.setId(id);
		return Optional.of(ciudadRepositorio.save(ciudad));
	}

	public boolean delete(long id) {
		if (!ciudadRepositorio.existsById(id)) {
			return false;
		}
		ciudadRepositorio.deleteById(id);
		return true;
	}

}
