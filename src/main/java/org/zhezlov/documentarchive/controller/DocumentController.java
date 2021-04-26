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
            LOG.info("FILED upload file because: {}", e.getMessage());
            //return "redirect:/documentForm"; // toDo переделать на возврат с сообщением об ошибке
        }
        return "redirect:/welcome";
    }

    @GetMapping("/documents/sharing")
    public String sharing(Model model, HttpServletRequest request) { //toDo вместое реквеста сделать moduleAttribute
        String id = request.getParameter("id");
        if (id != null) {
            LOG.info("forward for update document with id ={}", id);
            model.addAttribute("document", documentService.get(Long.parseLong(id)));
            model.addAttribute("users", userService.findAll());
            model.addAttribute("loggedUser", userService.getNameLoggedUser());
        }
        return "documentSharing";
    }

    @PostMapping("/documents/sharing")
    public String sharing(@RequestParam("id") String id,
                          @RequestParam(value = "userId", required = false) String userId,
                          @RequestParam(value = "forAllUsers", defaultValue = "false") boolean forAllUsers) { //toDo вместое реквеста сделать moduleAttribute
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
        if (id != null) {
            documentService.unsharingForAllUsers(Long.parseLong(id));
        }
        return "redirect:/welcome";
    }

    @GetMapping("/documents/update")
    public String update(Model model, HttpServletRequest request) { //toDo вместое реквеста сделать moduleAttribute
        String id = request.getParameter("id");
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
        documentService.update(Long.parseLong(id), description);
        return "redirect:/welcome";
    }


    @GetMapping("/documents/delete")
    public String delete(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
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

        String filenameForFS = documentService.getFilenameForFS(Long.parseLong(id));
        String uriString = UriUtils.encode(filenameForFS, StandardCharsets.UTF_8);
        return "redirect:/files/" + uriString;

    }

    @GetMapping("/preview")
    public String preview(@RequestParam("id") String id, Model model) {
        Long idDoc = Long.parseLong(id);
        LOG.debug("preview document with id = {}", idDoc);
        model.addAttribute("filenameForFS", documentService.getFilenameForFS(idDoc));
        return "previewDocument";
    }


//    @Autowired
//    private FileValidator fileValidator; //автосвязывание с бином FileValidator
//
//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView uploadFile(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult result) {// имена параметров (тут - "uploadedFile") - из формы JSP.
//
//        ModelAndView modelAndView = new ModelAndView();
//
//        String fileName = null;
//
//        MultipartFile file = uploadedFile.getFile();
//        fileValidator.validate(uploadedFile, result);
//
//        if (result.hasErrors()) {
//            modelAndView.setViewName("documents");
//        } else {
//
//            try {
//                byte[] bytes = file.getBytes();
//
//                fileName = file.getOriginalFilename();
//
//                String rootPath = "C:\\path\\";
//                File dir = new File(rootPath + File.separator + "loadFiles");
//
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//
//                File loadFile = new File(dir.getAbsolutePath() + File.separator + fileName);
//
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(loadFile));
//                stream.write(bytes);
//                stream.flush();
//                stream.close();
//
//                LOG.info("uploaded: " + loadFile.getAbsolutePath());
//
//                RedirectView redirectView = new RedirectView("welcome");
//                redirectView.setStatusCode(HttpStatus.FOUND);
//                modelAndView.setView(redirectView);
//                modelAndView.addObject("filename", fileName);
//
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }
//
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/fileuploaded", method = RequestMethod.GET)
//    public String fileUploaded() {
//        return "fileuploaded";
//    }

}
