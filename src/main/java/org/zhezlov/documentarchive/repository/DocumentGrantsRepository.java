package org.zhezlov.documentarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zhezlov.documentarchive.model.DocumentGrants;

import javax.transaction.Transactional;

public interface DocumentGrantsRepository extends JpaRepository<DocumentGrants, Long> {

    //    @Query(value = "insert into DOCUMENTS_GRANTS (DOCUMENT_ID, USER_ID) values ( :docId, :userId )", nativeQuery = true)
    @Transactional
    void deleteByUserIdAndDocumentId(Long userId, Long documentId);

    DocumentGrants findByUserIdAndDocumentId(Long userId, Long documentId);

    //Granted:
    //1 - is Author
    //5 - canDelete
    //10 - canUpdate
    //15 - canView

    @Query("SELECT (count(dg) > 0) FROM DocumentGrants dg WHERE dg.userId=:userId AND dg.documentId=:docId AND (dg.granted = 15 OR dg.granted = 1)")
    boolean userHasRightForDownloading(@Param("userId") Long userId,
                                       @Param("docId") Long docId);

    @Query("SELECT (count(dg) > 0) FROM DocumentGrants dg WHERE dg.userId=:userId AND dg.documentId=:docId AND (dg.granted = 15 OR dg.granted = 1)")
    boolean userHasRightForPreview(@Param("userId") Long userId,
                                   @Param("docId") Long docId);

    @Query("SELECT (count(dg) > 0) FROM DocumentGrants dg WHERE dg.userId=:userId AND dg.documentId=:docId AND (dg.granted = 10 OR dg.granted = 1)")
    boolean userHasRightForUpdate(@Param("userId") Long userId,
                                  @Param("docId") Long docId);

    @Query("SELECT (count(dg) > 0) FROM DocumentGrants dg WHERE dg.userId=:userId AND dg.documentId=:docId AND (dg.granted = 5 OR dg.granted = 1)")
    boolean userHasRightForDelete(@Param("userId") Long userId,
                                  @Param("docId") Long docId);

    @Query("SELECT (count(dg) > 0) FROM DocumentGrants dg WHERE dg.userId=:userId AND dg.documentId=:docId AND dg.granted = 1")
    boolean userHasRightForSharing(@Param("userId") Long userId,
                                   @Param("docId") Long docId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DocumentGrants dg where dg.userId=:userId AND dg.documentId=:docId AND dg.granted = 15")
    void deleteCanViewByUserIdAndDocumentId(@Param("userId") Long userId,
                                            @Param("docId") Long docId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DocumentGrants dg where dg.userId=:userId AND dg.documentId=:docId AND dg.granted = 10")
    void deleteCanEditByUserIdAndDocumentId(@Param("userId") Long userId,
                                            @Param("docId") Long docId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DocumentGrants dg where dg.userId=:userId AND dg.documentId=:docId AND dg.granted = 5")
    void deleteCanDeleteByUserIdAndDocumentId(@Param("userId") Long userId,
                                            @Param("docId") Long docId);
}
