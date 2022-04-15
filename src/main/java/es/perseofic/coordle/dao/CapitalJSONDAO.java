package es.perseofic.coordle.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.perseofic.coordle.modelo.CapitalJSON;
import es.perseofic.coordle.repositorio.CapitalJSONRepositorio;
import es.perseofic.coordle.servicio.CapitalJSONServicio;

@Service
public class CapitalJSONDAO implements CapitalJSONServicio {

	private CapitalJSONRepositorio capitalJSONRepositorio;

	@Autowired
	public CapitalJSONDAO(CapitalJSONRepositorio capitalJSONRepositorio) {
		this.capitalJSONRepositorio = capitalJSONRepositorio;
	}

	public CapitalJSON save(CapitalJSON capitalJSON) {
		return capitalJSONRepositorio.save(capitalJSON);
	}

	public Iterable<CapitalJSON> list() {
		return this.list(Optional.empty());
	}

	public Iterable<CapitalJSON> list(Optional<String> query) {
		return query.map((String q) -> capitalJSONRepositorio.search(q)).orElseGet(() -> capitalJSONRepositorio.findAll());
	}

	public Optional<CapitalJSON> get(long id) {
		return capitalJSONRepositorio.findById(id);
	}

	public Optional<CapitalJSON> update(long id, CapitalJSON capitalJSON) {
		if (!capitalJSONRepositorio.existsById(id)) {
			return Optional.empty();
		}
		capitalJSON.setId(id);
		return Optional.of(capitalJSONRepositorio.save(capitalJSON));
	}

	public boolean delete(long id) {
		if (!capitalJSONRepositorio.existsById(id)) {
			return false;
		}
		capitalJSONRepositorio.deleteById(id);
		return true;
	}

}
