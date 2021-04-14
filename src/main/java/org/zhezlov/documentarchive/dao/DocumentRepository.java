package org.zhezlov.documentarchive.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zhezlov.documentarchive.model.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByAuthorId(Long userId);
}
