package es.perseofic.coordle.servicio;

import java.util.Optional;

import es.perseofic.coordle.modelo.CapitalJSON;

public interface CapitalJSONServicio {

	public CapitalJSON save(CapitalJSON c);

	public Iterable<CapitalJSON> list();

	public Iterable<CapitalJSON> list(Optional<String> query);

	public Optional<CapitalJSON> get(long id);

	public Optional<CapitalJSON> update(long id, CapitalJSON c);

	public boolean delete(long id);

}
