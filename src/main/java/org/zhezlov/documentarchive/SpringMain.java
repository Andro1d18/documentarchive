package org.zhezlov.documentarchive;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zhezlov.documentarchive.model.User;
import org.zhezlov.documentarchive.service.UserService;

import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("appconfig-root.xml");
        //System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

        UserService userService = appCtx.getBean(UserService.class);
        List<User> users = userService.findAll();
        users.forEach( user
                -> user.getDocuments()
                .forEach(document
                        -> System.out.println(document.getName() + " id user id " + document.getAuthorId())) );

//        DocumentService documentService = appCtx.getBean(DocumentService.class);
//        List<Document> documents = documentService.getAll();
//        documents.forEach(
//                document -> System.out.println(document.getAuthorId())
//        );
   }
}
