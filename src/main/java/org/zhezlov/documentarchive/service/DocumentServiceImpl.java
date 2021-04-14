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
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    public static final Logger LOG = LoggerFactory.getLogger(DocumentServiceImpl.class);
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Document> getAll() {

        String username = getUsername();
        User user = userRepository.findByUsername(username); //toDo протестировать оба варианта.
//        return Optional.ofNullable(user)
//                .map(User::getId)  //заменяет
//                .map(documentRepository::findAllByAuthorId)
//                .orElse(new ArrayList<>());
        if (user != null)
            return documentRepository.findAllByAuthorId(user.getId());
        return Collections.EMPTY_LIST;
    }

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();

    }

    @Override
    public void create(Document document) {
        Document createdDocument = documentRepository.save(document); //перенести указание пользователя из контроллера в сервисы
        LOG.info("created document: {}", createdDocument);
    }
}
