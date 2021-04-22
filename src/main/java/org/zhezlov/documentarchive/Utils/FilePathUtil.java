package org.zhezlov.documentarchive.Utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import java.io.File;

@Configuration
public class FilePathUtil {

    @Value("${save.file.in.project.directory}")
    private boolean saveFileInProjectDirectory;

    @Value("${save.file.in.project.subFolder.name}")
    private String subFolder;

    @Value("${path.file.warehouse}")
    private String pathFileWarehouse;

    private final ServletContext context;

    public FilePathUtil(ServletContext context) {
        this.context = context;
    }

    public String getFolderPath() {

        if (saveFileInProjectDirectory) {
            if (subFolder != null && !subFolder.isEmpty()) {
                return context.getRealPath("") + File.separator + subFolder;
            } else return context.getRealPath("");
        } else return pathFileWarehouse;

    }
}
