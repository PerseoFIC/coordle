package es.perseofic.coordle.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.perseofic.coordle.modelo.Busqueda;
import es.perseofic.coordle.modelo.CoordleDia;
import es.perseofic.coordle.servicio.CoordleDiaServicio;

@RequestMapping("/coordledia")
@RestController
public class CoordleDiaController {

	private CoordleDiaServicio coordleDiaServicio;

	@Autowired
	public CoordleDiaController(CoordleDiaServicio intentoServicio) {
		this.coordleDiaServicio = intentoServicio;
	}

	@PostMapping
	public CoordleDia insert(@RequestBody CoordleDia intento) {
		return coordleDiaServicio.save(intento);
	}

	@GetMapping
	public Busqueda getCoordleDiaActual() {
		CoordleDia coordleDia;
		Optional<CoordleDia> optionalCoordleDia = coordleDiaServicio.get();
		if (optionalCoordleDia.isPresent()) {
			coordleDia = optionalCoordleDia.get();
		} else {
			coordleDia = coordleDiaServicio.create();
		}
		return new Busqueda(coordleDia.getId(), coordleDia.getCiudad().getNombre(), coordleDia.getCiudad().getTerritorio(), coordleDia.getCiudad().getAbreviatura());
	}

	@GetMapping("/nuevo")
	public Busqueda getNuevoCoordleDia() {
		CoordleDia coordleDia = coordleDiaServicio.create();
		return new Busqueda(coordleDia.getId(), coordleDia.getCiudad().getNombre(), coordleDia.getCiudad().getTerritorio(), coordleDia.getCiudad().getAbreviatura());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CoordleDia> findById(@PathVariable long id) {
		return coordleDiaServicio.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable long id) {
		if (coordleDiaServicio.delete(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
