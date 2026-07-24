package com.hatchworks.challenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hatchworks.challenge.dto.response.CvUploadResponse;
import com.hatchworks.challenge.service.CvProcessingService;

@RestController
@RequestMapping("/api/cv")
public class CvController {

    private final CvProcessingService cvProcessingService;

    public CvController(CvProcessingService cvProcessingService) {
        this.cvProcessingService = cvProcessingService;
    }

    @PostMapping("/upload")
    public ResponseEntity<CvUploadResponse> uploadCv(@RequestParam("file") MultipartFile file) {
        CvUploadResponse response = cvProcessingService.processCv(file);
        return ResponseEntity.ok(response);
    }
}