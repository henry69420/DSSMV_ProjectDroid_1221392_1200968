package com.example.dssmv.dtos;

import java.util.List;

public class AuthorDto {
    private String id;
    private String name;
    private String bio;
    private List<String> alternateNames;
    private String birthDate;
    private String deathDate;

    public AuthorDto(String id, String name, String bio, List<String> alternateNames, String birthDate, String deathDate) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.alternateNames = alternateNames;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getAlternateNames() {
        return alternateNames;
    }

    public void setAlternateNames(List<String> alternateNames) {
        this.alternateNames = alternateNames;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }
}
