package org.zhezlov.documentarchive.dao;

import org.zhezlov.documentarchive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
