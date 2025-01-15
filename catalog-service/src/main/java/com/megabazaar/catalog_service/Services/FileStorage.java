package com.megabazaar.catalog_service.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

@Service
public class FileStorage {
    @Value("${upload.directory}")
    private String uploadDirectory;

    public String save(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String uniqueFileName=(Duration.between(Instant.EPOCH, Instant.now()).toString());
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            uniqueFileName+="."+fileName.substring(fileName.lastIndexOf(".") + 1);
            Path filePath = Paths.get(uploadDirectory, uniqueFileName);
            Files.copy(file.getInputStream(), filePath);
            return uniqueFileName;
        }
        return "";
    }
    public String update(String oldFile, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String uniqueFileName=(Duration.between(Instant.EPOCH, Instant.now()).toString());
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            Path oldPath = Paths.get(uploadDirectory, oldFile);
            uniqueFileName+="."+fileName.substring(fileName.lastIndexOf(".") + 1);
            Path filePath = Paths.get(uploadDirectory, uniqueFileName);
            Files.copy(file.getInputStream(), filePath);
            Files.delete(oldPath);
            return uniqueFileName;
        }
        return "";
    }
    public Boolean delete(String fileName) throws IOException {
        try{
            Path filePath = Paths.get(uploadDirectory, fileName);
            Files.delete(filePath);
            return true;
        } catch (IOException e){
            System.out.println("Error is : "+e.getMessage());
            return false;
        }
    }
}