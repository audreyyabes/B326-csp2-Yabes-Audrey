package com.joysis.recordingapp.controller;

import com.joysis.recordingapp.model.Album;
import com.joysis.recordingapp.service.AlbumService;

import java.util.List;

public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    public List<Album> handleGetAllAlbum() {
        return albumService.getAllAlbum();
    }

    public List<Album> handleSearchAlbum(String keyword) {
        return albumService.searchAlbum(keyword);
    }

    public Album handleReadAlbumById(int id) {
        return albumService.readAlbumById(id);
    }

    public boolean handleCreateAlbum(Album album) {
        return albumService.createAlbum(album);
    }

    public boolean handleUpdateAlbum(Album album) {
        return albumService.updateAlbum(album);
    }

    public boolean handleDeleteAlbum(int id) {
        return albumService.deleteAlbum(id);
    }

    public boolean handleArchivedAlbum(int id) {
        return albumService.archivedAlbum(id);
    }

    public boolean handleRestoreAlbum(int id) {
        return albumService.restoreAlbum(id);
    }
    public List<Album> readAllArchivedAlbum() {
        return albumService.readAllArchivedAlbum();
    }


}
