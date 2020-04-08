package ao.proitconsulting.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ao.proitconsulting.dto.ComunaDTO;
import ao.proitconsulting.dto.ComunaDetalhes;
import ao.proitconsulting.dto.MunicipioComunas;
import ao.proitconsulting.dto.MunicipioDTO;
import ao.proitconsulting.dto.MunicipioDetalhes;
import ao.proitconsulting.dto.ProvinciaDTO;
import ao.proitconsulting.dto.ProvinciaMunicipios;
import ao.proitconsulting.model.Comuna;
import ao.proitconsulting.model.Municipio;
import ao.proitconsulting.model.Provincia;

@Mapper(componentModel = "spring")
public  interface Converter {
	
	ProvinciaDTO convertToProvDTO(Provincia provincia);
	Provincia convertToProv(ProvinciaDTO provincia);
	ProvinciaMunicipios convertToProvMuniDTO(Provincia provincia);
	
	MunicipioDTO convertToMunDTO(Municipio municipio);
	Municipio convertToMun(MunicipioDTO municipioDTO);
	@Mappings({@Mapping(target = "provincia",expression = "java(municipio.getProvincia().getNome())")})
	MunicipioDetalhes convertToMunDetalhes(Municipio municipio);
	MunicipioComunas convertToMunComunas(Municipio miMunicipio);
	
	ComunaDTO convertToComDTO(Comuna comuna);
	Comuna converToComDTO(ComunaDTO comunaDTO);
	@Mappings({@Mapping(target = "municipio",expression = "java(comuna.getMunicipio().getNome())"),
		@Mapping(target = "provincia",expression = "java(comuna.getMunicipio().getProvincia().getNome())")})
	ComunaDetalhes converToComDetalhes(Comuna comuna);
}
