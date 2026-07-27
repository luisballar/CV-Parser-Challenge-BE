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

        IMPORTANT extraction rules for work experience sections:
        - A company/project heading often looks like "CompanyName – Project Description" (separated by a dash).
          Use the text BEFORE the dash as companyName. If no dash is present, use the full heading as companyName.
        - A line starting with "Role:" indicates the jobTitle — extract the text after "Role:" as jobTitle.
        - Bullet points (lines starting with "?", "-", "•", or similar symbols) belong to the description field.
          Merge all bullets for that entry into a single description string, separated by ". " or line breaks.
        - A "Technologies:" line is part of the description, not a separate field — include it in description.
        - If no explicit dates are found for an entry, set startDate and endDate to null, but still extract companyName, jobTitle, and description.

        IMPORTANT extraction rules for education:
        - Institution names are usually in uppercase or as a clear heading (e.g. "UNIVERSITY OF COSTA RICA").
        - Degree and field of study often appear together (e.g. "Bachelor's Degree in Business Computing") — split them into degree ("Bachelor's Degree") and fieldOfStudy ("Business Computing").
        - Graduation ranges like "2022 – Present" mean startDate is the first year and isCurrent-like status applies; if endDate says "Present", return endDate as null.

        IMPORTANT extraction rules for skills:
        - Skills are often grouped under a category label (e.g. "Programming and Frameworks:", "Tools:", "Languages:") followed by a comma-separated list.
        - Use that label as the category, and create one skill entry per item in the list.
        - For "Languages" category specifically, if a proficiency is noted in parentheses (e.g. "Spanish (Native)"), extract it as proficiencyLevel.

        Required JSON structure:

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

          "workExperiences": [
            {
              "jobTitle": null,
              "companyName": null,
              "location": null,
              "startDate": null,
              "endDate": null,
              "isCurrent": null,
              "description": null
            }
          ],

          "educations": [
            {
              "institutionName": null,
              "degree": null,
              "fieldOfStudy": null,
              "startDate": null,
              "endDate": null
            }
          ],

          "skills": [
            {
              "name": null,
              "category": null,
              "proficiencyLevel": null
            }
          ],

          "certifications": [
            {
              "name": null,
              "issuingOrganization": null,
              "issueDate": null,
              "expirationDate": null
            }
          ],

          "language": "EN or ES"
        }

        Rules:
        - Return ONLY valid JSON.
        - Do not wrap the JSON in markdown.
        - If information is missing, use null.
        - Do not invent information.
        - Use empty arrays when there are no items.
        - Dates must be returned in ISO format (yyyy-MM-dd) when possible. Otherwise return the original text as-is, or null if not present.
        - Detect if the document is a CV.
        - If it is not a CV, return only:

          {
            "error": "NOT_A_CV"
          }

        Resume text:

        %s
        """
        .formatted(cvText);

    GenerateContentConfig config = GenerateContentConfig.builder()
        .temperature((float) 0.1)
        .responseMimeType("application/json")
        .build();

    GenerateContentResponse response = client.models.generateContent(
        "gemini-3.5-flash-lite",
        prompt,
        config);

    return response.text();
  }
}
