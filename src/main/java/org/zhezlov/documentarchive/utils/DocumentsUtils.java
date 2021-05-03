package org.zhezlov.documentarchive.utils;


import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.model.DocumentGrants;
import org.zhezlov.documentarchive.model.DocumentTo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentsUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");

    public static final String[] EXTENSION_FOR_PREVIEW = {"pdf", "odt", "odp", "ods"};

    public DocumentsUtils() {
    }

    public static List<DocumentTo> getTos(List<Document> documents, Long loggedUserId) {

        return documents.stream()
                .map(document -> createDocumentTo(document,
                        canPreview(document, loggedUserId),
                        canSharing(document.getAuthorId(), loggedUserId),
                        canUpdate(document, loggedUserId),
                        canDelete(document, loggedUserId),
                        canDownload(document, loggedUserId)))
                .collect(Collectors.toList());
    }
//03.05 ОСТАНОВИЛСЯ ТУТ - не работает превью у расшаренного документа и невозможно аптейт сделать у расшаренного документа (пользователь которому расшарили) ну и проверь дургое
    public static boolean canPreview(Document document, Long loggedUserId) {    //toDo можно разделить на 2 метода, чтобы показывать запрещен ли предварительный просмотр (хватает ли прав) или не допустим (не подходит расширение)
        List<DocumentGrants> documentGrants = document.getDocumentGrants();
        boolean flag = documentGrants.stream()
                .anyMatch(dg -> dg.getUserId().equals(loggedUserId)
                        && dg.getDocument().getId().equals(document.getId()) //ToDo скорее всего лишнее условие, т.к. в DG может быть информация только про один, родительский document
                        && (dg.getGranted().equals(15L) || dg.getGranted().equals(1L)));
        return flag && Arrays.asList(EXTENSION_FOR_PREVIEW).contains(getExtension(document.getName()));
    }

    public static boolean canDownload(Document document, Long loggedUserId) {    //toDo можно разделить на 2 метода, чтобы показывать запрещен ли предварительный просмотр (хватает ли прав) или не допустим (не подходит расширение)

        return document.getDocumentGrants().stream()
                .anyMatch(dg -> dg.getUserId().equals(loggedUserId)
                        && dg.getDocument().getId().equals(document.getId()) //ToDo скорее всего лишнее условие, т.к. в DG может быть информация только про один, родительский document
                        && (dg.getGranted().equals(15L) || dg.getGranted().equals(1L)));
    }

    public static boolean canSharing(Long authorId, Long loggedUserId) {
        return authorId.equals(loggedUserId);
    }

    public static boolean canUpdate(Document document, Long loggedUserId) {
        List<DocumentGrants> documentGrants = document.getDocumentGrants();
        return documentGrants.stream()
                .anyMatch(dg -> dg.getUserId().equals(loggedUserId)
                        && dg.getDocument().getId().equals(document.getId()) //ToDo скорее всего лишнее условие, т.к. в DG может быть информация только про один, родительский document
                        && (dg.getGranted().equals(10L) || dg.getGranted().equals(1L)));
    }

    public static boolean canDelete(Document document, Long loggedUserId) {
        List<DocumentGrants> documentGrants = document.getDocumentGrants();
        return documentGrants.stream()
                .anyMatch(dg -> dg.getUserId().equals(loggedUserId)
                        && dg.getDocument().getId().equals(document.getId()) //ToDo скорее всего лишнее условие, т.к. в DG может быть информация только про один, родительский document
                        && (dg.getGranted().equals(5L) || dg.getGranted().equals(1L)));
    }

    public static String getExtension(String fileName) {
        String[] fileNameDotSplit = fileName.split("\\.");
        if (fileNameDotSplit.length > 0)
            return fileNameDotSplit[fileNameDotSplit.length - 1];
        return "";
    }

    public static DocumentTo createDocumentTo(Document document, boolean canPreview, boolean canSharing, boolean canUpdate, boolean canDelete, boolean canDownload) {
        return new DocumentTo(document.getId(),
                document.getName(),
                document.getDescription(),
                document.getAuthorId(),
                document.getAuthorName(),
                document.getDateTimeCreated(),
                canPreview,
                canSharing,
                canUpdate,
                canDelete,
                canDownload);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
