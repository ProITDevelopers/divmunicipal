package ao.proitconsulting.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Provincia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "provincia")
	private List<Municipio> municipios;
	
	public void addMunicipio( Municipio municipio) {
		municipio.setProvincia(this);
		this.municipios.add(municipio);
	}
	
}
