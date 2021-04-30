package org.zhezlov.documentarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zhezlov.documentarchive.model.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByAuthorId(Long userId);

    @Query(value = "select * from DOCUMENTS where ID in (select ID_DOCUMENT from DOCUMENTS_GRANTS where ID_USER = :userId)", nativeQuery = true)
    List<Document> getAllwithAnyGrants(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "insert into DOCUMENTS_GRANTS (ID_DOCUMENT, ID_USER) values ( :docId, :userId )", nativeQuery = true)
    void sharingDocumentForOneUser(@Param("docId") Long docId,
                                   @Param("userId") Long userId);
//                                   @Param("grant") Long grant);

    @Transactional
    @Modifying
    @Query(value = "delete from DOCUMENTS_GRANTS where ID_DOCUMENT= :docId and ID_USER = :userId", nativeQuery = true)
    void unsharingDocumentForOneUser(@Param("docId") Long docId,
                                  @Param("userId") Long userId);

    @Query(value = "select count(*) from DOCUMENTS_GRANTS where ID_DOCUMENT= :docId and ID_USER= :userId",nativeQuery = true)
    int checkRightDownloading(@Param("docId") Long docId,
                              @Param("userId")Long userId);
}
