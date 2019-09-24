package cat.reisigualada.reis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.vo.FitxerKey;

public interface FitxerRepository extends JpaRepository<Fitxer, Long>{
	/** Buscar un Fitxer per Id */
	Fitxer findByPk(FitxerKey pk);
	/** Buscar tots els fitxers */
	List<Fitxer> findAllByOrderByPk();
}
