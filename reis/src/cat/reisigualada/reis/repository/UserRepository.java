package cat.reisigualada.reis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.reisigualada.reis.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	/** Buscar un Usuari per Id */
	User findById(Long id);
	/** Buscar un Usuari per nom */
    User findByUsername(String username);
    /** Buscar un Usuaris ordenats per id Ascendent */
	List<User> findAllByOrderByIdAsc();
    /** Buscar un Usuaris ordenats per id Descendent */
	List<User> findAllByOrderByIdDesc();
    /** Buscar un Usuaris ordenats per username Ascendent */
	List<User> findAllByOrderByUsernameAsc();
    /** Buscar un Usuaris ordenats per username Descendent */
	List<User> findAllByOrderByUsernameDesc();
}
