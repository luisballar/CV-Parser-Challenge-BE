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
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT,"Formato no soportado. Solo PDF y DOCX.");
        }

        String rawText = extractText(file, fileType);

        // Texto -> Gemini -> Cv (domain)
        Cv cv = cvDataExtractorService.extractStructuredData(rawText);
        cv.setOriginalFileName(file.getOriginalFilename());

        // Cv (domain) -> CvDataDto (via CvMapper)
        CvDataDto data = cvMapper.toDto(cv);

        // CvDataDto se envuelve dentro de CvUploadResponse
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
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT,"Formato no soportado. Solo PDF y DOCX.");
    }

    private String extractText(MultipartFile file, String fileType) {
        for (FileExtractionService extractionService : fileExtractionServices) {
            if (extractionService.supports(fileType)) {
                return extractionService.extractText(file);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT,"No hay un extractor disponible para el tipo: " + fileType);
    }
}
