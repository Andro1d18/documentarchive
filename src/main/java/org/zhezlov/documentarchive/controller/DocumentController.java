package org.zhezlov.documentarchive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.zhezlov.documentarchive.UploadedFile;
import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.service.DocumentService;
import org.zhezlov.documentarchive.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Controller
//@RequestMapping("/documents")
public class DocumentController {
    public static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;

    @GetMapping("/documents/create")
    public String create(Model model) {
        LOG.info("forward for create document");
        model.addAttribute("document", new Document());
        return "documentForm";
    }

    @PostMapping("/documents/create")
    public String create(@RequestParam("description") String description,
                         @ModelAttribute("uploadedFile") UploadedFile uploadedFile,
                         BindingResult bindingResult) {
        documentService.validateFile(uploadedFile, bindingResult);
        if (bindingResult.hasErrors())
            return "documentForm";
        try {
            documentService.create(description, uploadedFile.getFile());
        } catch (IOException e) {
            e.printStackTrace();
            LOG.info("FILED upload file because: {}", e.getMessage()); // toDo переделать на возврат с сообщением об ошибке
        }
        return "redirect:/welcome";
    }

    @GetMapping("/documents/sharing")
    public String sharing(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");

        if (!documentService.userHasRight(Long.parseLong(id)))
            return "redirect:/welcome";

        LOG.info("forward for update document with id ={}", id);
        model.addAttribute("document", documentService.get(Long.parseLong(id)));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("loggedUser", userService.getNameLoggedUser());

        return "documentSharing";
    }


    @PostMapping("/documents/sharing")
    public String sharing(@RequestParam("id") String id,
                          @RequestParam(value = "userId", required = false) String userId,
                          @RequestParam(value = "forAllUsers", defaultValue = "false") boolean forAllUsers) {

        if (!documentService.userHasRight(Long.parseLong(id)))
            return "redirect:/welcome";

        if (forAllUsers) {
            documentService.shareDocumentForAllUsers(Long.parseLong(id));
            return "redirect:/welcome";
        }
        if (userId != null && id != null) {
            documentService.shareDocument(Long.parseLong(id), Long.parseLong(userId));
        }
        return "redirect:/welcome";
    }


    @PostMapping("/documents/unsharing")
    public String sharing(@RequestParam("id") String id) {
        if (!documentService.userHasRight(Long.parseLong(id)))
            return "redirect:/welcome";

        documentService.unsharingForAllUsers(Long.parseLong(id));

        return "redirect:/welcome";
    }

    @GetMapping("/documents/update")
    public String update(Model model, HttpServletRequest request) {  //ОСТАНОВИЛСЯ ТУТ - доделай во все контроллеры проверку на имеет ли права пользователь на такие действия
        String id = request.getParameter("id");
        if (!documentService.userHasRight(Long.parseLong(id)))
            return "redirect:/welcome";
        if (id != null) {
            LOG.info("forward for update document with id ={}", id);
            model.addAttribute("document", documentService.get(Long.parseLong(id)));
        }
        return "documentUpdateForm";
    }

    @PostMapping("/documents/update")
    public String update(Model model,     //при update можно только менять Описание. Если нужно загрузить другой файл, нужно удалять предыдущю запись
                         @RequestParam("id") String id,
                         @RequestParam("description") String description) {
        if (!documentService.userHasRight(Long.parseLong(id)))
            return "redirect:/welcome";
        documentService.update(Long.parseLong(id), description);
        return "redirect:/welcome";
    }


    @GetMapping("/documents/delete")
    public String delete(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));

        if (!documentService.userHasRight(id))
            return "redirect:/welcome";
        LOG.debug("delete document with id = {}", id);
        try {
            documentService.delete(id);
        } catch (IOException e) {
            e.printStackTrace(); // toDo переделать на возврат с сообщением об ошибке
        }
        return "redirect:/welcome";
    }

    @GetMapping("/documents/downloading")
    public String downloading(HttpServletRequest request, @RequestParam String id) {

        if (!documentService.userHasRightForDownloadnig(Long.parseLong(id)))
            return "redirect:/welcome";
        String filenameForFS = documentService.getFilenameForFS(Long.parseLong(id));
        String uriString = UriUtils.encode(filenameForFS, StandardCharsets.UTF_8);
        return "redirect:/files/" + uriString;

    }

    @GetMapping("/preview")
    public String preview(@RequestParam("id") String id, Model model) {
        if (!documentService.userHasRightForPreview(Long.parseLong(id)))
            return "redirect:/welcome";
        Long idDoc = Long.parseLong(id);
        LOG.debug("preview document with id = {}", idDoc);
        model.addAttribute("filenameForFS", documentService.getFilenameForFS(idDoc));
        return "previewDocument";
    }

    @GetMapping("/cancel")
    public String cancel(){
        return "redirect:/welcome";
    }
}
