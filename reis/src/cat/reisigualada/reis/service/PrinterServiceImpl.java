package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.Printer;
import cat.reisigualada.reis.repository.PrinterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrinterServiceImpl implements PrinterService {
    @Autowired
    private PrinterRepository printerRepository;

    @Override
    public Printer findById(Long id) {
        return printerRepository.findById(id);
    }
    
    @Override
    public Printer findByPrintername(String printername) {
        return printerRepository.findByPrintername(printername);
    }

    @Override
    public List<Printer> findAll() {
        return printerRepository.findAll();
    }

    @Override
    public void save(Printer printer) {
       printerRepository.save(printer);
    }
    
    @Override
    public void deleteById(Long id) {
       printerRepository.delete(id);
    }
}
