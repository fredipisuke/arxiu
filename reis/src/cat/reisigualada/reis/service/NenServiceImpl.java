package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.Nen;
import cat.reisigualada.reis.repository.NenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NenServiceImpl implements NenService {
    @Autowired
    private NenRepository nenRepository;

    @Override
    public Nen findById(Long id) {
        return nenRepository.findById(id);
    }

    @Override
    public List<Nen> findAllByOrderById() {
    	return nenRepository.findAllByOrderById();
    }

    @Override
    public void save(Nen nen) {
    	nenRepository.save(nen);
    }
    
    @Override
    public void delete(Nen nen) {
    	nenRepository.delete(nen);
    }
}
