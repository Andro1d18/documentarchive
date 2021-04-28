package org.zhezlov.documentarchive.Utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilePathUtil {

    @Value("${save.file.in.project.directory}")
    private boolean saveFileInProjectDirectory;

    @Value("${save.file.in.project.subFolder.name}")
    private String subFolder;

    @Value("${path.file.warehouse}")
    private String pathFileWarehouse;


//    private ServletContext context;       //toDo закомментировал чтобы получилось запустить тесты. Раскомментировать перед деплоем
//
//    public FilePathUtil(ServletContext context) {
//        this.context = context;
//    }

    public String getFolderPath() {

//        if (saveFileInProjectDirectory) {         //toDo закомментировал чтобы получилось запустить тесты. Раскомментировать перед деплоем
//            if (subFolder != null && !subFolder.isEmpty()) {
//                return context.getRealPath("") + File.separator + subFolder;
//            } else return context.getRealPath("");
//        } else
            return pathFileWarehouse;

    }
}
