package es.perseofic.coordle.dao;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.perseofic.coordle.modelo.Ciudad;
import es.perseofic.coordle.modelo.CoordleDia;
import es.perseofic.coordle.repositorio.CiudadRepositorio;
import es.perseofic.coordle.repositorio.CoordleDiaRepositorio;
import es.perseofic.coordle.servicio.CoordleDiaServicio;

@Service
public class CoordleDiaDAO implements CoordleDiaServicio {

	private CoordleDiaRepositorio coordleDiaRepositorio;
	private CiudadRepositorio ciudadRepositorio;

	@Autowired
	public CoordleDiaDAO(CoordleDiaRepositorio coordleDiaRepositorio, CiudadRepositorio ciudadRepositorio) {
		this.coordleDiaRepositorio = coordleDiaRepositorio;
		this.ciudadRepositorio = ciudadRepositorio;
	}

	public CoordleDia create() {
		int count = (int) ciudadRepositorio.count();
		long id = ThreadLocalRandom.current().nextInt(1, count);
		Optional<Ciudad> ciudad = ciudadRepositorio.findById(id);
		CoordleDia coordleDia = new CoordleDia(ciudad.get());
		return coordleDiaRepositorio.save(coordleDia);
	}

	public CoordleDia save(CoordleDia coordleDia) {
		return coordleDiaRepositorio.save(coordleDia);
	}

	public Iterable<CoordleDia> list() {
		return this.list(Optional.empty());
	}

	public Iterable<CoordleDia> list(Optional<String> query) {
		return query.map((String q) -> coordleDiaRepositorio.search(q)).orElseGet(() -> coordleDiaRepositorio.findAll());
	}

	public Optional<CoordleDia> get() {
		return coordleDiaRepositorio.findFirstByOrderByIdDesc();
	}

	public Optional<CoordleDia> get(long id) {
		return coordleDiaRepositorio.findById(id);
	}

	public boolean delete(long id) {
		if (!coordleDiaRepositorio.existsById(id)) {
			return false;
		}
		coordleDiaRepositorio.deleteById(id);
		return true;
	}

}
