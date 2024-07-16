package com.example.prmpemobile.model;


import java.util.Date;

public class BookPostDto {

    private String name;
    private Date datePublished;
    private String genre; // Use String for Genre, or you can use Enum if you have it
    private Long authorId; // Assuming you need to reference an Author

    // Constructors
    public BookPostDto() {
    }

    public BookPostDto(String name, Date datePublished, String genre, Long authorId) {
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

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
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
