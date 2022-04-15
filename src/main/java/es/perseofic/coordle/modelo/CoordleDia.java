package es.perseofic.coordle.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Data;

@Entity
@Data
@Table(name = "coordledia")
public class CoordleDia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column
	private LocalDate dia;

	@NotNull
	@JoinColumn(name="id_ciudad")
	@ManyToOne(optional = false)
	private Ciudad ciudad;

	public CoordleDia(Ciudad ciudad) {
		this.dia = LocalDate.now();
		this.ciudad = ciudad;
	}

	public CoordleDia(int id) {
		this.id = id;
	}

	protected CoordleDia() {
	}

	public String toString() {
		return "CoordleDia [" + id + ", " + dia + ", " + ciudad + "]";
	}

}
