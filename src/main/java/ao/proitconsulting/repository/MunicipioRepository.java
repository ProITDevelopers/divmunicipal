package ao.proitconsulting.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ao.proitconsulting.model.Municipio;


public interface MunicipioRepository extends JpaRepository<Municipio, Long>{
	Optional<Municipio> findByNome(String nome);
}
