package com.joysis.recordingapp.config;

import com.joysis.recordingapp.dao.ArtistDao;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        DbConnection dbConnection = new DbConnection();
        ArtistDao artistDao = new ArtistDao(dbConnection);
        artistDao.readAllArtist();
        //artistDao.createArtist("Twice");
        artistDao.updateArtist("Twice", 6);
    }
    // CRUD Operation

    public void createArtist(){

    }

}
