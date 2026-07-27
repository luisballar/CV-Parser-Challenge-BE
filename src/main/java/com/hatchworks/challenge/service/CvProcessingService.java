package com.hatchworks.challenge.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.hatchworks.challenge.domain.Cv;
import com.hatchworks.challenge.dto.response.CvDataDto;
import com.hatchworks.challenge.dto.response.CvUploadResponse;
import com.hatchworks.challenge.mapper.CvMapper;

@Service
public class CvProcessingService {

    private final List<FileExtractionService> fileExtractionServices;
    private final CvDataExtractorService cvDataExtractorService;
    private final CvMapper cvMapper;

    public CvProcessingService(
            List<FileExtractionService> fileExtractionServices,
            CvDataExtractorService cvDataExtractorService,
            CvMapper cvMapper) {

        this.fileExtractionServices = fileExtractionServices;
        this.cvDataExtractorService = cvDataExtractorService;
        this.cvMapper = cvMapper;
    }

    public CvUploadResponse processCv(MultipartFile file) {

        String fileType = detectFileType(file);

        if (fileType == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Format not supported. Only PDF and DOCX are allowed.");
        }

        String rawText = extractText(file, fileType);

        // Text -> Gemini -> Cv (domain)
        Cv cv = cvDataExtractorService.extractStructuredData(rawText);
        cv.setOriginalFileName(file.getOriginalFilename());

        CvDataDto data = cvMapper.toDto(cv);

        return new CvUploadResponse(true, data);
    }

    private String detectFileType(MultipartFile file) {
        String contentType = file.getContentType();

        if ("application/pdf".equals(contentType)) {
            return "PDF";
        }

        if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                .equals(contentType)) {
            return "DOCX";
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Format not supported. Only PDF and DOCX are allowed.");
    }

    private String extractText(MultipartFile file, String fileType) {
        for (FileExtractionService extractionService : fileExtractionServices) {
            if (extractionService.supports(fileType)) {
                return extractionService.extractText(file);
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"No extractor available for the type: " + fileType);
    }
}
