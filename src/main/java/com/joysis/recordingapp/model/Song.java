package com.joysis.recordingapp.model;

public class Song {
    private int id;
    private String title;
    private int length;
    private String genre;
    private int album_id;
    private String albumName;

    public Song(String title, int length, String genre, int album_id) {
        this.title = title;
        this.length = length;
        this.genre = genre;
        this.album_id = album_id;
    }

    public Song(int id, String title, int length, String genre,
                int album_id, String albumName) {
        this.id = id;
        this.title = title;
        this.length = length;
        this.genre = genre;
        this.album_id = album_id;
        this.albumName = albumName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }


}
