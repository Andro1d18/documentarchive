package org.zhezlov.documentarchive.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhezlov.documentarchive.DocumentTestData;
import org.zhezlov.documentarchive.model.Document;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({
        "classpath:appconfig-root.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DocumentServiceTest {

    @Autowired
    DocumentService documentService;

//    @Test
//    public void getAll() {
//        List<Document> actual = documentService.getAllwithNativeQuery(1L);
//        assertThat(actual).usingRecursiveComparison().isEqualTo(DocumentTestData.documentsUser1);
//    }

    @Test
    public void get() {
        Document document = documentService.get(DocumentTestData.doc1.getId());
        assertThat(document).usingRecursiveComparison().isEqualTo(DocumentTestData.doc1);
    }

    @Test
    public void update() {
        documentService.update(DocumentTestData.doc1.getId(), "description update");
        Document actual = documentService.get(DocumentTestData.doc1.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(DocumentTestData.doc1Updated);
    }


//    @Test
//    public void shareDocument() {
//        documentService.shareDocumentWithNativeQuary(1L, 2L);
//        List<Document> actual = documentService.getAllwithNativeQuery(2L);
//    }
}