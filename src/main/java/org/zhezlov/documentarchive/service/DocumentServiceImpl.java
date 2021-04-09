package org.zhezlov.documentarchive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.zhezlov.documentarchive.dao.DocumentDao;
import org.zhezlov.documentarchive.dao.UserDao;
import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService{
    @Autowired
    DocumentDao documentDao;

    @Autowired
    UserDao userDao;

    @Override
    public List<Document> getAll() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        User user = userDao.findByUsername(username);

        //toDo refactor findAll without findAll
        return documentDao.findAll().stream()
                .filter(document -> document.getAuthorId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
