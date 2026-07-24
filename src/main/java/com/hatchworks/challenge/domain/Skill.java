package com.hatchworks.challenge.domain;

import java.util.UUID;

public class Skill {

    private UUID id;
    private String name;
    private String category; // "TECHNICAL", "SOFT", "LANGUAGE", "TOOL"
    private String proficiencyLevel; // "BASIC", "INTERMEDIATE", "ADVANCED"

    public Skill() {
    }

    public Skill(UUID id, String name, String category, String proficiencyLevel) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.proficiencyLevel = proficiencyLevel;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

}
