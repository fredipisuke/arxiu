package cat.reisigualada.reis.service;

import cat.reisigualada.reis.model.User;
import cat.reisigualada.reis.repository.RoleRepository;
import cat.reisigualada.reis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public List<User> findAllByOrderByIdAsc() {
        return userRepository.findAllByOrderByIdAsc();
    }
    public List<User> findAllByOrderByIdDesc() {
        return userRepository.findAllByOrderByIdDesc();
    }
    public List<User> findAllByOrderByUsernameAsc() {
        return userRepository.findAllByOrderByUsernameAsc();
    }
    public List<User> findAllByOrderByUsernameDesc() {
        return userRepository.findAllByOrderByUsernameDesc();
    }
    
    @Override
    public void save(User user) {
        if(user.getPassword()!=null){
        	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
        	user.setPassword(user.getCryptPassword());
        }
        if(user.getRoles()==null)
        	user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }
    
    @Override
    public void deleteById(Long id) {
        userRepository.delete(id);
    }
}
