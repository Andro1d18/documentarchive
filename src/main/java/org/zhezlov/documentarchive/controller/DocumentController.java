package org.zhezlov.documentarchive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zhezlov.documentarchive.dao.UserRepository;
import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.service.DocumentService;
import org.zhezlov.documentarchive.service.SecurityService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;


@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {

        model.addAttribute("document", new Document());
        return "documentForm";
    }

    @RequestMapping(value = "/update")
    public String updateOrCreate(HttpServletRequest request){ //toDo вместое реквеста сделать moduleAttribute
        Document document = new Document();
        User user = getLoggedUser();
        document.setAuthorId(user.getId());
        document.setName(request.getParameter("name"));
        document.setDescription(request.getParameter("description"));
        document.setDateTimeCreated(Timestamp.from(Instant.now()));
        documentService.create(document);
        return "redirect:/welcome";
    }

    private User getLoggedUser(){
        return userRepository.findByUsername(securityService.findLoggedInUsername());
    }
}
