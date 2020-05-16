package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.Nen;
import cat.reisigualada.reis.model.Tutor;
import cat.reisigualada.reis.repository.NenRepository;
import cat.reisigualada.reis.repository.TutorRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NenServiceImpl implements NenService {
    @Autowired
    private NenRepository nenRepository;
    @Autowired
    private TutorRepository tutorRepository;

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
    	// Guardem les dades del tutor legal
    	Tutor newTutor = tutorRepository.save(nen.getTutor());
    	nen.setTutor_id(newTutor.getId());
    	// Guardem el nen
    	nenRepository.save(nen);
    }
    
    @Override
    public void delete(Nen nen) {
    	nenRepository.delete(nen);
    }
}
