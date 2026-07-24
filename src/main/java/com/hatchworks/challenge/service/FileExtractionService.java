package com.hatchworks.challenge.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileExtractionService {

    String extractText(MultipartFile file);

    boolean supports(String fileType);

}
