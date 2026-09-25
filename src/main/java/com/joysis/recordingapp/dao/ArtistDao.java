package com.joysis.recordingapp.dao;

import com.joysis.recordingapp.config.DbConnection;

import java.sql.*;

public class ArtistDao extends DbConnection {

    private final DbConnection dbConnection;

    public ArtistDao( DbConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public void readAllArtist(){
        String query = "SELECT * FROM artists";

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



}
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