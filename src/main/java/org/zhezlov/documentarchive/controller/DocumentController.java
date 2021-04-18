package org.zhezlov.documentarchive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.service.DocumentService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;


@Controller
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {

        model.addAttribute("document", new Document());
        return "documentForm";
    }

    @RequestMapping(value = "/update")
    public String updateOrCreate(HttpServletRequest request){ //toDo вместое реквеста сделать moduleAttribute
        documentService.create(new Document(
                request.getParameter("name"),
                request.getParameter("description"),
                Timestamp.from(Instant.now()))
        );
        return "redirect:/welcome";
    }
}
