package com.hatchworks.challenge.mapper;

import org.springframework.stereotype.Component;

import com.hatchworks.challenge.domain.Certification;
import com.hatchworks.challenge.dto.response.CertificationDto;

@Component
public class CertificationMapper {

    public CertificationDto toDto(Certification certification) {
        CertificationDto dto = new CertificationDto();
        
        dto.setName(certification.getName());
        dto.setIssuingOrganization(certification.getIssuingOrganization());
        dto.setIssueDate(certification.getIssueDate());
        dto.setExpirationDate(certification.getExpirationDate());
        dto.setCredentialId(certification.getCredentialId());
        dto.setCredentialUrl(certification.getCredentialUrl());

        return dto;
    }
}
