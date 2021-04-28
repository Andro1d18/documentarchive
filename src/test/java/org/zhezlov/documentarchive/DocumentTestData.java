package org.zhezlov.documentarchive;

import org.zhezlov.documentarchive.model.Document;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class DocumentTestData {

    public static final Document doc1 = new Document(1L, "Жезлов Андрей (1).pdf", "Резюме", LocalDateTime.of(2020, Month.NOVEMBER, 1, 14,30), 1L, "andro1d1");
    public static final Document doc1Updated = new Document(1L, "Жезлов Андрей (1).pdf", "description update", LocalDateTime.of(2020, Month.NOVEMBER, 1, 14,30), 1L, "andro1d1");
    public static final Document doc2 = new Document(2L, "Spring_in_Action_5th_Edition.pdf", "Книга", LocalDateTime.of(2021, Month.FEBRUARY, 1, 8,30), 1L, "andro1d1");
    public static final Document doc3 = new Document(3L, "SpringSecurityApp-master.zip", "Архив", LocalDateTime.of(2019, Month.NOVEMBER, 1, 22,30), 1L, "andro1d1");
    public static final Document doc4 = new Document(4L, "error.log", "Лог ошибки", LocalDateTime.of(2002, Month.APRIL, 1, 14,59), 2L, "user");
    public static final Document doc5 = new Document(5L, "doc-Прилож1-ЗАЯВЛЕНИЕ+О+ДОСТАВКЕ+ПЕНСИИ.docx", "Штатное рассписание", LocalDateTime.of(2017, Month.SEPTEMBER, 21, 10,30), 2L, "user");

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
