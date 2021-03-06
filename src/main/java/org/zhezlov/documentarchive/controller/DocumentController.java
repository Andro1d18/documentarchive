package org.zhezlov.documentarchive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        LOG.info("forward for create document");
        model.addAttribute("document", new Document());
        return "documentForm";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Model model, HttpServletRequest request){ //toDo вместое реквеста сделать moduleAttribute
        String id = request.getParameter("id");
        if (id != null) {
            LOG.info("forward for update document with id ={}", id);
            model.addAttribute("document", documentService.get(Long.parseLong(id)));
        }
        return "documentForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateOrCreate(HttpServletRequest request){ //toDo вместое реквеста сделать moduleAttribute
        Document document = new Document(
                request.getParameter("name"),
                request.getParameter("description"),
                Timestamp.from(Instant.now()));
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()){
            LOG.debug("create document");
            documentService.create(document);
        } else {
            LOG.debug("update document with id = {}", id);
            documentService.update(document, Long.parseLong(id), Long.parseLong(request.getParameter("authorId")));
        }
        return "redirect:/welcome";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request){
        Long id = Long.parseLong(request.getParameter("id"));
        LOG.debug("delete document with id = {}", id);
        documentService.delete(id);
        return "redirect:/welcome";
    }


}
