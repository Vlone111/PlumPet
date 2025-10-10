package com.example.plumpet.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/Hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }
}
