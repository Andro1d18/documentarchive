package org.zhezlov.documentarchive.service;

import org.zhezlov.documentarchive.model.User;

import java.util.List;

/**
 * Service class for {@link User}
 *
 * @version 1.0
 */

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    List<User> findAll();
}
