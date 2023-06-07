package com.ateam.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeRestController {

    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> returnHello(){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user")
    public String user(Principal principal) {
        String name = principal.getName();
        return String.format("Welcome %s!", name);
    }
}