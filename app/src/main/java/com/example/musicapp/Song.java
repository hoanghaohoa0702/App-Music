package com.example.musicapp;

public class Song {
    private String title;
    private int file;

    public Song(String title, int file) {
        this.title = title;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public int getFile() {
        return file;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
