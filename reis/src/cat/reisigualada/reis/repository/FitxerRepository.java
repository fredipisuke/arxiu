package cat.reisigualada.reis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import cat.reisigualada.reis.model.Fitxer;

public interface FitxerRepository extends JpaRepository<Fitxer, Long>{
	/** Buscar un Fitxer per Id */
	Fitxer findById(Long id);
	/** Buscar tots els fitxers */
	List<Fitxer> findAllByOrderById();
}
