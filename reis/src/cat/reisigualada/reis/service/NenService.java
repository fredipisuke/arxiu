package cat.reisigualada.reis.service;

import java.util.List;
import cat.reisigualada.reis.model.Nen;

public interface NenService {
	/** Buscar Fitxer per id */
    Nen findById(Long id);
    /** Buscar tots els fitxers */
	List<Nen> findAllByOrderById();
	/** Guardar un Fitxer */
	void save(Nen nen);
	/** Esborrar un Fitxer */
	void delete(Nen nen);	
}
