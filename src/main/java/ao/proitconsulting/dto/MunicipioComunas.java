package ao.proitconsulting.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MunicipioComunas {
	
	private Long id;
	@JsonProperty(value = "municipio")
	private String nome;
	private List<ComunaDTO> comunas;

}
