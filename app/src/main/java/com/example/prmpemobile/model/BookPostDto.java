package com.example.prmpemobile.model;

public class BookPostDto {

    private String name;
    private String datePublished;
    private String genre;
    private Long authorId;

    public BookPostDto() {
    }

    public BookPostDto(String name, String datePublished, String genre, Long authorId) {
        this.name = name;
        this.datePublished = datePublished;
        this.genre = genre;
        this.authorId = authorId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
