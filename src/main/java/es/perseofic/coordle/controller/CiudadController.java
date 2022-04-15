package es.perseofic.coordle.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.perseofic.coordle.modelo.Ciudad;
import es.perseofic.coordle.servicio.CiudadServicio;

@RequestMapping("/ciudades")
@RestController
public class CiudadController {

	private CiudadServicio ciudadServicio;

	@Autowired
	public CiudadController(CiudadServicio ciudadServicio) {
		this.ciudadServicio = ciudadServicio;
	}

	@PostMapping
	public Ciudad insert(@RequestBody Ciudad input) {
		return ciudadServicio.save(input);
	}

	@GetMapping
	public Iterable<Ciudad> search(@RequestParam Optional<String> query) {
		return ciudadServicio.list(query);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ciudad> findById(@PathVariable long id) {
		return ciudadServicio.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable long id) {
		if (ciudadServicio.delete(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Ciudad> updateById(@PathVariable long id, @RequestBody Ciudad input) {
		return ciudadServicio.update(id, input).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
