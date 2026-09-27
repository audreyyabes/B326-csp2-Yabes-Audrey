package com.joysis.recordingapp.controller;

import com.joysis.recordingapp.model.Artist;
import com.joysis.recordingapp.service.ArtistService;

import java.util.List;

public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;

    }

    public List<Artist> handleViewAllArtist() {
        return artistService.getAllArtist();
    }

    public Artist handleReadArtistById(int id) {
        return artistService.readArtistById(id);
    }

    public List<Artist> handleReadAllArchivedArtists() {
        return artistService.readAllArchivedArtists();
    }

    public List<Artist> handleSearchArtist(String keyword) {
        return artistService.searchArtist(keyword);
    }

    public boolean handleCreateArtist(Artist artist) {
        return artistService.createArtist(artist);
    }

    public boolean handleUpdateArtist(Artist artist) {
        return artistService.updateArtist(artist);
    }

    public boolean handleArchivedArtist( int id) {
        return artistService.archivedArtist(id);
    }

    public boolean handleRestoreArtist(int id) {
        return artistService.restoreArtist(id);
    }

    public boolean handleDeleteArtist(int id) {
        return artistService.deleteArtist(id);
    }

}
