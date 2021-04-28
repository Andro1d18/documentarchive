package org.zhezlov.documentarchive.to;


import org.zhezlov.documentarchive.model.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentsUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");

    public static final String[] EXTENSION_FOR_PREVIEW = {"pdf","odt", "odp", "ods"};


    public DocumentsUtils(){}

    public static List<DocumentTo> getTos(List<Document> documents){

        return documents.stream()
                .map(document -> createDocumentTo(document, canPreview(document)))
                .collect(Collectors.toList());
    }

    public static boolean canPreview(Document document){
        return Arrays.asList(EXTENSION_FOR_PREVIEW).contains(getExtension(document.getName()));
    }

    public static String getExtension(String fileName){
        String[] fileNameDotSplit = fileName.split("\\.");
        if (fileNameDotSplit.length > 0)
                return fileNameDotSplit[fileNameDotSplit.length - 1];
        return "";
    }

    public static DocumentTo createDocumentTo(Document document, boolean canPreview){
        return new DocumentTo(document.getId(),
                document.getName(),
                document.getDescription(),
                document.getAuthorId(),
                document.getAuthorName(),
                document.getDateTimeCreated(),
                canPreview);
    }
    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
