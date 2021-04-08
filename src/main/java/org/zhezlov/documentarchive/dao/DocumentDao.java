package org.zhezlov.documentarchive.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zhezlov.documentarchive.model.Document;

public interface DocumentDao extends JpaRepository<Document, Long> {
}
