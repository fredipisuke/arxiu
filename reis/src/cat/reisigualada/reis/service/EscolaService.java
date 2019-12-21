package cat.reisigualada.reis.service;

import java.util.List;
import cat.reisigualada.reis.model.Escola;

public interface EscolaService {
	/** Buscar clau per id */
    Escola findById(Long id);
	/** Buscar clau per name */
    Escola findByDescripcio(String descripcio);
	/** Buscar totes les claus */
	List<Escola> findAll();
	/** Buscar totes les claus */
	List<Escola> findAllByOrderByDescripcio();
	/** Guardar una clau */
	void save(Escola escola);
	/** Esborrar una clau */
	void deleteById(Long id);	
}
