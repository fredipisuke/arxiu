package cat.reisigualada.reis.service;

import java.util.List;
import cat.reisigualada.reis.model.Printer;

public interface PrinterService {
	/** Buscar Impresora per id */
    Printer findById(Long id);
	/** Buscar Impresora per nom */
    Printer findByPrintername(String printername);
	/** Buscar totes les Impresores */
	List<Printer> findAll();
	/** Guardar una Impresora */
	void save(Printer printer);
	/** Esborrar una Impresora */
	void deleteById(Long id);	
}
