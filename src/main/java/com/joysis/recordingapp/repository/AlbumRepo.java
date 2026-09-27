package com.joysis.recordingapp.repository;

import com.joysis.recordingapp.model.Album;

import java.util.List;

public interface AlbumRepo {

    List<Album> getAllAlbum();
    List<Album> searchAlbum(String keyword);
    Album readAlbumById(int id);
    boolean createAlbum(Album album);
    boolean updateAlbum(Album album);
    boolean deleteAlbum(int id);
    boolean archivedAlbum (String name, int id);
    boolean restoreAlbum (String name, int id);

}
