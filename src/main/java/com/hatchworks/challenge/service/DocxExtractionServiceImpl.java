package com.hatchworks.challenge.service;

import java.io.IOException;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DocxExtractionServiceImpl implements FileExtractionService {
    
    @Override
    public String extractText(MultipartFile file) {
        try (XWPFDocument document = new XWPFDocument(file.getInputStream());
                XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {

            String text = extractor.getText();

            if (text == null || text.trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "El archivo DOCX está vacío o no contiene texto legible.");
            }

            return text;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar el archivo DOCX.", e);
        }
    }

    @Override
    public boolean supports(String fileType) {
        return "DOCX".equalsIgnoreCase(fileType);
    }

}
