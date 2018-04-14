package cat.reisigualada.reis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import cat.reisigualada.reis.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	/** Buscar un Usuari per Id */
	Role findById(Long id);
	/** Buscar un Usuari per nom */
	Role findByName(String name);
    /** Buscar un Usuaris ordenats per id Ascendent */
	List<Role> findAllByOrderByIdAsc();
    /** Buscar un Usuaris ordenats per id Descendent */
	List<Role> findAllByOrderByIdDesc();
    /** Buscar un Usuaris ordenats per name Ascendent */
	List<Role> findAllByOrderByNameAsc();
    /** Buscar un Usuaris ordenats per name Descendent */
	List<Role> findAllByOrderByNameDesc();
}
