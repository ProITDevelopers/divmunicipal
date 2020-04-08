package ao.proitconsulting.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MunicipioDetalhes {
	private Long id;
	@JsonProperty(value = "municipio")
	private String nome;
	private String provincia;
}
