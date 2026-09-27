package com.joysis.recordingapp.service;

import com.joysis.recordingapp.model.Artist;
import com.joysis.recordingapp.repository.ArtistRepo;

import java.util.List;

public class ArtistServiceImpl implements ArtistService{

    private final ArtistRepo artistRepo; //composition

    public ArtistServiceImpl(ArtistRepo artistRepo){
        this.artistRepo = artistRepo;
    }

    @Override
    public List<Artist> getAllArtist(){
        return artistRepo.getAllArtist();
    }

    @Override
    public Artist readArtistById (int id){
        if (id <= 0){
            System.out.println("Invalid Artist ID..");
            return null;
        }
        Artist artist = artistRepo.readArtistById(id);
        if(artist == null){
            System.out.println("Artist Not Found");
        }
        return artist;
    }

    @Override
    public boolean createArtist(Artist artist) {
        if (artist == null) {
            System.out.println("Artist object cannot be null.");
            return false;
        }

        if (artist.getName() == null || artist.getName().trim().isEmpty()) {
            System.out.println("Artist name is required.");
            return false;
        }


        artist.setName(artist.getName().trim());
        return artistRepo.createArtist(artist);
    }


    @Override
    public List<Artist> searchArtist (String keyword){
        if(keyword == null | keyword.trim().isEmpty()){
            System.out.println("Search Keyword cannot be empty..");
            return List.of(); // List.of(); = not null but empty
        }
        return artistRepo.searchArtist(keyword.trim());
    }

    @Override
    public List<Artist> readAllArchivedArtists(){
        return artistRepo.readAllArchivedArtists();
    }

    @Override
    public boolean updateArtist(Artist artist){
        if(artist.getName() == null || artist.getName().trim().isEmpty()){
            System.out.println("Artist name is required");
            return false;
        }
        return artistRepo.updateArtist(artist);
    }

    @Override
    public boolean archivedArtist(int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID for archive.");
            return false;
        }

        return artistRepo.archivedArtist(id);
    }

    @Override
    public boolean restoreArtist (int id){
        if (id <= 0){
            System.out.println("Invalid artist ID to restore...");
            return false;
        }
        return artistRepo.restoreArtist(id);
    }

    @Override
    public boolean deleteArtist (int id){
        if (id <= 0){
            System.out.println("Invalid artist ID...");
            return false;
        }
        return artistRepo.deleteArtist(id);
    }

}
