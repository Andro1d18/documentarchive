package org.zhezlov.documentarchive.Utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class FileManager {
    public static final Logger LOG = LoggerFactory.getLogger(FileManager.class);

    @Autowired
    FilePathUtil filePathUtil;

    public void create(MultipartFile multipartFile, String realFilename) throws IOException {

        byte[] bytes = multipartFile.getBytes();

        File dir = new File(filePathUtil.getFolderPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(realFilename);
        String utf8realFileName = StandardCharsets.UTF_8.decode(buffer).toString();

        File loadFile = new File(dir.getAbsolutePath() + File.separator + utf8realFileName);

        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(loadFile))) {
            stream.write(bytes);
            stream.flush();
        }
        LOG.info("uploaded: " + loadFile.getAbsolutePath());
        for (File file :
                dir.listFiles()) {
            LOG.info("folder has file: {}", file.getAbsolutePath());    //for heroku debug
        }
    }

    public void delete(String realFilename) throws IOException {
        Path path = Paths.get(filePathUtil.getFolderPath() + File.separator + realFilename);
        Files.delete(path);
    }
}
