package com.hatchworks.challenge.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hatchworks.challenge.domain.Certification;
import com.hatchworks.challenge.domain.Cv;
import com.hatchworks.challenge.domain.Education;
import com.hatchworks.challenge.domain.Skill;
import com.hatchworks.challenge.domain.WorkExperience;
import com.hatchworks.challenge.dto.response.CertificationDto;
import com.hatchworks.challenge.dto.response.CvDataDto;
import com.hatchworks.challenge.dto.response.EducationDto;
import com.hatchworks.challenge.dto.response.PersonalInfoDto;
import com.hatchworks.challenge.dto.response.SkillDto;
import com.hatchworks.challenge.dto.response.WorkExperienceDto;

@Component
public class CvMapper {

    private final PersonalInfoMapper personalInfoMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final EducationMapper educationMapper;
    private final SkillMapper skillMapper;
    private final CertificationMapper certificationMapper;

    public CvMapper(PersonalInfoMapper personalInfoMapper, WorkExperienceMapper workExperienceMapper,
            EducationMapper educationMapper, SkillMapper skillMapper, CertificationMapper certificationMapper) {
        this.personalInfoMapper = personalInfoMapper;
        this.workExperienceMapper = workExperienceMapper;
        this.educationMapper = educationMapper;
        this.skillMapper = skillMapper;
        this.certificationMapper = certificationMapper;
    }

    public CvDataDto toDto(Cv cv) {

        return new CvDataDto(
                cv.getOriginalFileName(),
                cv.getLanguage(),
                cv.getPersonalInfo() != null ? personalInfoMapper.toDto(cv.getPersonalInfo()) : new PersonalInfoDto(),
                mapWorkExperiences(cv.getWorkExperiences()),
                mapEducations(cv.getEducations()),
                mapSkills(cv.getSkills()),
                mapCertifications(cv.getCertifications()));

    }

    private List<WorkExperienceDto> mapWorkExperiences(List<WorkExperience> source) {
        List<WorkExperienceDto> result = new ArrayList<>();
        if (source == null) {
            return result;
        }
        for (WorkExperience item : source) {
            result.add(workExperienceMapper.toDto(item));
        }
        return result;
    }

    private List<EducationDto> mapEducations(List<Education> source) {
        List<EducationDto> result = new ArrayList<>();
        if (source == null) {
            return result;
        }
        for (Education item : source) {
            result.add(educationMapper.toDto(item));
        }
        return result;
    }

    private List<SkillDto> mapSkills(List<Skill> source) {
        List<SkillDto> result = new ArrayList<>();
        if (source == null) {
            return result;
        }
        for (Skill item : source) {
            result.add(skillMapper.toDto(item));
        }
        return result;
    }

    private List<CertificationDto> mapCertifications(List<Certification> source) {
        List<CertificationDto> result = new ArrayList<>();
        if (source == null) {
            return result;
        }
        for (Certification item : source) {
            result.add(certificationMapper.toDto(item));
        }
        return result;
    }

}
