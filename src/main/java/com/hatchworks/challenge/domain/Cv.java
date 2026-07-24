package com.hatchworks.challenge.domain;

import java.util.List;
import java.util.UUID;

public class Cv {

    private UUID id;
    private String originalFileName;
    private String fileType; // "PDF" o "DOCX"
    private String rawText;
    private String language; // "ES" o "EN"
    private String extractionStatus; // "SUCCESS", "PARTIAL", "FAILED"
    private String createdAt;

    private PersonalInfo personalInfo;
    private List<WorkExperience> workExperiences;
    private List<Education> educations;
    private List<Skill> skills;
    private List<Certification> certifications;

    public Cv() {
    }

    public Cv(UUID id, String originalFileName, String fileType, String rawText, String language,
            String extractionStatus, String createdAt, PersonalInfo personalInfo,
            List<WorkExperience> workExperiences, List<Education> educations, List<Skill> skills,
            List<Certification> certifications) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.fileType = fileType;
        this.rawText = rawText;
        this.language = language;
        this.extractionStatus = extractionStatus;
        this.createdAt = createdAt;
        this.personalInfo = personalInfo;
        this.workExperiences = workExperiences;
        this.educations = educations;
        this.skills = skills;
        this.certifications = certifications;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getExtractionStatus() {
        return extractionStatus;
    }

    public void setExtractionStatus(String extractionStatus) {
        this.extractionStatus = extractionStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

}
