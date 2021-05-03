package org.zhezlov.documentarchive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.model.DocumentGrants;
import org.zhezlov.documentarchive.model.DocumentTo;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.repository.DocumentGrantsRepository;
import org.zhezlov.documentarchive.repository.DocumentRepository;
import org.zhezlov.documentarchive.repository.UserRepository;
import org.zhezlov.documentarchive.utils.DocumentsUtils;
import org.zhezlov.documentarchive.utils.FileManager;
import org.zhezlov.documentarchive.validator.FileValidator;
import org.zhezlov.documentarchive.validator.UploadedFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class DocumentService {
    public static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private DocumentGrantsRepository documentGrantsRepository;

    @Autowired
    FileManager fileManager;

    @Autowired
    FileValidator fileValidator;


//    public List<Document> findAllDocumentsWithAnyUserGrants(Long userId){   //for test in SpringMain
//        return documentRepository.findAllDocumentsWithAnyUserGrants(userId);
//    }

    public List<DocumentTo> findAllDocumentsWithAnyUserGrants() {
        User loggedUser = userRepository.findByUsername(getLoggedUser().getUsername());
        if (loggedUser != null) {
            return DocumentsUtils.getTos(documentRepository.findAllDocumentsWithAnyUserGrants(loggedUser.getId()), loggedUser.getId());
        }
        return Collections.emptyList();

    }


    public List<Document> getAllwithAuthorId() {    //return document only logged user = authorId
        String username = getUsername();
        User user = userRepository.findByUsername(username);
        if (user != null)
            return documentRepository.findAllByAuthorId(user.getId());
        return Collections.emptyList();
    }

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }


    public void create(String description, MultipartFile multipartFile) throws IOException { //ToDo если останется время, сделай через темп файлы (вначале создание в темп директории, затем создание в моделе, затем перенос в место дислокаци)
        if (multipartFile != null) {
            String fileName = multipartFile.getOriginalFilename();
            Document document = new Document(fileName, description, LocalDateTime.now(), getLoggedUser().getId(), getLoggedUser().getUsername());
            Document createdDocument = documentRepository.save(document);
            DocumentGrants dg = new DocumentGrants(createdDocument.getId(), getLoggedUser().getId(), 1L, createdDocument);
            createdDocument.addDocumentGrants(dg);
            documentGrantsRepository.save(dg);
            String filenameForFS = getFilenameForFS(createdDocument);
            fileManager.create(multipartFile, filenameForFS);
            LOG.info("created document: {}", createdDocument.getName());
            LOG.info("created document DocumentGrants size = {}", createdDocument.getDocumentGrants().size());

        }
    }

    public String getFilenameForFS(Document createdDocument) {
        return createdDocument.getId() + "-" + createdDocument.getName();
    }

    public String getFilenameForFS(Long id) {
        Document document = get(id);
        return document.getId() + "-" + document.getName();
    }

    public Document get(Long id) {
        return documentRepository.findById(id).orElse(new Document());
    }

    public void update(Long id, String description) {
        Document document = get(id);
            document.setDescription(description);
            documentRepository.save(document);
    }

    public void delete(Long id) throws IOException {
        fileManager.delete(getFilenameForFS(id));
        documentRepository.delete(get(id));
    }

    private User getLoggedUser() {
        return userRepository.findByUsername(securityService.findLoggedUsername());
    }

    public void shareDocumentForUser(Long id, Long userId, boolean canView, boolean canEdit, boolean canDelete) {

        Document document = get(id);
        if (!document.getAuthorId().equals(userId)) {
            if (canView) {
                DocumentGrants dg = new DocumentGrants(document.getId(), userId, 15L, document);
                documentGrantsRepository.save(dg);
            } else documentGrantsRepository.deleteCanViewByUserIdAndDocumentId(userId, id);

            if (canEdit) {
                DocumentGrants dg = new DocumentGrants(document.getId(), userId, 10L, document);
                documentGrantsRepository.save(dg);
            } else documentGrantsRepository.deleteCanEditByUserIdAndDocumentId(userId, id);
            if (canDelete) {
                DocumentGrants dg = new DocumentGrants(document.getId(), userId, 5L, document);
                documentGrantsRepository.save(dg);
            } else documentGrantsRepository.deleteCanDeleteByUserIdAndDocumentId(userId, id);
        }
    }


    public void shareDocumentForAllUsers(Long id, boolean canView, boolean canEdit, boolean canDelete) {

        for (User user :
                userRepository.findAll()) {
            shareDocumentForUser(id, user.getId(), canView, canEdit, canDelete);

        }
    }

    public void unsharingForAllUsers(Long id) {
        Document document = get(id);
        for (User user :
                userRepository.findAll()) {
            if (!user.getId().equals(document.getAuthorId()))
                documentGrantsRepository.deleteByUserIdAndDocumentId(user.getId(), id);
        }
    }

    public void validateFile(UploadedFile uploadFile, BindingResult bindingResult) {
        fileValidator.validate(uploadFile, bindingResult);
    }

    @Deprecated
    public boolean userHasRight(Long docId) {
        return get(docId).getAuthorId().equals(getLoggedUser().getId());
    }

    public boolean userHasRightForUpdate(Long docId) {
        boolean flag = documentGrantsRepository.userHasRightForUpdate(getLoggedUser().getId(), docId);
        LOG.info("flag is: {}", flag);
        return flag;
    }

    public boolean userHasRightForPreview(Long docId) {
        return documentGrantsRepository.userHasRightForPreview(getLoggedUser().getId(), docId);
    }

    public boolean userHasRightForDownloadnig(Long docId) {
        return documentGrantsRepository.userHasRightForDownloading(getLoggedUser().getId(), docId);
    }

    public boolean userHasRightForDelete(Long docId) {
        return documentGrantsRepository.userHasRightForDelete(getLoggedUser().getId(), docId);
    }

    public boolean userHasRightForSharing(Long docId) {
        return documentGrantsRepository.userHasRightForSharing(getLoggedUser().getId(), docId);
    }
}
