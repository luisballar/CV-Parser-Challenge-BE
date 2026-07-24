package com.hatchworks.challenge.mapper;

import org.springframework.stereotype.Component;

import com.hatchworks.challenge.domain.PersonalInfo;
import com.hatchworks.challenge.dto.response.PersonalInfoDto;

@Component
public class PersonalInfoMapper {

    public PersonalInfoDto toDto(PersonalInfo personalInfo) {
        PersonalInfoDto dto = new PersonalInfoDto();

    
        dto.setFullName(personalInfo.getFullName());
        dto.setEmail(personalInfo.getEmail());
        dto.setPhone(personalInfo.getPhone());
        dto.setLocation(personalInfo.getLocation() != null ? personalInfo.getLocation().toString() : null);
        dto.setLinkedinUrl(personalInfo.getLinkedinUrl() != null ? personalInfo.getLinkedinUrl().toString() : null);
        dto.setPortfolioUrl(personalInfo.getPortfolioUrl() != null ? personalInfo.getPortfolioUrl().toString() : null);
        dto.setSummary(personalInfo.getSummary());

        return dto;
    }

}
