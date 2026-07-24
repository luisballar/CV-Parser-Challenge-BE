package com.hatchworks.challenge.dto.response;

import java.util.List;

public class CvUploadResponse {

    private String originalFileName;
    private String language;
    private PersonalInfoDto personalInfo;
    private List<WorkExperienceDto> workExperiences;
    private List<EducationDto> educations;
    private List<SkillDto> skills;
    private List<CertificationDto> certifications;

    public CvUploadResponse() {
    }

    public CvUploadResponse(String originalFileName, String language, PersonalInfoDto personalInfo,
            List<WorkExperienceDto> workExperiences, List<EducationDto> educations, List<SkillDto> skills,
            List<CertificationDto> certifications) {
        this.originalFileName = originalFileName;
        this.language = language;
        this.personalInfo = personalInfo;
        this.workExperiences = workExperiences;
        this.educations = educations;
        this.skills = skills;
        this.certifications = certifications;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public PersonalInfoDto getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfoDto personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<WorkExperienceDto> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperienceDto> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public List<EducationDto> getEducations() {
        return educations;
    }

    public void setEducations(List<EducationDto> educations) {
        this.educations = educations;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    public List<CertificationDto> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<CertificationDto> certifications) {
        this.certifications = certifications;
    }

}
