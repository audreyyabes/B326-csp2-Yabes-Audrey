package com.joysis.recordingapp.cliview;

import com.joysis.recordingapp.controller.AlbumController;
import com.joysis.recordingapp.controller.ArtistController;
import com.joysis.recordingapp.model.Album;
import com.joysis.recordingapp.model.Artist;

import java.util.List;
import java.util.Scanner;



public class AlbumView {

    private final ArtistController artistController;
    private final AlbumController albumController;
    private final Scanner scanner;

    public AlbumView(AlbumController albumController, Scanner scanner, ArtistController artistController){
        this.albumController = albumController;
        this.artistController = artistController;
        this.scanner = scanner;
    }

    public void runAlbum() {
        int choice;
        do {
            printAlbumMenu();
            choice = promptChoice();

            switch (choice) {
                case 1 -> viewAllAlbum();
                case 2 -> searchAlbum();
                case 3 -> addAlbum();
                case 4 -> updateAlbum();
                case 5 -> archivedAlbum();
                case 6 -> restoreAlbum();
                case 7 -> deleteAlbum();
                case 8 -> viewAllArchivedAlbum();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.println("\nPress enter to continue...");
                scanner.nextLine();
            }
        } while(choice != 0);

    }


    private void printAlbumMenu() {
        System.out.println("Album Management\n-----");
        System.out.println("1. View All Album"); // done
        System.out.println("2. Search Album"); //done
        System.out.println("3. Add Album"); // done
        System.out.println("4. Update Album"); // done
        System.out.println("5. Archive Album"); // done
        System.out.println("6. Restore Album"); //done
        System.out.println("7. Delete Album"); //done
        System.out.println("8. View All Archived Album"); //done
        System.out.println("0. Back");
    }

    public int promptChoice() {
        System.out.println("Choice: ");
        return readInt();
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (RuntimeException e) {
                System.out.println("Enter a valid number: ");
            }
        }
    }

    private void viewAllAlbum() {
        System.out.println("--- View all Album/s ---");
        List<Album> album = albumController.handleGetAllAlbum();
        printAllAlbum(album);
    }

    private void printAllAlbum(List<Album> albums){
        System.out.printf("%-5s | %-20s | %-6s | %-20s%n",
                "ID", "Name", "Year", "Artist");

        System.out.println("-".repeat(60));

        for (Album album : albums) {
            System.out.printf("%-5d | %-20s | %-6d | %-20s%n",
                    album.getId(),
                    album.getName(),
                    album.getYear(),
                    album.getArtistName());
        }

    }

    private void searchAlbum(){
        System.out.println("--- Search Album ---");
        System.out.println("Enter album name: ");
        String keyword = scanner.next();
        List<Album> albums = albumController.handleSearchAlbum(keyword);
        if (albums.isEmpty()) {
            System.out.println("No albums found.");
        } else {
            printAllAlbum(albums);
        }

    }

    private void viewAllArtist() {
        List<Artist> artists = artistController.handleViewAllArtist();

        System.out.printf("%-5s | %-30s%n", "ID", "Artist");
        System.out.println("-".repeat(40));

        for (Artist artist : artists) {
            System.out.printf(
                    "%-5d | %-30s%n",
                    artist.getId(),
                    artist.getName()
            );
        }
    }

    private void addAlbum() {
        System.out.println("--- Add Album ---");

        // Show Artists
        viewAllArtist();

        System.out.println("Select Artist ID: ");
        int artist_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Album released year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Album name: ");
        String name = scanner.nextLine();

        Album album = new Album(name, year, artist_id);

        boolean isSuccess = albumController.handleCreateAlbum(album);

        System.out.println(
                isSuccess
                        ? "Album added successfully."
                        : "Failed to add Album"
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbum();
        }
    }

    private void updateAlbum() {
        System.out.println("--- Update Album ---\n");

        viewAllAlbum();
        System.out.println("\nEnter Album ID:");
        int id = readInt();

        Album current = albumController.handleReadAlbumById(id);

        if (current == null) {
            System.out.println("No album found with ID " + id + ".");
            return;
        }

        System.out.println("Album Name to update: " + current.getName());

        System.out.println("update [" + current.getName() + "] name:");
        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Album name cannot be empty.");
            return;
        }

        current.setName(name);

        boolean isSuccess = albumController.handleUpdateAlbum(current);

        System.out.println(
                isSuccess
                        ? "Album updated successfully."
                        : "Failed to update album"
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbum();
        }
    }

    private void archivedAlbum() {
        System.out.println("--- Archived Album ---");
        viewAllAlbum();
        System.out.println("Enter Album ID: ");
        int id = scanner.nextInt();

        boolean isSuccess = albumController.handleArchivedAlbum(id);

        System.out.println(isSuccess ? "Album successfully Archived." : "Failed to archive Album");

        if (isSuccess) {
            System.out.println();
            viewAllAlbum();
        }
    }

    private void restoreAlbum() {
        System.out.println("--- Restore Album ---");
        viewAllAlbum();
        System.out.println("Enter Album ID: ");
        int id = scanner.nextInt();

        boolean isSuccess = albumController.handleRestoreAlbum(id);

        System.out.println(isSuccess ? "Album successfully Restore." : "Failed to Restore Album");

        if (isSuccess) {
            System.out.println();
            viewAllAlbum();
        }
    }

    private void deleteAlbum() {
        System.out.println("--- Delete Album ---");
        viewAllAlbum();
        System.out.println("Enter Album ID: ");
        int id = scanner.nextInt();

        boolean isSuccess = albumController.handleDeleteAlbum(id);

        System.out.println(isSuccess ? "Album successfully Delete." : "Failed to Delete Album");

        if (isSuccess) {
            System.out.println();
            viewAllAlbum();
        }
    }

    private void viewAllArchivedAlbum() {
        System.out.println("--- View all Archived Album/s ---");
        List<Album> albums = albumController.readAllArchivedAlbum();
        viewAllAlbum();
    }


}
