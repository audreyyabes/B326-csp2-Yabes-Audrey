package com.joysis.recordingapp.repository;

import com.joysis.recordingapp.model.Artist;

import java.util.List;

public interface ArtistRepo {

    List<Artist> getAllArtist();
    List<Artist> readAllArchivedArtists();
    List<Artist> searchArtist (String keyword);
    Artist readArtistById (int id);
    boolean createArtist(Artist artist);
    boolean updateArtist(Artist artist);
    boolean archivedArtist (int id);
    boolean restoreArtist (int id);
    boolean deleteArtist (int id);

}
