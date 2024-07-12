package com.example.prmpemobile.model;


public class ItemPostDto {
    private String name;
    private String description;

    public ItemPostDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ItemPostDto() {
    }

    // Getters and setters...


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

