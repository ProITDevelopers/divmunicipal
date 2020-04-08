package ao.proitconsulting.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ao.proitconsulting.model.Municipio;
import ao.proitconsulting.model.Provincia;

@Repository
public interface ProvinciaRepository  extends JpaRepository<Provincia, Long>{
	Optional<Provincia> findByNome(String nome);
}
