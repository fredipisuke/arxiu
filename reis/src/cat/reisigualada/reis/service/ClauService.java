package cat.reisigualada.reis.service;

import java.util.List;
import cat.reisigualada.reis.model.Clau;

public interface ClauService {
	/** Buscar clau per id */
    Clau findById(Long id);
	/** Buscar clau per type */
    List<Clau> findByType(Long type);
	/** Buscar clau per nom i type */
    Clau findByNameAndType(String name, Long type);
	/** Buscar totes les claus */
	List<Clau> findAll();
    /** Buscar un Perfils ordenats per tipus i nom Ascendent */
	List<Clau> findAllByOrderByTypeAscNameAsc();
	/** Guardar una clau */
	void save(Clau clau);
	/** Esborrar una clau */
	void deleteById(Long id);	
}
