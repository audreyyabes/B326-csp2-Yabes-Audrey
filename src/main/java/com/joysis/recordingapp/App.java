package com.joysis.recordingapp;

import com.joysis.recordingapp.cliview.AlbumView;
import com.joysis.recordingapp.cliview.ArtistView;
import com.joysis.recordingapp.config.DbConnection;
import com.joysis.recordingapp.controller.AlbumController;
import com.joysis.recordingapp.controller.ArtistController;
import com.joysis.recordingapp.repository.AlbumRepo;
import com.joysis.recordingapp.repository.AlbumRepoImpl;
import com.joysis.recordingapp.repository.ArtistRepo;
import com.joysis.recordingapp.repository.ArtistRepoImpl;
import com.joysis.recordingapp.service.AlbumService;
import com.joysis.recordingapp.service.AlbumServiceImpl;
import com.joysis.recordingapp.service.ArtistService;
import com.joysis.recordingapp.service.ArtistServiceImpl;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DbConnection dbConnection = new DbConnection();

        // ----- Artist feature wiring -----
        ArtistRepo artistRepository = new ArtistRepoImpl(dbConnection);
        ArtistService artistService = new ArtistServiceImpl(artistRepository);
        ArtistController artistController = new ArtistController(artistService);
        ArtistView artistView = new ArtistView(artistController, scanner);

        // ----- Album feature wiring -----
        AlbumRepo albumRepo = new AlbumRepoImpl(dbConnection);
        AlbumService albumService = new AlbumServiceImpl(albumRepo);
        AlbumController albumController = new AlbumController(albumService);
        AlbumView albumView = new AlbumView(albumController, scanner, artistController);

        albumView.runAlbum();

//        System.out.println("---RECORDING STUDIO APP MANAGEMENT---\n");
//        System.out.println("1 - Artist Management");
//        System.out.println("2 - Album Management");
//        System.out.println("0 - Exit");
//
//        System.out.println("Enter selection: ");
//        int selectChoice = scanner.nextInt();
//
//        do {
//            switch (selectChoice){
//                case 1 -> artistView.runArtist();
//                case 2 -> albumView.runAlbum();
//            }
//        } while(selectChoice != 0);



        scanner.close();
    }
}
