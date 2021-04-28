package org.zhezlov.documentarchive.service;

import org.zhezlov.documentarchive.repository.UserRepository;
import org.zhezlov.documentarchive.model.Role;
import org.zhezlov.documentarchive.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link UserService} interface.
 *
 * @version 1.0
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        return userRepository.save(user) ;
    }

    public User getUser(User user){
        return userRepository.findById(user.getId()).orElse(new User());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String getNameLoggedUser() {
        return securityService.findLoggedUsername();
    }
}
