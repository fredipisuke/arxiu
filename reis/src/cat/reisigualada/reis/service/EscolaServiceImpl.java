package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.Escola;
import cat.reisigualada.reis.repository.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EscolaServiceImpl implements EscolaService {
    @Autowired
    private EscolaRepository escolaRepository;

    @Override
    public Escola findById(Long id) {
        return escolaRepository.findById(id);
    }

    @Override
    public Escola findByDescripcio(String descripcio) {
        return escolaRepository.findByDescripcio(descripcio);
    }
    
    @Override
    public List<Escola> findAll() {
        return escolaRepository.findAll();
    }

    @Override
    public List<Escola> findAllByOrderByDescripcio() {
        return escolaRepository.findAllByOrderByDescripcio();
    }

    @Override
    public void save(Escola key) {
       escolaRepository.save(key);
    }
    
    @Override
    public void deleteById(Long id) {
       escolaRepository.delete(id);
    }
}
