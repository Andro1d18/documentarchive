package org.zhezlov.documentarchive;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.service.DocumentService;
import org.zhezlov.documentarchive.service.SecurityServiceImpl;
import org.zhezlov.documentarchive.service.UserService;

import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("appconfig-root.xml");
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

        UserService userService = appCtx.getBean(UserService.class);
        List<User> users = userService.findAll();
//        users.forEach( user
//                -> user.getDocuments()
//                .forEach(document
//                        -> System.out.println(document.getName() + " id user id " + document.getAuthorId())) );
        SecurityServiceImpl securityServiceImpl = appCtx.getBean(SecurityServiceImpl.class);
        securityServiceImpl.autoLogin("andro1d1", "12345678");
        DocumentService documentService = appCtx.getBean(DocumentService.class);
//        List<DocumentTo> documents = documentService.getAllwithNativeQuery();
//        documents.forEach(
//                documentTo -> System.out.println(documentTo.getAuthorId())
//        );
//        List<Document> documentList = documentService.findAllDocumentsWithAnyUserGrants(1L);
//        for (Document document :
//                documentList) {
//            System.out.println("count of document grants = " + document.getDocumentGrants().size());
//            List<DocumentGrants> listDocGrants = document.getDocumentGrants();
//            for (DocumentGrants documentGrants:
//                 listDocGrants) {
//
//                System.out.println(" id document:" + documentGrants.getIdDocument() + ", id user: " + documentGrants.getIdUser() + ", granted= " + documentGrants.getGranted());
//            }
//        }

//        System.out.println(documentList.get(0).getDocumentGrants().get(0).getGranted());
//        System.out.println(documentList.get(1).getDocumentGrants().get(0).getGranted());
//        System.out.println(documentList.get(2).getDocumentGrants().get(0).getGranted());
//        System.out.println(documentList.get(3).getDocumentGrants().get(0).getGranted());



   }
}
