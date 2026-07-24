package com.hatchworks.challenge.service;

import java.io.IOException;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hatchworks.challenge.exception.FileParsingException;

@Service
public class DocxExtractionServiceImpl implements FileExtractionService {
    @Override
    public String extractText(MultipartFile file) {
        try (XWPFDocument document = new XWPFDocument(file.getInputStream());
                XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {

            String text = extractor.getText();

            if (text == null || text.trim().isEmpty()) {
                throw new FileParsingException("El documento DOCX no contiene texto extraíble.");
            }

            return text;
        } catch (IOException e) {
            throw new FileParsingException("No se pudo leer el contenido del DOCX.", e);
        }
    }

    @Override
    public boolean supports(String fileType) {
        return "DOCX".equalsIgnoreCase(fileType);
    }

}
