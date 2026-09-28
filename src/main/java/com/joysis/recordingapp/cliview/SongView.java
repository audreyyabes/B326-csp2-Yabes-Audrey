package com.joysis.recordingapp.cliview;

import com.joysis.recordingapp.controller.AlbumController;
import com.joysis.recordingapp.controller.SongController;
import com.joysis.recordingapp.model.Album;
import com.joysis.recordingapp.model.Song;

import java.util.List;
import java.util.Scanner;

public class SongView {

    private final SongController songController;
    private final AlbumController albumController;
    private final Scanner scanner;

    public SongView(
            SongController songController,
            Scanner scanner, AlbumController albumController
    ) {
        this.songController = songController;
        this.albumController = albumController;
        this.scanner = new Scanner(System.in);
    }

    public void SongMenu() {

        while (true) {

            System.out.println();
            System.out.println("--- Song Menu ---");
            System.out.println("1. View All Songs");
            System.out.println("2. Search Song");
            System.out.println("3. Add Song");
            System.out.println("4. Update Song");
            System.out.println("5. Delete Song");
            System.out.println("0. Back");

            System.out.println("Choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    viewAllSong();
                    break;

                case 2:
                    searchSong();
                    break;

                case 3:
                    addSong();
                    break;

                case 4:
                    updateSong();
                    break;

                case 5:
                    deleteSong();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAllSong() {

        System.out.println("--- View all Song/s ---");

        List<Song> songs = songController.handleGetAllSong();

        printAllSong(songs);
    }

    private void printAllSong(List<Song> songs) {

        System.out.printf(
                "%-5s | %-25s | %-8s | %-15s | %-20s%n",
                "ID",
                "Title",
                "Length",
                "Genre",
                "Album"
        );

        System.out.println("-".repeat(85));

        for (Song song : songs) {

            System.out.printf(
                    "%-5d | %-25s | %-8d | %-15s | %-20s%n",
                    song.getId(),
                    song.getTitle(),
                    song.getLength(),
                    song.getGenre(),
                    song.getAlbumName()
            );
        }
    }

    private void searchSong() {

        System.out.println("--- Search Song ---");

        System.out.println("Song title to search:");
        String keyword = scanner.nextLine();

        List<Song> songs =
                songController.handleSearchSong(keyword);

        if (songs.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }

        printAllSong(songs);
    }

    private void addSong() {

        System.out.println("--- Add Song ---");

        viewAllAlbum();

        System.out.println("Select Album ID:");
        int albumId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Song title:");
        String title = scanner.nextLine();

        System.out.println("Enter Song length:");
        int length = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Song genre:");
        String genre = scanner.nextLine();

        Song song = new Song(
                title,
                length,
                genre,
                albumId
        );

        boolean isSuccess =
                songController.handleCreateSong(song);

        System.out.println(
                isSuccess
                        ? "Song added successfully."
                        : "Failed to add Song"
        );

        if (isSuccess) {
            System.out.println();
            viewAllSong();
        }
    }

    private void updateSong() {

        System.out.println("--- Update Song ---");

        System.out.println("Enter Song ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Song current =
                songController.handleReadSongById(id);

        if (current == null) {
            System.out.println(
                    "No song found with ID " + id + "."
            );
            return;
        }

        System.out.println(
                "Song title to update: "
                        + current.getTitle()
        );

        System.out.println(
                "update [" + current.getTitle() + "] title:"
        );

        String title = scanner.nextLine();

        if (title.trim().isEmpty()) {
            System.out.println("Song title cannot be empty.");
            return;
        }

        current.setTitle(title);

        boolean isSuccess =
                songController.handleUpdateSong(current);

        System.out.println(
                isSuccess
                        ? "Song updated successfully."
                        : "Failed to update Song"
        );

        if (isSuccess) {
            System.out.println();
            viewAllSong();
        }
    }

    private void deleteSong() {

        System.out.println("--- Delete Song ---");

        System.out.println("Enter Song ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Song current =
                songController.handleReadSongById(id);

        if (current == null) {
            System.out.println(
                    "No song found with ID " + id + "."
            );
            return;
        }

        System.out.println(
                "Delete [" + current.getTitle() + "]?"
        );

        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        boolean isSuccess =
                songController.handleDeleteSong(id);

        System.out.println(
                isSuccess
                        ? "Song deleted successfully."
                        : "Failed to delete Song"
        );

        if (isSuccess) {
            System.out.println();
            viewAllSong();
        }
    }

    private void viewAllAlbum() {

        List<Album> albums =
                albumController.handleGetAllAlbum();

        System.out.printf(
                "%-5s | %-30s | %-6s%n",
                "ID",
                "Album",
                "Year"
        );

        System.out.println("-".repeat(50));

        for (Album album : albums) {

            System.out.printf(
                    "%-5d | %-30s | %-6d%n",
                    album.getId(),
                    album.getName(),
                    album.getYear()
            );
        }
    }
}