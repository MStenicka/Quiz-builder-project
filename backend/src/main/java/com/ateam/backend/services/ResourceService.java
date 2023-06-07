package com.ateam.backend.services;

import com.ateam.backend.config.ApplicationConfig;
import com.ateam.backend.models.quiz.Resource;
import com.ateam.backend.repositories.ResourceRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    private static final Long ONE_MB = 1048576L;
    private static final String UPLOAD_DIRECTORY = "uploaded";

    private final ResourceRepository repository;

    private final ApplicationConfig.CommonsMultipartResolver customMultipartResolver;

    public Optional<Resource> findById(Long id) {
        return repository.findById(id);
    }

    public Resource save(Resource resource) {
        return repository.save(resource);
    }

    public List<Resource> findByDeletedFalse() {
        return repository.findByDeletedFalse();
    }

    public ResourceService(ResourceRepository repository, ApplicationConfig.CommonsMultipartResolver customMultipartResolver) {
        this.repository = repository;
        this.customMultipartResolver = customMultipartResolver;
    }

    public void uploadResource(MultipartFile file) throws ValidationException, IOException {
        if (file.getContentType().contains("video") && file.getSize() >= 50 * ONE_MB) {
            throw new ValidationException("Video file is too big");
        }
        if (file.getContentType().contains("image") && file.getSize() >= 3 * ONE_MB) {
            throw new ValidationException("Image file is too big");
        }
        if (file.getContentType().contains("audio") && file.getSize() >= 5 * ONE_MB) {
            throw new ValidationException("Audio file is too big");
        }
        Path directory = Paths.get(UPLOAD_DIRECTORY);
        if (!Files.exists(directory)) {
            Files.createDirectory(directory);
        }
        Path filePath = directory.resolve(file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        Resource resource = new Resource(null, filePath.toAbsolutePath().toString());
        repository.save(resource);
    }

    public void downloadResource() {}

}