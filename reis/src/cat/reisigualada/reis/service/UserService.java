package cat.reisigualada.reis.service;

import java.util.List;
import cat.reisigualada.reis.model.User;

public interface UserService {
	/** Buscar Usuari per Id */
	User findById(Long id);
	/** Buscar Usuari per nom */
    User findByUsername(String username);
    /** Buscar tots els usuaris */
    List<User> findAll();
    /** Buscar un Usuaris ordenats per id Ascendent */
	List<User> findAllByOrderByIdAsc();
    /** Buscar un Usuaris ordenats per id Descendent */
	List<User> findAllByOrderByIdDesc();
    /** Buscar un Usuaris ordenats per username Ascendent */
	List<User> findAllByOrderByUsernameAsc();
    /** Buscar un Usuaris ordenats per username Descendent */
	List<User> findAllByOrderByUsernameDesc();
    /** Guardar un usuari */
    void save(User user);
    /** Esborrar un usuari */
    void deleteById(Long id);
}
