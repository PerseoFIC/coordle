package es.perseofic.coordle.modelo;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Data;

@Entity
@Data
@Table(name = "capitaljson")
public class CapitalJSON implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column
	private String countryName;

	@NotNull
	@Column
	private String capitalName;

	@NotNull
	@Column
	private Double capitalLatitude;

	@NotNull
	@Column
	private Double capitalLongitude;

	@NotNull
	@Column
	private String countryCode;

	@NotNull
	@Column
	private String continentName;

	protected CapitalJSON() {
	}

	public String toString() {
		return "Capital [" + countryName + ", " + capitalName + ", " + capitalLatitude + ", " + capitalLongitude + ", " + countryCode + ", " + continentName + "]";
	}

}
