package ao.proitconsulting.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ao.proitconsulting.converter.Converter;
import ao.proitconsulting.dto.ComunaDTO;
import ao.proitconsulting.dto.ComunaDetalhes;
import ao.proitconsulting.dto.MunicipioComunas;
import ao.proitconsulting.dto.MunicipioDTO;
import ao.proitconsulting.dto.MunicipioDetalhes;
import ao.proitconsulting.dto.ProvinciaDTO;
import ao.proitconsulting.dto.ProvinciaMunicipios;
import ao.proitconsulting.exception.DuplicateDateException;
import ao.proitconsulting.model.Comuna;
import ao.proitconsulting.model.Municipio;
import ao.proitconsulting.model.Provincia;
import ao.proitconsulting.repository.ComunaRepository;
import ao.proitconsulting.repository.MunicipioRepository;
import ao.proitconsulting.repository.ProvinciaRepository;

@RestController
@RequestMapping("/api/v1")
public class DivController {
	
	private ProvinciaRepository pr;
	private ComunaRepository  cr;
	private MunicipioRepository mr;
	private Converter conv;
	
	@Autowired
	public DivController(ProvinciaRepository pr, ComunaRepository cr, MunicipioRepository mr,Converter conv) {
		super();
		this.pr = pr;
		this.cr = cr;
		this.mr = mr;
		this.conv = conv;
	}
	
	//****************************Methods for provincia****************************************
	@GetMapping("/provincias")
	public ResponseEntity<List<ProvinciaDTO>>  getAllProvincia(){
		List<ProvinciaDTO> lProv = pr.findAll().stream().map(conv::convertToProvDTO).collect(Collectors.toList());
		return new ResponseEntity<>(lProv, HttpStatus.OK);
	}
	
	@PostMapping("/provincias")
	public ResponseEntity<List<ProvinciaDTO>> saveProvincia(@RequestBody Set<ProvinciaDTO> provinciaDTO) throws DuplicateDateException{
		
		for( ProvinciaDTO p : provinciaDTO) {
			if( pr.findByNome(p.getNome()).isPresent() )
				throw new DuplicateDateException("provincia "+ p.getNome() + " ja existe");
		}
		
		List<Provincia> p = provinciaDTO.stream().map(conv::convertToProv).collect(Collectors.toList());
		return new ResponseEntity<>(pr.saveAll(p).stream().map(conv::convertToProvDTO).collect(Collectors.toList()), HttpStatus.CREATED);
	}
	
	@PutMapping("/provincias/{id}")
	public ResponseEntity<ProvinciaDTO> updateProvincia(@PathVariable Long id, @RequestBody ProvinciaDTO provinciaDTO){
		
		Optional<Provincia> optinalProv = pr.findById(id);
		
		if( optinalProv.isEmpty() )
			return ResponseEntity.notFound().build();
		
		Provincia p = optinalProv.get();
		p.setNome(provinciaDTO.getNome());
		ProvinciaDTO pDTO = conv.convertToProvDTO(pr.save(p));
		
		return new ResponseEntity<>(pDTO, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/provincias/{id}")
	public void deleteProvincia(@PathVariable Long id){
		 pr.deleteById(id);
	}
	
	@PostMapping("/provincias/{id}/municipios")
	public ResponseEntity<ProvinciaMunicipios> saveMunicipio(@PathVariable Long id,@RequestBody List<MunicipioDTO> municipios) 
				throws DuplicateDateException{
		
		Optional<Provincia> optinalProv = pr.findById(id);
		
		if( optinalProv.isEmpty() )
			return ResponseEntity.notFound().build();
		
		Provincia p = optinalProv.get();
		
		for( MunicipioDTO mDTO : municipios) {
			if( p.getMunicipios().stream().filter(m-> m.getNome().equals(mDTO.getNome())).findFirst().isPresent()  )
				throw new DuplicateDateException("Municipio "+ p.getNome() + " ja existe");
		}
		
		municipios.stream().map(conv::convertToMun).forEach(p::addMunicipio);
		
		return new ResponseEntity<>(conv.convertToProvMuniDTO(pr.save(p)), HttpStatus.CREATED);
	}
	
	
	//****************************Methods for Municipio****************************************
	
	@GetMapping("/municipios")
	public ResponseEntity<List<MunicipioDetalhes>> getMunicipio(){
		List<MunicipioDetalhes> lMun = mr.findAll().stream().map(conv::convertToMunDetalhes).collect(Collectors.toList());
		return new ResponseEntity<>(lMun, HttpStatus.OK);
	}
	
	@GetMapping("/municipios/{id}")
	public ResponseEntity<MunicipioDetalhes> getMunicipioDetalhes(@PathVariable Long id){
		Optional<Municipio> optionalPMun = mr.findById(id);
		
		if( optionalPMun.isEmpty() )
			return ResponseEntity.notFound().build();
		
		return new ResponseEntity<>(optionalPMun.map(conv::convertToMunDetalhes).get(), HttpStatus.OK);
	}
	
	
	@PutMapping("/municipios/{id}")
	public ResponseEntity<MunicipioDetalhes> updateMunicipio(@PathVariable Long id, @RequestBody MunicipioDTO municipioDTO){
		
		Optional<Municipio> optionalMun = mr.findById(id);
		
		if( optionalMun.isEmpty() )
			return ResponseEntity.notFound().build();
		
		Municipio m = optionalMun.get();
		m.setId(id);
		m.setNome(municipioDTO.getNome());
		MunicipioDetalhes mDTO = conv.convertToMunDetalhes(mr.save(m));
		
		return new ResponseEntity<>(mDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/municipios/{id}")
	public void deleteMun(@PathVariable Long id){
		 mr.deleteById(id);
	}
	
	@PostMapping("/municipios/{id}/comunas")
	public ResponseEntity<MunicipioComunas> saveComunas(@PathVariable Long id,@RequestBody List<ComunaDTO> comunas){
		Optional<Municipio> optionalMuni = mr.findById(id);
		
		if( optionalMuni.isEmpty() )
			return ResponseEntity.notFound().build();
		
		
		Municipio m = optionalMuni.get();
		
		comunas.stream().map(conv::converToComDTO).forEach(m::addcomuna);
		/*
		 * Comuna c = conv.converToComDTO(comunaDTO); m.addcomuna(c);
		 */
		
		return new ResponseEntity<>(conv.convertToMunComunas(mr.save(m)), HttpStatus.CREATED);
	}
	
	//listar todos os municipios por provincias
	@GetMapping("/provincias/{id}/municipios")
	public ResponseEntity<ProvinciaMunicipios> getMunicipioPorProvincia(@PathVariable Long id) 
				throws DuplicateDateException{
		
		Optional<Provincia> optinalProv = pr.findById(id);
		
		if( optinalProv.isEmpty() )
			return ResponseEntity.notFound().build();
		
		Provincia p = optinalProv.get();
		
		return new ResponseEntity<>(conv.convertToProvMuniDTO(p), HttpStatus.CREATED);
	}
	
	//****************************Methods for comuna****************************************
	
	

	@GetMapping("/comunas")
	public ResponseEntity<List<ComunaDetalhes>> getComunas(){
		List<ComunaDetalhes> lCom = cr.findAll().stream().map(conv::converToComDetalhes).collect(Collectors.toList());
		return new ResponseEntity<>(lCom, HttpStatus.OK);
	}
	
	@GetMapping("/comunas/{id}")
	public ResponseEntity<ComunaDetalhes> getComunaDetalhes(@PathVariable Long id){
		Optional<Comuna> optionalCom = cr.findById(id);
		
		if( optionalCom.isEmpty() )
			return ResponseEntity.notFound().build();
		
		return new ResponseEntity<>(optionalCom.map(conv::converToComDetalhes).get(), HttpStatus.OK);
	}
	
	@PutMapping("/comunas/{id}")
	public ResponseEntity<ComunaDetalhes> updateComuna(@PathVariable Long id, @RequestBody ComunaDTO comunaDTO){
		
		Optional<Comuna> optionalCom = cr.findById(id);
		
		if( optionalCom.isEmpty() )
			return ResponseEntity.notFound().build();
		
		Comuna c = optionalCom.get();
		c.setId(id);
		c.setNome(comunaDTO.getNome());
		ComunaDetalhes cDTO = conv.converToComDetalhes(c);
		
		return new ResponseEntity<>(cDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/comunas/{id}")
	public void deleteComuna(@PathVariable Long id){
		 cr.deleteById(id);
	}

}
