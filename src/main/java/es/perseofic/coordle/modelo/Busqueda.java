package es.perseofic.coordle.modelo;

import java.io.Serializable;
import javax.validation.constraints.*;

import lombok.Data;

@Data
public class Busqueda implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private long idCoordleDia;

	@NotNull
	private String nombre;

	@NotNull
	private String territorio;

	@NotNull
	private String abreviatura;

	public Busqueda(long idCoordleDia, String nombre, String territorio, String abreviatura) {
		this.idCoordleDia = idCoordleDia;
		this.nombre = nombre;
		this.territorio = territorio;
		this.abreviatura = abreviatura;
	}

	protected Busqueda() {
	}

	public String toString() {
		return "Busqueda [" + idCoordleDia + ", " + nombre + ", " + territorio + ", " + abreviatura + "]";
	}

}
