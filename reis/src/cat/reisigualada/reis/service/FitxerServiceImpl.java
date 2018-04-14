package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.Fitxer;
import cat.reisigualada.reis.repository.FitxerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FitxerServiceImpl implements FitxerService {
    @Autowired
    private FitxerRepository fitxerRepository;

    @Override
    public Fitxer findById(Long id) {
        return fitxerRepository.findById(id);
    }

    @Override
    public List<Fitxer> findAllByOrderById() {
    	return fitxerRepository.findAllByOrderById();
    }

    @Override
    public void save(Fitxer fitxer) {
    	fitxerRepository.save(fitxer);
    }
    
    @Override
    public void deleteById(Long id) {
    	fitxerRepository.delete(id);
    }
}
