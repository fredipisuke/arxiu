package cat.reisigualada.reis.service;

import java.util.List;

import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.vo.FitxerKey;

public interface FitxerService {
	/** Buscar Fitxer per id */
    Fitxer findByPk(FitxerKey pk);
    /** Buscar tots els fitxers */
	List<Fitxer> findAllByOrderByPk();
	/** Guardar un Fitxer */
	void save(Fitxer fitxer);
	/** Esborrar un Fitxer */
	void delete(Fitxer fitxer);	
}
