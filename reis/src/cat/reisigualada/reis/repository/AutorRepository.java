package cat.reisigualada.reis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cat.reisigualada.reis.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{
	/** Buscar una clau per Id */
	Autor findById(Long id);
	/** Buscar una clau per Name */
	Autor findByName(String name);
}
