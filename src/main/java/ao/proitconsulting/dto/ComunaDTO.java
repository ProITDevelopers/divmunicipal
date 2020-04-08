package ao.proitconsulting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ComunaDTO {
	private Long id;
	@JsonProperty(value = "comuna")
	private String nome;
}
