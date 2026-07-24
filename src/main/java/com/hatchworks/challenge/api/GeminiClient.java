package com.hatchworks.challenge.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

@Component
public class GeminiClient {

    private final Client client;

    public GeminiClient(
            @Value("${gemini.api.key}") String apiKey) {

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String extractResumeData(String cvText) {

        String prompt = """
                You are a CV parser AI.

                Analyze the following resume text and extract structured information.

                Return ONLY valid JSON.
                Do not add explanations.
                Do not use markdown.

                Required structure:

                {
                  "personalInfo": {
                    "fullName": null,
                    "email": null,
                    "phone": null,
                    "location": null,
                    "linkedinUrl": null,
                    "portfolioUrl": null,
                    "summary": null
                  },

                  "workExperiences": [],

                  "educations": [],

                  "skills": [],

                  "certifications": [],

                  "language": "EN or ES"
                }


                Rules:
                - If information is missing use null.
                - Do not invent information.
                - Detect if this document is a CV.
                - If it is not a CV, return:

                {
                  "error": "NOT_A_CV"
                }


                Resume text:

                %s
                """.formatted(cvText);

        GenerateContentConfig config = GenerateContentConfig.builder()
                .temperature((float) 0.1)
                .responseMimeType("application/json")
                .build();

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                config);

        return response.text();
    }
}
