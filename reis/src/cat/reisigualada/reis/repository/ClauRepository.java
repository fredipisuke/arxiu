package cat.reisigualada.reis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import cat.reisigualada.reis.model.Clau;

public interface ClauRepository extends JpaRepository<Clau, Long>{
	/** Buscar una clau per Id */
	Clau findById(Long id);
	/** Buscar una clau per name */
	List<Clau> findByName(String name);
	/** Buscar una clau per type */
	List<Clau> findByType(Long type);
	/** Buscar una clau per name i type */
	Clau findByNameAndType(String name, Long type);
	/** Buscar un Perfils ordenats per tipus i nom Ascendent */
	List<Clau> findAllByOrderByTypeAscNameAsc();
}
