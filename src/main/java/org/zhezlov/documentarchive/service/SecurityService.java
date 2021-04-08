package org.zhezlov.documentarchive.service;

/**
 * Service for Security.
 *
 * @version 1.0
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
