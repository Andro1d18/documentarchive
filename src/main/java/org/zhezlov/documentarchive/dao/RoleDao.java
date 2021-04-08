package org.zhezlov.documentarchive.dao;

import org.zhezlov.documentarchive.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
