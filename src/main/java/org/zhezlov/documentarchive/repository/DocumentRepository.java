package org.zhezlov.documentarchive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zhezlov.documentarchive.model.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByAuthorId(Long userId);

//    @Query(value = "select doc from Document doc where doc.id in(select d.idDocument from DocumentGrants d where d.idUser= :userId)")
//    List<Document> findAllDocumentsWithAnyUserGrants(@Param("userId") Long userId);

    @Query(value = "select DISTINCT doc from Document doc left join doc.documentGrants dg where dg.userId= :userId")
    List<Document> findAllDocumentsWithAnyUserGrants(@Param("userId") Long userId);

}
