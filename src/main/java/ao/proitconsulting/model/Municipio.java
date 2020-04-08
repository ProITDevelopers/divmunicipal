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
@NoArgsConstructor
@Entity
public class Municipio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "municipio")
	private List<Comuna> comunas;
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
	private Provincia provincia;
	
	public void addcomuna( Comuna comuna) {
		comuna.setMunicipio(this);
		this.comunas.add(comuna);
	}
	
	
}
