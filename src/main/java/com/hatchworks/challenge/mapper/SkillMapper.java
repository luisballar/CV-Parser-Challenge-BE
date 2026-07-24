package com.hatchworks.challenge.mapper;

import org.springframework.stereotype.Component;

import com.hatchworks.challenge.domain.Skill;
import com.hatchworks.challenge.dto.response.SkillDto;

@Component
public class SkillMapper {
    public SkillDto toDto(Skill skill) {
        SkillDto dto = new SkillDto();

        dto.setName(skill.getName());
        dto.setCategory(skill.getCategory());
        dto.setProficiencyLevel(skill.getProficiencyLevel() != null ? skill.getProficiencyLevel().toString() : null);

        return dto;
    }
}
