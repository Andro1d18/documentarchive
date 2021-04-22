package org.zhezlov.documentarchive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zhezlov.documentarchive.model.Document;
import org.zhezlov.documentarchive.service.DocumentService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


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
    public String update(Model model, HttpServletRequest request) { //toDo вместое реквеста сделать moduleAttribute
        String id = request.getParameter("id");
        if (id != null) {
            LOG.info("forward for update document with id ={}", id);
            model.addAttribute("document", documentService.get(Long.parseLong(id)));
        }
        return "documentUpdateForm";
    }

    @PostMapping("/update")
    public String update(Model model,     //при update можно только менять Описание. Если нужно загрузить другой файл, нужно удалять предыдущю запись
                         @RequestParam("id") String id,
                         @RequestParam("description") String description) {
        documentService.update(Long.parseLong(id), description);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam("description") String description,
                         @RequestParam("file") MultipartFile multipartFile) {
        try {
            documentService.create(description, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.info("FILED upload file because: {}", e.getMessage());
            //return "redirect:/documentForm"; // toDo переделать на возврат с сообщением об ошибке
        }

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
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
