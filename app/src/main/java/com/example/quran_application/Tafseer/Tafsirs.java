package com.example.quran_application.Tafseer;

public class Tafsirs {
    private int id;
    private String name,author_name,slug,language_name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public Tafsirs(int id, String name, String author_name, String slug, String language_name) {
        this.id = id;
        this.name = name;
        this.author_name = author_name;
        this.slug = slug;
        this.language_name = language_name;
    }
}
