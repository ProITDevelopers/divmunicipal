package ao.proitconsulting.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProvinciaMunicipios {
	private Long id;
	@JsonProperty(value = "provincia")
	private String nome;
	private List<MunicipioDTO> municipios;
}
