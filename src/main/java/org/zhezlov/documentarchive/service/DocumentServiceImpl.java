package org.zhezlov.documentarchive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.zhezlov.documentarchive.dao.DocumentDao;
import org.zhezlov.documentarchive.model.Document;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService{
    @Autowired
    DocumentDao documentDao;

    @Override
    public List<Document> getAll() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        } else {
            String username = principal.toString();
        }
        return documentDao.findAll();
    }
}
