package com.hatchworks.challenge.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatchworks.challenge.api.GeminiClient;
import com.hatchworks.challenge.domain.Cv;

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

        String llmJsonResponse;

        try {
            llmJsonResponse = geminiClient.extractResumeData(rawText);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "It was not possible to communicate with the data extraction service", e);
        }

        JsonNode rootNode = parseJson(llmJsonResponse);

        // Caso: Gemini detectó que el documento no es un CV
        if (rootNode.has("error") && "NOT_A_CV".equals(rootNode.path("error").asText())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT,
                    "The uploaded document does not appear to be a CV/resume.");
        }

        // JSON -> objeto Java (Cv)
        try {
            return objectMapper.treeToValue(rootNode, Cv.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "It was not possible to interpret the API response.", e);
        }
    }

    private JsonNode parseJson(String rawJson) {
        try {
            return objectMapper.readTree(rawJson);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "The API response is not a valid JSON.", e);
        }
    }
}
