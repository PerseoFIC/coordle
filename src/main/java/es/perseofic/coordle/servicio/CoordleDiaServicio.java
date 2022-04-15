package es.perseofic.coordle.servicio;

import java.util.Optional;

import es.perseofic.coordle.modelo.CoordleDia;

public interface CoordleDiaServicio {

	public CoordleDia create();

	public CoordleDia save(CoordleDia c);

	public Iterable<CoordleDia> list();

	public Iterable<CoordleDia> list(Optional<String> query);

	public Optional<CoordleDia> get();

	public Optional<CoordleDia> get(long id);

	public boolean delete(long id);

}
