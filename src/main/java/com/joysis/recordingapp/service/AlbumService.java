package com.joysis.recordingapp.service;


import com.joysis.recordingapp.model.Album;

import java.util.List;

public interface AlbumService {

    List<Album> getAllAlbum();
    List<Album> searchAlbum(String keyword);
    List<Album> readAllArchivedAlbum();
    Album readAlbumById(int id);
    boolean createAlbum(Album album);
    boolean updateAlbum(Album album);
    boolean deleteAlbum(int id);
    boolean archivedAlbum (int id);
    boolean restoreAlbum (int id);
}
