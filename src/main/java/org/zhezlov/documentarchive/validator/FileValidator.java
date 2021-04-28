package org.zhezlov.documentarchive.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.zhezlov.documentarchive.UploadedFile;
import org.zhezlov.documentarchive.to.DocumentsUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class FileValidator implements Validator {

    private static final String ILLEGAL_CHARACTERS = "[\\/\\n\\r\\t\\f'?*<>|:]+";

    @Override
    public void validate(Object uploadFile, Errors errors) {

        UploadedFile fileUploaded = (UploadedFile) uploadFile;

        String description = fileUploaded.getDescription();
        MultipartFile file = fileUploaded.getFile();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Required");

        if(description.length() > 255){
            errors.rejectValue("description", "uploadForm.selectFile", "Max size description is 255 characters");
        }

        Pattern regex = Pattern.compile(ILLEGAL_CHARACTERS);
        Matcher m = regex.matcher(description);
        if (m.find()) {
            errors.rejectValue("description", "uploadForm.selectFile", "Description contains illegal characters");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "file", "Required");

        if (DocumentsUtils.getExtension(Objects.requireNonNull(file.getOriginalFilename())).equals("exe")) {
            errors.rejectValue("file", "uploadForm.selectFile", "Cannot be loaded exe file!");
        }
        if(file.getSize() >20971520){
            errors.rejectValue("file", "uploadForm.selectFile", "Cannot upload file over 20Mb");
        }
        if(file.getOriginalFilename().length() > 255){
            errors.rejectValue("file", "uploadForm.selectFile", "Cannot upload filename over 255 characters!");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

}