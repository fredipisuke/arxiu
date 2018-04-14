package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.Clau;
import cat.reisigualada.reis.repository.ClauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClauServiceImpl implements ClauService {
    @Autowired
    private ClauRepository keyRepository;

    @Override
    public Clau findById(Long id) {
        return keyRepository.findById(id);
    }
    
    @Override
    public List<Clau> findByType(Long type) {
    	return keyRepository.findByType(type);
	}

    @Override
    public Clau findByNameAndType(String name, Long type) {
    	return keyRepository.findByNameAndType(name, type);
	}
    
    @Override
    public List<Clau> findAll() {
        return keyRepository.findAll();
    }
    public List<Clau> findAllByOrderByTypeAscNameAsc() {
        return keyRepository.findAllByOrderByTypeAscNameAsc();
    }

    @Override
    public void save(Clau key) {
       keyRepository.save(key);
    }
    
    @Override
    public void deleteById(Long id) {
       keyRepository.delete(id);
    }
}
