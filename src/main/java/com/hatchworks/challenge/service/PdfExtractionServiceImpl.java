package com.hatchworks.challenge.service;

import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.hatchworks.challenge.exception.FileParsingException;

@Service
public class PdfExtractionServiceImpl implements FileExtractionService{


    @Override
    public String extractText(MultipartFile file) {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            if (text == null || text.trim().isEmpty()) {
                throw new FileParsingException("El PDF no contiene texto extraíble (¿es un escaneo/imagen?).");
            }

            return text;
        } catch (IOException e) {
            throw new FileParsingException("No se pudo leer el contenido del PDF.", e);
        }
    }

    @Override
    public boolean supports(String fileType) {
        return "PDF".equalsIgnoreCase(fileType);
    }
}
