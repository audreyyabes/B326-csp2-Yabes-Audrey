package com.joysis.recordingapp;

import com.joysis.recordingapp.cliview.AlbumView;
import com.joysis.recordingapp.cliview.ArtistView;
import com.joysis.recordingapp.cliview.SongView;
import com.joysis.recordingapp.config.DbConnection;
import com.joysis.recordingapp.controller.AlbumController;
import com.joysis.recordingapp.controller.ArtistController;
import com.joysis.recordingapp.controller.SongController;
import com.joysis.recordingapp.repository.*;
import com.joysis.recordingapp.service.*;

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

        SongRepo songRepo = new SongRepoImpl(dbConnection);
        SongService songService = new SongServiceImpl(songRepo);
        SongController songController = new SongController(songService);
        SongView songView = new SongView(songController, scanner, albumController
        );

        //albumView.runAlbum();

        System.out.println("---RECORDING STUDIO APP MANAGEMENT---\n");
        System.out.println("1 - Artist Management");
        System.out.println("2 - Album Management");
        System.out.println("3 - Song Management");
        System.out.println("0 - Exit");

        System.out.println("Enter selection: ");
        int selectChoice = scanner.nextInt();

        do {
            switch (selectChoice){
                case 1 -> artistView.runArtist();
                case 2 -> albumView.runAlbum();
                case 3 -> songView.SongMenu();
                default -> System.out.println("Invalid choice. Try again.");
            }
            if (selectChoice != 0) {
                System.out.println("\nPress enter to continue...");
                scanner.nextLine();
            }
        } while(selectChoice != 0);



        scanner.close();
    }
}
