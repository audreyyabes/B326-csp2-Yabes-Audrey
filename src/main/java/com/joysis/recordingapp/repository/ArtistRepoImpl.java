package com.joysis.recordingapp.repository;

import com.joysis.recordingapp.config.DbConnection;
import com.joysis.recordingapp.model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepoImpl implements ArtistRepo {
    //Database Access Logic
    private final DbConnection dbConnection;

    public ArtistRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;

    }

    @Override
    public List<Artist> getAllArtist() {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 0";

        try (Connection conn = dbConnection.connect(); //connect db
             Statement stmt = conn.createStatement(); // create statement
             ResultSet result = stmt.executeQuery(query); // execute query
        ) {
            //extract data
            while (result.next()) {
                artists.add(new Artist
                        (result.getInt("id"),
                                result.getString("name")));
            }

        } catch (SQLException e) {
            System.err.println("Get all artists " + e.getMessage());
        }
        return artists;
    }

    @Override
    public boolean createArtist(Artist artist) {
        // Anti-sql injection
        String query = "INSERT INTO artists (name) VALUES (?)"; // parameterized query

        try (Connection conn = dbConnection.connect()) {
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, artist.getName()); // set wild card values
            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Create Artist: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateArtist(Artist artist) {
        String query = "UPDATE artists SET name = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, artist.getName());
            prep.setInt(2, artist.getId());

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Update Artist Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean archivedArtist(int id) {
        String query = "UPDATE artists SET is_archived = 1  WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;


        } catch (SQLException e) {
            System.err.println("Archive Artist Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean restoreArtist(int id) {

        String query = "UPDATE artists SET is_archived = 0  WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);

            int rows = prep.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Restore Artist: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteArtist(int id) {
        // hard delete
        String query = "DELETE FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete Artist Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Artist readArtistById(int id) {
        String query = "SELECT * FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                return new Artist(
                        res.getInt("id"),
                        res.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Read Artist by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Artist> searchArtist(String keyword) {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT id, name FROM artists WHERE name LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword.trim() + "%");

            try (ResultSet res = prep.executeQuery()) {
                while (res.next()) {
                    artists.add(new Artist(
                            res.getInt("id"),
                            res.getString("name")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Search Artist: " + e.getMessage());
        }
        return artists;
    }

    @Override
    public List<Artist> readAllArchivedArtists() {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 1";

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(query)) {

            // Extract and display data rows
            while (result.next()) {
                artists.add(new Artist
                        (result.getInt("id"),
                                result.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Archived Artist: " + e.getMessage());
        }
        return artists;
    }

}
