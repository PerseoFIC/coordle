package es.perseofic.coordle.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.perseofic.coordle.modelo.Ciudad;
import es.perseofic.coordle.modelo.CoordleDia;
import es.perseofic.coordle.modelo.Intento;
import es.perseofic.coordle.repositorio.IntentoRepositorio;
import es.perseofic.coordle.servicio.IntentoServicio;

@Service
public class IntentoDAO implements IntentoServicio {

	private IntentoRepositorio intentoRepositorio;

	@Autowired
	public IntentoDAO(IntentoRepositorio intentoRepositorio) {
		this.intentoRepositorio = intentoRepositorio;
	}

	public Intento save(Intento intento) {
		return intentoRepositorio.save(intento);
	}

	public Iterable<Intento> list() {
		return this.list(Optional.empty());
	}

	public Iterable<Intento> list(Optional<String> query) {
		return query.map((String q) -> intentoRepositorio.search(q)).orElseGet(() -> intentoRepositorio.findAll());
	}

	public Optional<Intento> get(long id) {
		return intentoRepositorio.findById(id);
	}

	public Optional<Intento> update(long id, Intento intento) {
		if (!intentoRepositorio.existsById(id)) {
			return Optional.empty();
		}
		intento.setId(id);
		return Optional.of(intentoRepositorio.save(intento));
	}

	public boolean delete(long id) {
		if (!intentoRepositorio.existsById(id)) {
			return false;
		}
		intentoRepositorio.deleteById(id);
		return true;
	}

	public void calcularExito(Intento intento, CoordleDia coordleDia) {
		intento.setValor1(Integer.parseInt(intento.getEtiquetas().substring(0, 1)) == Math.abs(coordleDia.getCiudad().getLatitud() / 10));
		intento.setValor2(Integer.parseInt(intento.getEtiquetas().substring(1, 2)) == Math.abs(coordleDia.getCiudad().getLatitud() % 10));
		intento.setValor3(coordleDia.getCiudad().getLatitud() >= 0 ? intento.getEtiquetas().substring(2, 3).equals("N") : intento.getEtiquetas().substring(2, 3).equals("S"));
		intento.setValor4(Integer.parseInt(intento.getEtiquetas().substring(3, 4)) == Math.abs(coordleDia.getCiudad().getLongitud() / 100));
		intento.setValor5(Integer.parseInt(intento.getEtiquetas().substring(4, 5)) == (Math.abs(coordleDia.getCiudad().getLongitud() / 10) % 10));
		intento.setValor6(Integer.parseInt(intento.getEtiquetas().substring(5, 6)) == Math.abs(coordleDia.getCiudad().getLongitud() % 10));
		intento.setValor7(coordleDia.getCiudad().getLongitud() >= 0 ? intento.getEtiquetas().substring(6, 7).equals("E") : intento.getEtiquetas().substring(6, 7).equals("O"));
		intento.setDireccion(calcularDireccion(intento, coordleDia.getCiudad()));
		intento.setExito(intento.getValor1() && intento.getValor2() && intento.getValor3() && intento.getValor4() &&
				intento.getValor5() && intento.getValor6() && intento.getValor7());
	}

	private Integer calcularDireccion(Intento intento, Ciudad busqueda) {
		double longitud1 = intento.getLongitud();
		double longitud2 = busqueda.getLongitud();
		double latitud1 = Math.toRadians(intento.getLatitud());
		double latitud2 = Math.toRadians(busqueda.getLatitud());
		double longDiff = Math.toRadians(longitud2 - longitud1);
		double y = Math.sin(longDiff) * Math.cos(latitud2);
		double x = Math.cos(latitud1) * Math.sin(latitud2) - Math.sin(latitud1) * Math.cos(latitud2) * Math.cos(longDiff);

		double resultDegree = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
		return (int) Math.round(resultDegree / 45);
	}

}
