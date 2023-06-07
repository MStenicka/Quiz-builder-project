package com.ateam.backend.repositories;

import com.ateam.backend.models.quiz.Resource;
import com.ateam.backend.services.ResourceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResourceRepositoryUnitTest {

    @Mock
    private ResourceRepository resourceRepository;
    @Mock
    private MultipartFile multipartFile;

    @Test // create resource, add to list, then check the list
    public void find_not_deleted_resource() {
        Resource resource1 = new Resource(1L, "test/resource1/path");
        List<Resource> resources = new ArrayList<>();
        resources.add(resource1);
        when(resourceRepository.findByDeletedFalse()).thenReturn(resources);
        List<Resource> result = new ResourceService(resourceRepository, null).findByDeletedFalse();

        assertEquals(resources.size(), result.size());
        assertEquals(resource1.getId(), result.get(0).getId());
        assertEquals(resource1.getResourcePath(), result.get(0).getResourcePath());
        verify(resourceRepository).findByDeletedFalse();
    }

    @Test // create resource, then delete it, then check the empty list
    public void find_deleted_resource() {
        Resource resource2 = new Resource(2L, "test/resource1/path2");
        resource2.setDeleted(true);
        List<Resource> resources = new ArrayList<>();
        when(resourceRepository.findByDeletedFalse()).thenReturn(resources);
        List<Resource> result = new ResourceService(resourceRepository, null).findByDeletedFalse();

        assertEquals(resources.size(), result.size());
        verify(resourceRepository).findByDeletedFalse();
    }
}

