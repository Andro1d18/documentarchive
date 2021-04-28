package org.zhezlov.documentarchive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.zhezlov.documentarchive.UploadedFile;
import org.zhezlov.documentarchive.Utils.FileManager;
import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.repository.DocumentRepository;
import org.zhezlov.documentarchive.repository.UserRepository;
import org.zhezlov.documentarchive.to.DocumentTo;
import org.zhezlov.documentarchive.to.DocumentsUtils;
import org.zhezlov.documentarchive.validator.FileValidator;

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
    FileManager fileManager;

    @Autowired
    FileValidator fileValidator;

    public List<DocumentTo> getAll() {             //return all document with everything grants
        User user = userRepository.findByUsername(getLoggedUser().getUsername());
        if (user != null) {

            return DocumentsUtils.getTos(documentRepository.getAllwithAnyGrants(user.getId()));
        }
        return Collections.emptyList();

    }

    public List<Document> getAll(Long userId) {             //for test
        User user = userRepository.getOne(userId);
        if (user != null) {
            return documentRepository.getAllwithAnyGrants(user.getId());
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

    @Transactional
    public void create(String description, MultipartFile multipartFile) throws IOException { //ToDo если останется время, сделай через темп файлы (вначале создание в темп директории, затем создание в моделе, затем перенос в место дислокаци)
        if (multipartFile != null) {
            String fileName = multipartFile.getOriginalFilename();
            Document document = new Document(fileName, description, LocalDateTime.now(), getLoggedUser().getId(), getLoggedUser().getUsername());
            Document createdDocument = documentRepository.save(document);
            documentRepository.sharingDocumentForOneUser(createdDocument.getId(), getLoggedUser().getId());  //ToDo переделать если останется время - нужно создать свою имплементацию Repositories, а внутри юзать JpaRepository
            String filenameForFS = getFilenameForFS(createdDocument);
            fileManager.create(multipartFile, filenameForFS);
            LOG.info("created document: {}", createdDocument.getName());
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
        return documentRepository.getOne(id);
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

    public void shareDocument(Long id, Long userId) {
        documentRepository.unsharingDocumentForOneUser(id, userId);
        documentRepository.sharingDocumentForOneUser(id, userId);
    }

    public void shareDocumentForAllUsers(Long id) {
        for (User user :
                userRepository.findAll()) {
            documentRepository.unsharingDocumentForOneUser(id, user.getId());
            documentRepository.sharingDocumentForOneUser(id, user.getId());
        }
    }

    public void unsharingForAllUsers(Long id) {
        for (User user :
                userRepository.findAll()) {
            if (!user.getId().equals(get(id).getAuthorId()))
                documentRepository.unsharingDocumentForOneUser(id, user.getId());
        }
    }

    public void validateFile(UploadedFile uploadFile, BindingResult bindingResult){
        fileValidator.validate(uploadFile, bindingResult);
    }
}
