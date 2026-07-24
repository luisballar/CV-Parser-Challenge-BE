package com.hatchworks.challenge.mapper;

import org.springframework.stereotype.Component;

import com.hatchworks.challenge.domain.Education;
import com.hatchworks.challenge.dto.response.EducationDto;

@Component
public class EducationMapper {

     public EducationDto toDto(Education education) {
        EducationDto dto = new EducationDto();
        
        dto.setInstitutionName(education.getInstitutionName());
        dto.setDegree(education.getDegree());
        dto.setFieldOfStudy(education.getFieldOfStudy());
        dto.setStartDate(education.getStartDate() != null ? education.getStartDate().toString() : null);
        dto.setEndDate(education.getEndDate() != null ? education.getEndDate().toString() : null);

        return dto;
    }
}
