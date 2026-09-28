package com.joysis.recordingapp.service;

import com.joysis.recordingapp.model.Album;
import com.joysis.recordingapp.repository.AlbumRepo;

import java.util.List;

public class AlbumServiceImpl implements AlbumService{

    private final AlbumRepo albumRepo;
    public AlbumServiceImpl(AlbumRepo albumRepo){
        this.albumRepo = albumRepo;
    }

    @Override
    public List<Album> getAllAlbum() {
        return albumRepo.getAllAlbum();
    }

    @Override
    public List<Album> searchAlbum(String keyword) {
        if(keyword == null | keyword.trim().isEmpty()){
            System.out.println("Search Album Keyword cannot be empty..");
            return List.of(); // List.of(); = not null but empty
        }
        return albumRepo.searchAlbum(keyword.trim());
    }

    @Override
    public Album readAlbumById(int id) {
        if (id <= 0){
            System.out.println("Invalid Album ID..");
            return null;
        }
        Album album = albumRepo.readAlbumById(id);
        if (album == null){
            System.out.println("Album not found..");
        }
        return album;
    }

    @Override
    public boolean createAlbum(Album album) {
        if (album == null) {
            System.out.println("Album object cannot be null.");
            return false;
        }

        if (album.getName() == null || album.getName().trim().isEmpty()) {
            System.out.println("Album name is required.");
            return false;
        }


        album.setName(album.getName().trim());
        return albumRepo.createAlbum(album);
    }

    @Override
    public boolean updateAlbum(Album album) {
        if(album.getName() == null || album.getName().trim().isEmpty()){
            System.out.println("Album name is required to update");
            return false;
        }
        return albumRepo.updateAlbum(album);
    }

    @Override
    public boolean deleteAlbum(int id) {
        if (id <= 0){
            System.out.println("Invalid album ID...");
            return false;
        }
        return albumRepo.deleteAlbum(id);
    }

    @Override
    public boolean archivedAlbum(int id) {
        if (id <= 0) {
            System.out.println("Invalid Album ID for archive.");
            return false;
        }

        return albumRepo.archivedAlbum(id);
    }

    @Override
    public boolean restoreAlbum(int id) {
        if (id <= 0){
            System.out.println("Invalid artist ID to restore...");
            return false;
        }
        return albumRepo.restoreAlbum(id);
    }

    @Override
    public List<Album> readAllArchivedAlbum() {
        return albumRepo.readAllArchivedAlbum();
    }

}
