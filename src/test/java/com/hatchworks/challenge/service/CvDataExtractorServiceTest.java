package com.hatchworks.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatchworks.challenge.api.GeminiClient;
import com.hatchworks.challenge.domain.Cv;

public class CvDataExtractorServiceTest {
        @Test
        void testExtractStructuredDataSuccessful() {

                GeminiClient geminiClient = mock(GeminiClient.class);
                ObjectMapper objectMapper = new ObjectMapper();

                CvDataExtractorService service = new CvDataExtractorService(
                                geminiClient,
                                objectMapper);

                String jsonResponse = """
                                {
                                  "personalInfo": {
                                    "fullName": "Juanita Perez Solano",
                                    "email": "juanitaperezsolano@gmail.com",
                                    "phone": "+506 8888-8888",
                                    "location": "Costa Rica"
                                  },
                                  "skills": [
                                    {
                                      "name": "Java",
                                      "category": "Programming and Frameworks"
                                    },
                                    {
                                      "name": "Spring Boot",
                                      "category": "Programming and Frameworks"
                                    }
                                  ]
                                }
                                """;

                when(geminiClient.extractResumeData(anyString()))
                                .thenReturn(jsonResponse);

                Cv result = service.extractStructuredData("CV text");

                assertNotNull(result);

                assertEquals(
                                "Juanita Perez Solano",
                                result.getPersonalInfo().getFullName());

                assertEquals(
                                "juanitaperezsolano@gmail.com",
                                result.getPersonalInfo().getEmail());

                assertEquals(
                                "Java",
                                result.getSkills().get(0).getName());

                verify(geminiClient)
                                .extractResumeData("CV text");
        }

        @Test
        void shouldThrowExceptionWhenLlmResponseIsInvalid() {

                GeminiClient geminiClient = mock(GeminiClient.class);
                ObjectMapper objectMapper = new ObjectMapper();

                CvDataExtractorService service = new CvDataExtractorService(
                                geminiClient,
                                objectMapper);

                when(geminiClient.extractResumeData(anyString()))
                                .thenReturn("this is not valid json");

                try {
                        service.extractStructuredData("CV text");

                        fail("Expected ResponseStatusException to be thrown");

                } catch (ResponseStatusException e) {

                        assertEquals(
                                        HttpStatus.INTERNAL_SERVER_ERROR,
                                        e.getStatusCode());
                }
        }
}