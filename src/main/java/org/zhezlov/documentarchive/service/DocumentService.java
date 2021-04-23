package org.zhezlov.documentarchive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zhezlov.documentarchive.Utils.FileManager;
import org.zhezlov.documentarchive.dao.DocumentRepository;
import org.zhezlov.documentarchive.dao.UserRepository;
import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.model.User;

import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

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

    public List<Document> getAll(){             //return all document with everything grants
        User user = userRepository.findByUsername(getLoggedUser().getUsername());
        if (user != null)
            return documentRepository.getAllwithAnyGrants(user.getId());
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

    public void create(String description, MultipartFile multipartFile) throws IOException {
        String key = UUID.randomUUID().toString();
        fileManager.create(multipartFile, key);
        String fileName = multipartFile.getOriginalFilename();
        Document document = new Document(
                fileName,
                description,
                key,
                Timestamp.from(Instant.now()),
                getLoggedUser().getId());
        Document createdDocument = documentRepository.save(document);
        documentRepository.sharingDocumentForOneUser(createdDocument.getId(),getLoggedUser().getId());  //ToDo переделать если останется время - нужно создать свою имплементацию Repositories, а внутри юзать JpaRepository
        LOG.info("created document: {}", createdDocument.getName());
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
        fileManager.delete(get(id).getKey());
        documentRepository.delete(get(id));
    }

    private User getLoggedUser() {
        return userRepository.findByUsername(securityService.findLoggedUsername());
    }

    public void shareDocument(Long id, Long userId) {
        documentRepository.unsharingDocumentForOneUser(id,userId);
        documentRepository.sharingDocumentForOneUser(id, userId);
    }
}
