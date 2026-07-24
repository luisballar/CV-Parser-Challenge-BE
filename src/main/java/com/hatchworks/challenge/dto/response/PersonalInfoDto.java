package com.hatchworks.challenge.dto.response;

public class PersonalInfoDto {

    private String fullName;
    private String email;
    private String phone;
    private String location;
    private String linkedinUrl;
    private String portfolioUrl;
    private String summary;

    public PersonalInfoDto() {
    }

    public PersonalInfoDto(String fullName, String email, String phone, String location, String linkedinUrl,
            String portfolioUrl, String summary) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.linkedinUrl = linkedinUrl;
        this.portfolioUrl = portfolioUrl;
        this.summary = summary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
