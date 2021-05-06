package org.zhezlov.documentarchive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zhezlov.documentarchive.controller.UserController;
import org.zhezlov.documentarchive.exception.UserAlreadyExistException;
import org.zhezlov.documentarchive.model.Role;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.model.VerificationToken;
import org.zhezlov.documentarchive.repository.UserRepository;
import org.zhezlov.documentarchive.repository.VerificationTokenRepository;

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

    public static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public User save(User user) {
        LOG.info("user email:{}", user.getEmail());
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

    public User getUser(String verificationToken) {
        return tokenRepository.findByToken(verificationToken).getUser();
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public User registerNewUserAccount(User userDto)
            throws UserAlreadyExistException {

        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with that email adress: "
                            + userDto.getEmail());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

}
