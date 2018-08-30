package cat.reisigualada.reis.service;

import cat.reisdigualada.reis.vo.FitxerKey;
import cat.reisigualada.reis.model.Clau;
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
    public Fitxer findByPk(FitxerKey pk) {
        return fitxerRepository.findByPk(pk);
    }

    @Override
    public List<Fitxer> findAllByOrderByPk() {
    	return fitxerRepository.findAllByOrderByPk();
    }

    @Override
    public void save(Fitxer fitxer) {
    	fitxerRepository.save(fitxer);
    	DBUtils.deleteAllClaus(fitxer);
    	if(fitxer.getClaus()!=null){
    		for(Clau c : fitxer.getClaus()){
    			DBUtils.addFitxerClau(fitxer.getPk().getId(), fitxer.getPk().getTypeDocument(), c.getId());
    		}
    	}
    }
    
    @Override
    public void delete(Fitxer fitxer) {
    	fitxerRepository.delete(fitxer);
    }
}
