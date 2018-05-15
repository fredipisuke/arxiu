package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.Autor;
import cat.reisigualada.reis.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutorServiceImpl implements AutorService {
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public Autor findById(Long id) {
        return autorRepository.findById(id);
    }

    @Override
    public Autor findByName(String name) {
        return autorRepository.findByName(name);
    }
    
    @Override
    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    @Override
    public void save(Autor key) {
       autorRepository.save(key);
    }
    
    @Override
    public void deleteById(Long id) {
       autorRepository.delete(id);
    }
}
