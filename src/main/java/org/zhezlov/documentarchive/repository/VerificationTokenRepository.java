package org.zhezlov.documentarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}

