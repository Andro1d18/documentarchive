package org.zhezlov.documentarchive;

import org.zhezlov.documentarchive.model.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DocumentTestData {

    public static final Document doc1 = new Document(1L, "Жезлов Андрей (1).pdf", "Резюме", Timestamp.valueOf("2020-11-01 14:30:00"), 1L);
    public static final Document doc1Updated = new Document(1L, "Жезлов Андрей (1).pdf", "description update", Timestamp.valueOf("2020-11-01 14:30:00"), 1L);
    public static final Document doc2 = new Document(2L, "Spring_in_Action_5th_Edition.pdf", "Книга", Timestamp.valueOf("2021-02-01 8:30:00"), 1L);
    public static final Document doc3 = new Document(3L, "SpringSecurityApp-master.zip", "Архив", Timestamp.valueOf("2019-11-01 22:30:00"), 1L);
    public static final Document doc4 = new Document(4L, "error.log", "Лог ошибки", Timestamp.valueOf("2002-11-01 14:30:00"), 2L);
    public static final Document doc5 = new Document(5L, "doc-Прилож1-ЗАЯВЛЕНИЕ+О+ДОСТАВКЕ+ПЕНСИИ.docx", "Штатное рассписание", Timestamp.valueOf("2017-09-01 10:30:00"), 2L);

    public static List<Document> documentsUser1 = new ArrayList<>();
    static {
        documentsUser1.add(doc1);
        documentsUser1.add(doc2);
        documentsUser1.add(doc3);
        documentsUser1.add(doc5);
    }
    public static List<Document> documentsUser2AfterSharing = new ArrayList<>();
    static {
        documentsUser2AfterSharing.add(doc4);
        documentsUser2AfterSharing.add(doc5);
        documentsUser2AfterSharing.add(doc1);
    }
}
