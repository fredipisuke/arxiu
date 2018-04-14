package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.Role;
import cat.reisigualada.reis.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolerServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id);
    }
    
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
    public List<Role> findAllByOrderByIdAsc() {
        return roleRepository.findAllByOrderByIdAsc();
    }
    public List<Role> findAllByOrderByIdDesc() {
        return roleRepository.findAllByOrderByIdDesc();
    }
    public List<Role> findAllByOrderByNameAsc() {
        return roleRepository.findAllByOrderByNameAsc();
    }
    public List<Role> findAllByOrderByNameDesc() {
        return roleRepository.findAllByOrderByNameDesc();
    }

    @Override
    public void save(Role role) {
       roleRepository.save(role);
    }
    
    @Override
    public void deleteById(Long id) {
       roleRepository.delete(id);
    }
}
