package com.joysis.recordingapp.dao;

import com.joysis.recordingapp.config.DbConnection;

import java.sql.*;

/*
    SQL Categories
    DDL
    DML -> Data Manipulation Language
        (Insert, Update, Delete) : prepareStatement() -> executeUpdate()
    DQL -> Data Query Language
        (Select) : createStatement() -> executeQuery()
 */

/*
 * DATA ACCESS OBJECT (DAO) PATTERN EXPLANATION
 * --------------------------------------------
 *
 * WHAT IS A DAO?
 * DAO stands for Data Access Object. It is a fundamental design pattern in
 * Java used to separate low-level data accessing operations (SQL queries)
 * from high-level business logic.
 *
 * PURPOSE OF A DAO CLASS:
 * 1. Abstraction & Decoupling:
 *    It acts as a "middleman" between your Java Application and the Database.
 *    The rest of your code calls simple Java methods (e.g., songDao.addSong(song))
 *    without needing to know the underlying SQL queries or database details.
 *
 * 2. Centralized Database Operations (CRUD):
 *    All Create, Read, Update, and Delete operations for a specific entity
 *    (like a 'Song' or 'User') are organized in one dedicated class instead
 *    of being scattered throughout the project.
 *
 * 3. Maintainability & Flexibility:
 *    If the database structure, SQL queries, or database type (e.g., switching
 *    from MySQL to PostgreSQL) change in the future, you only need to modify
 *    the DAO class rather than touching the entire application codebase.
 */

// inheritance: is-a relationship (tightly coupled)
// composition: has-a relationship (loosely coupled)

public class ArtistDao extends DbConnection {

    private final DbConnection dbConnection;

    public ArtistDao( DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public void readAllArtist(){
        String query = "SELECT * FROM artists WHERE is_archived = 0";

        try (Connection conn = dbConnection.connect(); //connect db
                Statement stmt = conn.createStatement(); // create statement
                ResultSet result = stmt.executeQuery(query); // execute query
                ) {


            System.out.printf("%-5s | %-20s%n", "ID", "Name");
            System.out.println("-".repeat(26));
            //extract data
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                System.out.printf("%-5d | %-20s%n", id, name);
            }

        } catch (SQLException e) {
            System.err.println("Get all artists "+ e.getMessage());
        }
    }

    public  void createArtist(String name){
        // Anti-sql injection
        String query = "INSERT INTO artists (name) VALUES (?)"; // parameterized query

        try (Connection conn = dbConnection.connect()){
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, name); // set wild card values
            int rows = prep.executeUpdate();

            // synchronization
            System.out.println( rows > 0 ? "" : "Failed to add successfully");
            readAllArtist();

        }catch (SQLException e) {
            System.err.println("Create Artist: " + e.getMessage());
        }
    }

    public void updateArtist(String name, int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID.");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Artist name is required");
            return;
        }

        String query = "UPDATE artists SET name = ? WHERE id = ?";

        try {
            Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setString(1, name);
            prep.setInt(2, id);

            int rows = prep.executeUpdate();

            System.out.println(rows > 0 ? "Artist " + name + " updated successfully" : "Failed to update artist");

            System.out.println();
            readAllArtist();
        } catch (SQLException e) {
            System.out.println("Update Artist: " + e.getMessage());
        }
    }

    public void archivedArtist (String name, int id){
        // soft delete
        // hard delete
        if (id <= 0) {
            System.out.println("Invalid artist ID.");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Artist name is required");
            return;
        }

        String query = "UPDATE artists SET is_archived = 1  WHERE id = ?";

        try {
            Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            System.out.println(rows > 0 ? "Artist " + name + " Archived successfully" : "Failed to update artist");

            System.out.println();
            readAllArtist();
        } catch (SQLException e) {
            System.out.println("Archived Artist: " + e.getMessage());
        }
    }

    public void restoreArtist (String name, int id){
        // soft delete
        // hard delete
        if (id <= 0) {
            System.out.println("Invalid artist ID.");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Artist name is required");
            return;
        }

        String query = "UPDATE artists SET is_archived = 0  WHERE id = ?";

        try {
            Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            System.out.println(rows > 0 ? "Artist" + name + " Restore successfully" : "Failed to update artist");

            System.out.println();
            readAllArtist();
        } catch (SQLException e) {
            System.out.println("Restore Artist: " + e.getMessage());
        }
    }

    public void deleteArtist (int id){
        // hard delete

        String query = "DELETE FROM artists WHERE id = ?";

        try {
            Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query);

            prep.setInt(1, id);

            int rows = prep.executeUpdate();

            System.out.println(rows > 0 ? "Artist delete successfully" : "Failed to delete artist");

            System.out.println();
            readAllArtist();
        } catch (SQLException e) {
            System.out.println("Delete Artist: " + e.getMessage());
        }
    }

    public void readArtistById (int id){

        String query = "SELECT * FROM artists WHERE id = ?";

        try(Connection conn = dbConnection.connect();
            PreparedStatement prep = conn.prepareStatement(query)) {


            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            System.out.println("+-------+----------------------+");
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            System.out.println("+-------+----------------------+");

            if (res.next()) {
                System.out.printf("| %-5d | %-20s |%n", res.getInt("id"), res.getString("name"));
            }

            System.out.println("+-------+----------------------+");

        } catch (SQLException e) {
            System.out.println("Delete Artist: " + e.getMessage());
        }

    }

    public void searchArtist (String keyword){
        // Validation
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        String query = "SELECT id, name FROM artists WHERE name LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword.trim() + "%");
            ResultSet res = prep.executeQuery();

            // UI ./ View
            System.out.println("+-------+----------------------+");
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            System.out.println("+-------+----------------------+");

            while (res.next()) {
                System.out.printf("| %-5d | %-20s |%n", res.getInt("id"), res.getString("name"));
            }

            System.out.println("+-------+----------------------+");

        } catch (SQLException e) {
            System.out.println("Search Artist: " + e.getMessage());
        }
    }

    public void readAllArchivedArtists() {
        String query = "SELECT * FROM artists WHERE is_archived = 1";

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(query)) {

            // Top Border
            System.out.println("+-------+----------------------+");
            // Header
            System.out.printf("| %-5s | %-20s |%n", "ID", "Name");
            // Header Divider
            System.out.println("+-------+----------------------+");

            // Extract and display data rows
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");

                System.out.printf("| %-5d | %-20s |%n", id, name);
            }

            // Bottom Border
            System.out.println("+-------+----------------------+");
        } catch (SQLException e) {
            System.out.println("Archived Artist: " + e.getMessage());
        }

    }

}
