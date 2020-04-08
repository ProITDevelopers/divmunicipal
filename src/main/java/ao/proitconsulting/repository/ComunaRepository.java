package ao.proitconsulting.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import ao.proitconsulting.model.Comuna;

public interface ComunaRepository extends JpaRepository<Comuna, Long>{

}
