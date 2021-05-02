package org.zhezlov.documentarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zhezlov.documentarchive.model.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByAuthorId(Long userId);

//    @Query(value = "select * from DOCUMENTS where ID in (select DOCUMENT_ID from DOCUMENTS_GRANTS where USER_ID = :userId)", nativeQuery = true)
//    List<Document> getAllwithAnyGrants(@Param("userId") Long userId);
//
//    @Transactional
//    @Modifying
//    @Query(value = "insert into DOCUMENTS_GRANTS (DOCUMENT_ID, USER_ID) values ( :docId, :userId )", nativeQuery = true)
//    void sharingDocumentForOneUser(@Param("docId") Long docId,
//                                   @Param("userId") Long userId);
////                                   @Param("grant") Long grant);
//
//    @Transactional
//    @Modifying
//    @Query(value = "delete from DOCUMENTS_GRANTS where DOCUMENT_ID= :docId and USER_ID = :userId", nativeQuery = true)
//    void unsharingDocumentForOneUser(@Param("docId") Long docId,
//                                  @Param("userId") Long userId);
//
//
//    @Query(value = "select count(*) from DOCUMENTS_GRANTS where DOCUMENT_ID= :docId and USER_ID= :userId",nativeQuery = true)
//    int checkRightDownloading(@Param("docId") Long docId,
//                              @Param("userId")Long userId);

//    @Query(value = "select doc from Document doc where doc.id in(select d.idDocument from DocumentGrants d where d.idUser= :userId)")
//    List<Document> findAllDocumentsWithAnyUserGrants(@Param("userId") Long userId);

    @Query(value = "select doc from Document doc left join doc.documentGrants dg where dg.userId= :userId")
    List<Document> findAllDocumentsWithAnyUserGrants(@Param("userId") Long userId);

}
