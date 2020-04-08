package ao.proitconsulting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MunicipioDTO {
	private Long id;
	@JsonProperty(value = "municipio")
	private String nome;

}
