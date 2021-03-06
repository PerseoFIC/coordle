package es.perseofic.coordle.modelo;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Data;

@Entity
@Data
@Table(name = "ciudad")
public class Ciudad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column
	private Integer latitud;

	@NotNull
	@Column
	private Integer longitud;

	@NotNull
	@Column
	private String nombre;

	@NotNull
	@Column
	private String territorio;

	@NotNull
	@Column
	private String abreviatura;

	protected Ciudad() {
	}

	public String getCoordenadas() {
		String coordenadas = Integer.toString(Math.abs(latitud));
		coordenadas += latitud >= 0 ? "N" : "S";
		coordenadas += Integer.toString(Math.abs(longitud));
		coordenadas += longitud >= 0 ? "E" : "O";
		return coordenadas;
	}

	public String toString() {
		return "Ciudad [" + id + ", " + nombre + ", " + territorio + ", " + abreviatura + ", " + latitud + ", " + longitud + "]";
	}

}
