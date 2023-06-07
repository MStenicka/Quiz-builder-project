package com.ateam.backend.controllers;

import com.ateam.backend.models.quiz.Resource;
import com.ateam.backend.services.ResourceService;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("resource")
public class ResourcesController {
    public ResourcesController(ResourceService service) {
        this.service = service;
    }

    private final ResourceService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResource(@RequestParam("file") MultipartFile file) {
        try {
            service.uploadResource(file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        Optional<Resource> resource = service.findById(id);

        if(!resource.isPresent()) {
            ResponseEntity.badRequest().body("Resource not found!");
        }

        resource.get().setDeleted(true);
        service.save(resource.get());

        return ResponseEntity.ok().build();
    }
}