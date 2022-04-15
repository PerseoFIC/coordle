package es.perseofic.coordle.servicio;

import java.util.Optional;

import es.perseofic.coordle.modelo.CoordleDia;
import es.perseofic.coordle.modelo.Intento;

public interface IntentoServicio {

	public Intento save(Intento i);

	public Iterable<Intento> list();

	public Iterable<Intento> list(Optional<String> query);

	public Optional<Intento> get(long id);

	public Optional<Intento> update(long id, Intento i);

	public boolean delete(long id);

	public void calcularExito(Intento intento, CoordleDia coordleDia);

}
