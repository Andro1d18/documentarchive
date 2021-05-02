package org.zhezlov.documentarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zhezlov.documentarchive.model.DocumentGrants;

import javax.transaction.Transactional;

public interface DocumentGrantsRepository extends JpaRepository<DocumentGrants, Long> {

//    @Query(value = "insert into DOCUMENTS_GRANTS (DOCUMENT_ID, USER_ID) values ( :docId, :userId )", nativeQuery = true)
    @Transactional
    void deleteByUserIdAndDocumentId(Long userId, Long documentId);

    DocumentGrants findByUserIdAndDocumentId(Long userId, Long documentId);
}
