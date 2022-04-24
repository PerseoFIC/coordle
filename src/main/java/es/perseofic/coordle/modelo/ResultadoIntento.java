package es.perseofic.coordle.modelo;

import java.io.Serializable;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class ResultadoIntento implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Boolean valor1;

	@NotNull
	private Boolean valor2;

	@NotNull
	private Boolean valor3;

	@NotNull
	private Boolean valor4;

	@NotNull
	private Boolean valor5;

	@NotNull
	private Boolean valor6;

	@NotNull
	private Boolean valor7;

	@NotNull
	private Integer direccion;

	@NotNull
	private Boolean exito;

	private String coordenadasCorrectas;

	public ResultadoIntento(Boolean valor1, Boolean valor2, Boolean valor3, Boolean valor4, Boolean valor5,
			Boolean valor6, Boolean valor7, Integer direccion, Boolean exito, String coordenadasCorrectas) {
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.valor3 = valor3;
		this.valor4 = valor4;
		this.valor5 = valor5;
		this.valor6 = valor6;
		this.valor7 = valor7;
		this.direccion = direccion;
		this.exito = exito;
		this.coordenadasCorrectas = coordenadasCorrectas;
	}

	public ResultadoIntento(Intento intento, CoordleDia coordleDia) {
		this.valor1 = intento.getValor1();
		this.valor2 = intento.getValor2();
		this.valor3 = intento.getValor3();
		this.valor4 = intento.getValor4();
		this.valor5 = intento.getValor5();
		this.valor6 = intento.getValor6();
		this.valor7 = intento.getValor7();
		this.direccion = intento.getDireccion();
		this.exito = intento.getExito();
		if (intento.getNumeroIntento() == 5) {
			this.coordenadasCorrectas = coordleDia.getCiudad().getCoordenadas();
		}
	}

	protected ResultadoIntento() {
	}

	public String toString() {
		return "ResultadoIntento [" + valor1 + ", " + valor2 + ", " + valor3 + ", " + valor4 +
				", " + valor5 + ", " + valor6 + ", " + valor7 + ", " + direccion + ", " + exito + "]";
	}

}
