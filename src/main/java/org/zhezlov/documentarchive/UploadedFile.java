package org.zhezlov.documentarchive;


import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

    private MultipartFile file;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
