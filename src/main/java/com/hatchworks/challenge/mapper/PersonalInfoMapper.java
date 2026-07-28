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
        dto.setLocation(personalInfo.getLocation());
        dto.setLinkedinUrl(personalInfo.getLinkedinUrl());
        dto.setPortfolioUrl(personalInfo.getPortfolioUrl());
        dto.setSummary(personalInfo.getSummary());

        return dto;
    }

}
