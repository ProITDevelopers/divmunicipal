package ao.proitconsulting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProvinciaDTO {
	
	private Long id;
	@JsonProperty(value = "provincia")
	private String nome;

}
