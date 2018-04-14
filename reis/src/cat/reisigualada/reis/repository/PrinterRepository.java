package cat.reisigualada.reis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cat.reisigualada.reis.model.Printer;

public interface PrinterRepository extends JpaRepository<Printer, Long>{
	/** Buscar una Impresora per Id */
	Printer findById(Long id);
	/** Buscar una Impresora per nom */
	Printer findByPrintername(String printername);
}
