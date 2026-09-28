package com.joysis.recordingapp.repository;

import com.joysis.recordingapp.config.DbConnection;
import com.joysis.recordingapp.model.Album;
import com.joysis.recordingapp.model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepoImpl implements AlbumRepo {

    private final DbConnection dbConnection;

    public AlbumRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Album> getAllAlbum() {
        List<Album> albums = new ArrayList<>();

        String query = """
        SELECT albums.id,
               albums.name,
               albums.year,
               albums.artist_id,
               artists.name AS artist_name
        FROM albums
        JOIN artists ON albums.artist_id = artists.id
        WHERE albums.is_archived = 0
        """;

        try (Connection connection = dbConnection.connect();
             Statement stmt = connection.createStatement();
             ResultSet result = stmt.executeQuery(query)) {

            while (result.next()) {
                albums.add(new Album(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("year"),
                        result.getInt("artist_id"),
                        result.getString("artist_name")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Get all albums: " + e.getMessage());
        }

        return albums;
    }

    @Override
    public List<Album> searchAlbum(String keyword) {
        List<Album> albums = new ArrayList<>();
        String query = """
        SELECT albums.id,
               albums.name,
               albums.year,
               albums.artist_id,
               artists.name AS artist_name
        FROM albums
        JOIN artists ON albums.artist_id = artists.id
        WHERE albums.name LIKE ?
          AND albums.is_archived = 0
        """;

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword.trim() + "%");

            try (ResultSet res = prep.executeQuery()) {
                while (res.next()) {
                    albums.add(new Album(
                            res.getInt("id"),
                            res.getString("name"),
                            res.getInt("year"),
                            res.getInt("artist_id"),
                            res.getString("artist_name")
                    ));
                }

            }
        } catch (SQLException e) {
            System.err.println("Search album title error ... " + e.getMessage());
        }
        return albums;
    }

    @Override
    public Album readAlbumById(int id) {
        String query = """
        SELECT albums.id,
               albums.name,
               albums.year,
               albums.artist_id,
               artists.name AS artist_name
        FROM albums
        JOIN artists ON albums.artist_id = artists.id
        WHERE albums.id = ?
        """;

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            ResultSet res = prep.executeQuery();

            if (res.next()) {
                return new Album(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getInt("year"),
                        res.getInt("artist_id"),
                        res.getString("artist_name"));
            }

        } catch (SQLException e) {
            System.out.println("Get all Album by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean createAlbum(Album album) {
        String query = """
        INSERT INTO albums (name, year, artist_id)
        VALUES (?, ?, ?)
        """;

        try (Connection conn = dbConnection.connect();
                PreparedStatement prep = conn.prepareStatement(query)) {
            ;

            prep.setString(1, album.getName());
            prep.setInt(2, album.getYear());
            prep.setInt(3, album.getArtist_id());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Create Album Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateAlbum(Album album) {
        String query = """
        UPDATE albums
        SET name = ?
        WHERE id = ?
        """;

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, album.getName());
            prep.setInt(2, album.getId());

            int rowsAffected = prep.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Update Album Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteAlbum(int id) {
        String query = "DELETE FROM albums WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete Album Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean archivedAlbum(int id) {
        String query = "UPDATE albums SET is_archived = 1  WHERE id = ?";
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
    public boolean restoreAlbum(int id) {
        String query = "UPDATE albums SET is_archived = 0  WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);

            int rows = prep.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Restore Album: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Album> readAllArchivedAlbum() {
        List<Album> albums = new ArrayList<>();
        String query = "SELECT * FROM albums WHERE is_archived = 1";

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(query)) {

            // Extract and display data rows
            while (result.next()) {
                albums.add(new Album
                        (result.getInt("id"),
                                result.getString("name")

                        ));
            }
        } catch (SQLException e) {
            System.out.println("Archived Artist: " + e.getMessage());
        }
        return albums;
    }
}

