package com.hatchworks.challenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.hatchworks.challenge.dto.response.CvUploadResponse;
import com.hatchworks.challenge.service.CvProcessingService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://cv-parser-challenge-fe-iota.vercel.app", "http://localhost:3000"}) 
@RequestMapping("/api/cv")
public class CvController {

    private final CvProcessingService cvProcessingService;

    public CvController(CvProcessingService cvProcessingService) {
        this.cvProcessingService = cvProcessingService;
    }

    @PostMapping("/upload")
    public ResponseEntity<CvUploadResponse> uploadCv(@RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must provide a non-empty file.");
        }

        CvUploadResponse response = cvProcessingService.processCv(file);
        return ResponseEntity.ok(response);
    }
}