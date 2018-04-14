package cat.reisigualada.reis.service;

import java.util.List;
import cat.reisigualada.reis.model.Role;

public interface RoleService {
	/** Buscar Perfil per id */
    Role findById(Long id);
	/** Buscar Perfil per nom */
    Role findByName(String name);
	/** Buscar tots els Perfils */
	List<Role> findAll();
    /** Buscar un Perfils ordenats per id Ascendent */
	List<Role> findAllByOrderByIdAsc();
    /** Buscar un Perfils ordenats per id Descendent */
	List<Role> findAllByOrderByIdDesc();
    /** Buscar un Perfils ordenats per name Ascendent */
	List<Role> findAllByOrderByNameAsc();
    /** Buscar un Perfils ordenats per name Descendent */
	List<Role> findAllByOrderByNameDesc();
	/** Guardar un Perfil */
	void save(Role role);
	/** Esborrar un Perfil */
	void deleteById(Long id);	
}
