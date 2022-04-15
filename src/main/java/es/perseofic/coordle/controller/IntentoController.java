package es.perseofic.coordle.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.perseofic.coordle.modelo.CoordleDia;
import es.perseofic.coordle.modelo.Intento;
import es.perseofic.coordle.modelo.ResultadoIntento;
import es.perseofic.coordle.servicio.CoordleDiaServicio;
import es.perseofic.coordle.servicio.IntentoServicio;

@RequestMapping("/intento")
@RestController
public class IntentoController {

	private IntentoServicio intentoServicio;

	private CoordleDiaServicio coordleDiaServicio;

	@Autowired
	public IntentoController(IntentoServicio intentoServicio, CoordleDiaServicio coordleDiaServicio) {
		this.intentoServicio = intentoServicio;
		this.coordleDiaServicio = coordleDiaServicio;
	}

	@PostMapping
	public ResultadoIntento insert(@RequestBody Intento intento) {
		CoordleDia coordleDia = coordleDiaServicio.get(intento.getCoordleDia().getId()).get();
		intentoServicio.calcularExito(intento, coordleDia);
		intentoServicio.save(intento);
		return new ResultadoIntento(intento);
	}

	@GetMapping
	public Iterable<Intento> search(@RequestParam Optional<String> query) {
		return intentoServicio.list(query);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Intento> findById(@PathVariable long id) {
		return intentoServicio.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable long id) {
		if (intentoServicio.delete(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Intento> updateById(@PathVariable long id, @RequestBody Intento input) {
		return intentoServicio.update(id, input).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
