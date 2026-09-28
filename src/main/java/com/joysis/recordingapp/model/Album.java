package com.joysis.recordingapp.model;

import com.joysis.recordingapp.controller.AlbumController;

public class Album { private int id;
    private String name;
    private int year;
    private int artist_id;
    private int is_archivedAlbum;
    private String artistName;

    public Album(String name, int year, int artist_id) {
        this.name = name;
        this.year = year;
        this.artist_id = artist_id;
    }

    public Album(int id, String name, int year, int artist_id, String artistName) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.artist_id = artist_id;
        this.artistName = artistName;
    }

    public Album(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getArtistName() {
        return artistName;
    }


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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public int getIs_archivedAlbum() {
        return is_archivedAlbum;
    }

    public void setIs_archivedAlbum(int is_archivedAlbum) {
        this.is_archivedAlbum = is_archivedAlbum;
    }


    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", artist_id=" + artist_id +
                ", is_archivedAlbum=" + is_archivedAlbum +
                '}';
    }
}
