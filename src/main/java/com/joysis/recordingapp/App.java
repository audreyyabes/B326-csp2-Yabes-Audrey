package com.joysis.recordingapp;

import com.joysis.recordingapp.cliview.ArtistView;
import com.joysis.recordingapp.config.DbConnection;
import com.joysis.recordingapp.controller.ArtistController;
import com.joysis.recordingapp.repository.ArtistRepo;
import com.joysis.recordingapp.repository.ArtistRepoImpl;
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

        // Straight into Artist Management — no main menu needed yet
        artistView.run();

        scanner.close();
    }
}
