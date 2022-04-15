package es.perseofic.coordle.servicio;

import java.util.Optional;

import es.perseofic.coordle.modelo.Ciudad;

public interface CiudadServicio {

	public Ciudad save(Ciudad c);

	public Iterable<Ciudad> list();

	public Iterable<Ciudad> list(Optional<String> query);

	public Optional<Ciudad> get(long id);

	public Optional<Ciudad> update(long id, Ciudad c);

	public boolean delete(long id);

}
