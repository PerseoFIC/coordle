package es.perseofic.coordle.modelo;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Data;

@Entity
@Data
@Table(name = "intento")
public class Intento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@JoinColumn(name="id_coordle_dia")
	@ManyToOne(optional = false)
	private CoordleDia coordleDia;

	@NotNull
	@Column
	private Integer latitud;

	@NotNull
	@Column
	private Integer longitud;

	@NotNull
	@Column
	private Boolean valor1;

	@NotNull
	@Column
	private Boolean valor2;

	@NotNull
	@Column
	private Boolean valor3;

	@NotNull
	@Column
	private Boolean valor4;

	@NotNull
	@Column
	private Boolean valor5;

	@NotNull
	@Column
	private Boolean valor6;

	@NotNull
	@Column
	private Boolean valor7;

	@NotNull
	@Column
	private Integer direccion;

	@NotNull
	@Column
	private Boolean exito;

	@NotNull
	@Column
	private String etiquetas;

	@NotNull
	@Column
	private Integer numeroIntento;

	public Intento(Integer latitud, Integer longitud, Boolean valor1, Boolean valor2, Boolean valor3, Boolean valor4,
			Boolean valor5, Boolean valor6, Boolean valor7, Integer direccion, Boolean exito, String etiquetas, Integer numeroIntento) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.valor3 = valor3;
		this.valor4 = valor4;
		this.valor5 = valor5;
		this.valor6 = valor6;
		this.valor7 = valor7;
		this.direccion = direccion;
		this.exito = exito;
		this.etiquetas = etiquetas;
		this.numeroIntento = numeroIntento;
	}

	protected Intento() {
	}

	public String toString() {
		return "Intento [" + id + ", " + coordleDia + ", " + latitud + ", " + longitud + ", " + valor1 + ", " + valor2 + ", " + valor3 + ", " + valor4 +
				", " + valor5 + ", " + valor6 + ", " + valor7 + ", " + direccion + ", " + exito + ", " + etiquetas + ", " + numeroIntento + "]";
	}

}
