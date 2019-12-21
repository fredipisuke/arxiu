package cat.reisigualada.reis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import cat.reisigualada.reis.model.Escola;

public interface EscolaRepository extends JpaRepository<Escola, Long>{
	/** Buscar una escola per Id */
	Escola findById(Long id);
	/** Buscar una escola per Name */
	Escola findByDescripcio(String descripcio);
	/** Buscar una escola ordenada per Descripcio */
	List<Escola> findAllByOrderByDescripcio();
}
