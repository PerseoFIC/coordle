package es.perseofic.coordle.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.perseofic.coordle.modelo.CapitalJSON;
import es.perseofic.coordle.servicio.CapitalJSONServicio;

@RequestMapping("/capitales")
@RestController
public class CapitalController {

	private CapitalJSONServicio capitalServicio;

	@Autowired
	public CapitalController(CapitalJSONServicio capitalServicio) {
		this.capitalServicio = capitalServicio;
	}

	@PostMapping
	public CapitalJSON insert(@RequestBody CapitalJSON input) {
		return capitalServicio.save(input);
	}

	@GetMapping
	public void parsearJSON() throws IOException {
		String json = Files.readString(Path.of("capitales.json"));
		Gson gson = new Gson();
		Type capitalType = new TypeToken<List<CapitalJSON>>() {}.getType();
		List<CapitalJSON> capitales = gson.fromJson(json, capitalType);
		for (CapitalJSON capital : capitales) {
			capitalServicio.save(capital);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CapitalJSON> findById(@PathVariable long id) {
		return capitalServicio.get(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable long id) {
		if (capitalServicio.delete(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<CapitalJSON> updateById(@PathVariable long id, @RequestBody CapitalJSON input) {
		return capitalServicio.update(id, input).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
