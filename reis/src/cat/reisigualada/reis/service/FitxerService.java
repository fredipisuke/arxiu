package cat.reisigualada.reis.service;

import java.util.List;
import cat.reisigualada.reis.model.Fitxer;

public interface FitxerService {
	/** Buscar Fitxer per id */
    Fitxer findById(Long id);
    /** Buscar tots els fitxers */
	List<Fitxer> findAllByOrderById();
	/** Guardar un Fitxer */
	void save(Fitxer fitxer);
	/** Esborrar un Fitxer */
	void deleteById(Long id);	
}
