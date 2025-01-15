package com.megabazaar.catalog_service.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

@RestController
@RequestMapping(value = "/api/products/images/get")
public class ImageController {
    @Value("${upload.directory}")
    private String uploadDirectory;

    @GetMapping(value = "/{filename}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename){
        try {
            Path filePath= Paths.get(uploadDirectory).resolve(filename).normalize();
            if (!Files.exists(filePath) || !Files.isReadable(filePath)){
                return ResponseEntity.notFound().build();
            }
            Resource resource=new FileSystemResource(filePath);
            return ResponseEntity.ok().header("Content-Type", Files.probeContentType(filePath)).body(resource);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
