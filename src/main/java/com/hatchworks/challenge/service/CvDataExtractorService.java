package com.hatchworks.challenge.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatchworks.challenge.api.GeminiClient;
import com.hatchworks.challenge.domain.Cv;
import com.hatchworks.challenge.exception.LlmExtractionException;
import com.hatchworks.challenge.exception.UnsupportedFileTypeException;

@Service
public class CvDataExtractorService {

    private final GeminiClient geminiClient;
    private final ObjectMapper objectMapper;

    public CvDataExtractorService(
            GeminiClient geminiClient,
            ObjectMapper objectMapper) {

        this.geminiClient = geminiClient;
        this.objectMapper = objectMapper;
    }

    public Cv extractStructuredData(String rawText) {

        // Gemini retorna un String JSON
        String llmJsonResponse = geminiClient.extractResumeData(rawText);

        JsonNode rootNode = parseJson(llmJsonResponse);

        // Caso: Gemini detectó que el documento no es un CV
        if (rootNode.has("error") && "NOT_A_CV".equals(rootNode.path("error").asText())) {
            throw new UnsupportedFileTypeException("El documento subido no parece ser un CV/currículum.");
        }

        // JSON -> objeto Java (Cv)
        try {
            return objectMapper.treeToValue(rootNode, Cv.class);
        } catch (Exception e) {
            throw new LlmExtractionException("No se pudo interpretar la respuesta de Gemini.", e);
        }
    }

    private JsonNode parseJson(String rawJson) {
        try {
            return objectMapper.readTree(rawJson);
        } catch (Exception e) {
            throw new LlmExtractionException("La respuesta de Gemini no es un JSON válido.", e);
        }
    }
}
