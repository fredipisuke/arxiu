package cat.reisigualada.reis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import cat.reisigualada.reis.model.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long>{
	/** Buscar un Fitxer per Id */
	Tutor findById(Long id);
	/** Buscar tots els fitxers */
	List<Tutor> findAllByOrderById();
}
