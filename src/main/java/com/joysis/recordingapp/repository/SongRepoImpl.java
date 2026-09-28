package com.joysis.recordingapp.repository;

import com.joysis.recordingapp.config.DbConnection;
import com.joysis.recordingapp.model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongRepoImpl implements SongRepo {

    private final DbConnection dbConnection;

    public SongRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Song> getAllSong() {

        List<Song> songs = new ArrayList<>();

        String query = """
            SELECT songs.id,
                   songs.title,
                   songs.length,
                   songs.genre,
                   songs.album_id,
                   albums.name AS album_name
            FROM songs
            JOIN albums ON songs.album_id = albums.id
            """;

        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet result = stmt.executeQuery(query)) {

            while (result.next()) {

                songs.add(new Song(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getInt("length"),
                        result.getString("genre"),
                        result.getInt("album_id"),
                        result.getString("album_name")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Get all songs: " + e.getMessage());
        }

        return songs;
    }

    @Override
    public List<Song> searchSong(String keyword) {

        List<Song> songs = new ArrayList<>();

        String query = """
            SELECT songs.id,
                   songs.title,
                   songs.length,
                   songs.genre,
                   songs.album_id,
                   albums.name AS album_name
            FROM songs
            JOIN albums ON songs.album_id = albums.id
            WHERE songs.title LIKE ?
            """;

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet result = prep.executeQuery()) {

                while (result.next()) {

                    songs.add(new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getInt("length"),
                            result.getString("genre"),
                            result.getInt("album_id"),
                            result.getString("album_name")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Search song: " + e.getMessage());
        }

        return songs;
    }

    @Override
    public Song readSongById(int id) {

        String query = """
            SELECT songs.id,
                   songs.title,
                   songs.length,
                   songs.genre,
                   songs.album_id,
                   albums.name AS album_name
            FROM songs
            JOIN albums ON songs.album_id = albums.id
            WHERE songs.id = ?
            """;

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {

                if (result.next()) {

                    return new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getInt("length"),
                            result.getString("genre"),
                            result.getInt("album_id"),
                            result.getString("album_name")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Get song by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean createSong(Song song) {

        String query = """
            INSERT INTO songs (title, length, genre, album_id)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getTitle());
            prep.setInt(2, song.getLength());
            prep.setString(3, song.getGenre());
            prep.setInt(4, song.getAlbum_id());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Create song: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateSong(Song song) {

        String query = """
            UPDATE songs
            SET title = ?
            WHERE id = ?
            """;

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, song.getTitle());
            prep.setInt(2, song.getId());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Update song: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteSong(int id) {

        String query = "DELETE FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Delete song: " + e.getMessage());
        }

        return false;
    }
}