package com.ateam.backend.controllers;

import com.ateam.backend.services.ResourceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("test")
@ContextConfiguration(classes = { ResourcesController.class })
@WebMvcTest
public class ResourcesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceService resourceService;

    @Test // create resource and upload it
    public void test_upload_resource() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello,  World!".getBytes());
        Mockito.doNothing().when(resourceService).uploadResource(eq(file));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/resource/upload")
                        .file(file))
                .andDo(print());
    }
}
