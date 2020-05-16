package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.Tutor;
import cat.reisigualada.reis.repository.TutorRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorServiceImpl implements TutorService {
    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public Tutor findById(Long id) {
        return tutorRepository.findById(id);
    }

    @Override
    public List<Tutor> findAllByOrderById() {
    	return tutorRepository.findAllByOrderById();
    }

    @Override
    public void save(Tutor tutor) {
    	tutorRepository.save(tutor);
    }
    
    @Override
    public void delete(Tutor tutor) {
    	tutorRepository.delete(tutor);
    }
}
