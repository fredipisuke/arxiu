package cat.reisigualada.reis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import cat.reisigualada.reis.model.Nen;

public interface NenRepository extends JpaRepository<Nen, Long>{
	/** Buscar un Fitxer per Id */
	Nen findById(Long id);
	/** Buscar tots els fitxers */
	List<Nen> findAllByOrderById();
}
