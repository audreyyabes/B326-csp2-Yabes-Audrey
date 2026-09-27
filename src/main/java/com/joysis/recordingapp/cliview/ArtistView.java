package com.joysis.recordingapp.cliview;

import com.joysis.recordingapp.controller.ArtistController;
import com.joysis.recordingapp.model.Artist;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Scanner;

public class ArtistView {

    private final ArtistController artistController;
    private final Scanner scanner;

    public ArtistView(ArtistController artistController, Scanner scanner) {
        this.artistController = artistController;
        this.scanner = scanner;
    }

    public void run() {
        int choice;
        do {
            printMenu();
            choice = promptChoice();

            switch (choice) {
                case 1 -> viewAllArtist();
                case 2 -> searchArtist();
                case 3 -> addArtist();
                case 4 -> updateArtist();
                case 5 -> archivedArtist();
                case 6 -> restoreArtist();
                case 7 -> deleteArtist();
                case 8 -> viewAllArchivedArtist();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.println("\nPress enter to continue...");
                scanner.nextLine();
            }
        } while(choice != 0);

    }

    private void printMenu() {
        System.out.println("Artist Management\n-----");
        System.out.println("1. View All Artists"); //done
        System.out.println("2. Search Artist"); //done
        System.out.println("3. Add Artist"); //done
        System.out.println("4. Update Artist"); //done
        System.out.println("5. Archive Artist"); //done
        System.out.println("6. Restore Artist"); //done
        System.out.println("7. Delete Artist"); //done
        System.out.println("8. View All Archived Artists"); //done
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

    private void viewAllArtist() {
        System.out.println("--- View all Artist/s ---");
        List<Artist> artists = artistController.handleViewAllArtist();
        printArtists(artists);
    }

    private void searchArtist() {
        System.out.println("--- Search Artist/s ---");
        System.out.println("Enter name: ");
        String keyword = scanner.nextLine();
        List<Artist> artists = artistController.handleSearchArtist(keyword);
        printArtists(artists);

    }

    private void addArtist() {
        System.out.println("--- Add Artist ---");
        System.out.println("Enter Artist name or band: ");
        String name = scanner.nextLine();

        Artist artist = new Artist(name);
        boolean isSuccess = artistController.handleCreateArtist(artist);
        System.out.println(isSuccess ? "Artist added successfully." : "Failed to add artist");

        if (isSuccess) {
            System.out.println();
            viewAllArtist();
        }

    }

    public void updateArtist() {
        System.out.println("--- Update Artist ---");
        viewAllArtist();
        System.out.println("Artist ID to update: ");
        int id = readInt();

        Artist current = artistController.handleReadArtistById(id);

        if (current == null) {
            System.out.println("No artist found in ID " + id + ". Please check the ID and try again.");
            return;
        }
        System.out.println("New Name [" + current.getName() + "] (press Enter to keep the current.): ");

        String name = scanner.nextLine();
        if (name.trim().isEmpty()){
            name = current.getName();
        }

        Artist artist = new Artist(id, name);

        boolean isSuccess = artistController.handleUpdateArtist(artist);
        System.out.println(isSuccess ? "Artist updated successfully." : "Failed to update artist");

        if (isSuccess) {
            System.out.println();
            viewAllArtist(); //read-after-write || refresh after mutation
        }

    }

    public void archivedArtist(){
        System.out.println("--- Archived Artist ---");
        System.out.println("Enter Artist ID: ");
        int id = scanner.nextInt();

        boolean isSuccess = artistController.handleArchivedArtist(id);

        System.out.println(isSuccess ? "Artist successfully Archived." : "Failed to archive artist");

        if (isSuccess) {
            System.out.println();
            viewAllArchivedArtist();
        }
    }

    private void viewAllArchivedArtist() {
        System.out.println("--- View all Archived Artist/s ---");
        List<Artist> artists = artistController.handleReadAllArchivedArtists();
        printArtists(artists);
    }

    private void deleteArtist() {
        viewAllArtist();
        System.out.println("--- Delete Artist ---");
        System.out.println("Select Artist Name to restore: ");
        int id = scanner.nextInt();

        boolean isSuccess = artistController.handleDeleteArtist(id);

        System.out.println(isSuccess ? "Artist successfully deleted." : "Failed to deleted artist");

        if (isSuccess) {
            System.out.println();
            viewAllArchivedArtist();
        }
    }

    private void restoreArtist() {
        viewAllArchivedArtist();
        System.out.println("--- Restore Artist ---");
        System.out.println("Select Artist Name to restore: ");
        int id = scanner.nextInt();

        boolean isSuccess = artistController.handleRestoreArtist(id);

        System.out.println(isSuccess ? "Artist successfully restore." : "Failed to restore artist");

        if (isSuccess) {
            System.out.println();
            viewAllArchivedArtist();
        }
    }

    private void printArtists(List<Artist> artists) {
        if (artists.isEmpty()) {
            System.out.println("No artists found");
            return;
        }
        String border = "+" + "-".repeat(6) + "+" + "-".repeat(27) + "+";
        System.out.println(border);
        System.out.printf("| %-4s | %-25s |%n", "ID", "Name");
        System.out.println(border);

        for (Artist artist : artists) {
            System.out.printf("| %-4s | %-25s |%n", artist.getId(), artist.getName());
        }
        System.out.println(border);

    }

}
