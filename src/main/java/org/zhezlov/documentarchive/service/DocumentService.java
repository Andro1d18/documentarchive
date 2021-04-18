package org.zhezlov.documentarchive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.zhezlov.documentarchive.dao.DocumentRepository;
import org.zhezlov.documentarchive.dao.UserRepository;
import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.model.User;

import java.util.*;

@Service
public class DocumentService {

    public static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SecurityService securityService;

    public List<Document> getAll() {

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

    public void create(Document document) {
        document.setAuthorId(getLoggedUser().getId());
        Document createdDocument = documentRepository.save(document);
        LOG.info("created document: {}", createdDocument);
    }

    private User getLoggedUser(){
        return userRepository.findByUsername(securityService.findLoggedInUsername());
    }
}
