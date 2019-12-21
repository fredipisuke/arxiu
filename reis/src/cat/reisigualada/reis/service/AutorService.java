package cat.reisigualada.reis.service;

import java.util.List;
import cat.reisigualada.reis.model.Autor;

public interface AutorService {
	/** Buscar clau per id */
    Autor findById(Long id);
	/** Buscar clau per name */
    Autor findByName(String name);
	/** Buscar totes les claus */
	List<Autor> findAll();
	/** Guardar una clau */
	void save(Autor autor);
	/** Esborrar una clau */
	void deleteById(Long id);	
}
