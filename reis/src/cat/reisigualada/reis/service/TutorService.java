package cat.reisigualada.reis.service;

import java.util.List;
import cat.reisigualada.reis.model.Tutor;

public interface TutorService {
	/** Buscar Tutor per id */
	Tutor findById(Long id);
    /** Buscar tots els Tutors */
	List<Tutor> findAllByOrderById();
	/** Guardar un Tutor */
	void save(Tutor tutor);
	/** Esborrar un Tutor */
	void delete(Tutor tutor);	
}
