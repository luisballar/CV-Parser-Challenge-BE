package com.hatchworks.challenge.service;

import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PdfExtractionServiceImpl implements FileExtractionService {

    @Override
    public String extractText(MultipartFile file) {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            if (text == null || text.trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT,
                        "El PDF está vacío o no contiene texto legible (¿es un escaneo/imagen?).");
            }

            return text;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar el archivo PDF.", e);
        }
    }

    @Override
    public boolean supports(String fileType) {
        return "PDF".equalsIgnoreCase(fileType);
    }
}
