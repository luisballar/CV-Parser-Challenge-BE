package com.hatchworks.challenge.mapper;

import org.springframework.stereotype.Component;

import com.hatchworks.challenge.domain.WorkExperience;
import com.hatchworks.challenge.dto.response.WorkExperienceDto;

@Component
public class WorkExperienceMapper {
    public WorkExperienceDto toDto(WorkExperience workExperience) {
        WorkExperienceDto dto = new WorkExperienceDto();

        dto.setJobTitle(workExperience.getJobTitle());
        dto.setCompanyName(workExperience.getCompanyName());
        dto.setLocation(workExperience.getLocation());
        dto.setStartDate(workExperience.getStartDate() != null ? workExperience.getStartDate().toString() : null);
        dto.setEndDate(workExperience.getEndDate() != null ? workExperience.getEndDate().toString() : null);
        dto.setIsCurrent(workExperience.getIsCurrent());
        dto.setDescription(workExperience.getDescription());
        
        return dto;
    }
}
