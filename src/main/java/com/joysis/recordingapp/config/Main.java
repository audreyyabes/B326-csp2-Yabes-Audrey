package com.joysis.recordingapp.config;

import com.joysis.recordingapp.dao.ArtistDao;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        DbConnection dbConnection = new DbConnection();
        ArtistDao artistDao = new ArtistDao(dbConnection);
        //artistDao.readAllArtist();
        //artistDao.createArtist("Red Velvet");
        //artistDao.updateArtist("Twice", 6);
        //artistDao.archivedArtist("Twice", 6);
        //artistDao.restoreArtist("Twice", 6);
        //artistDao.deleteArtist(10);
        //artistDao.readArtistById(5);
        //artistDao.searchArtist("LI");
        //artistDao.readAllArchivedArtists();
    }
    // CRUD Operation

    public void createArtist(){

    }

}
