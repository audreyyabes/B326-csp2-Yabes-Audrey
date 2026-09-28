package com.joysis.recordingapp.service;

import com.joysis.recordingapp.model.Playlist;
import com.joysis.recordingapp.model.Song;
import com.joysis.recordingapp.repository.PlaylistRepo;

import java.util.List;

public class PlaylistServiceImpl implements PlaylistService{
    private final PlaylistRepo playlistRepo; // Composition

    // Constructor injection
    public PlaylistServiceImpl(PlaylistRepo playlistRepository) {
        this.playlistRepo = playlistRepository;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepo.getAllPlaylists();
    }

    @Override
    public List<Playlist> getPlaylistsByUser(int userId) {
        if (userId <= 0) {
            System.out.println("Invalid user ID.");
            return List.of();
        }
        return playlistRepo.getPlaylistsByUser(userId);
    }

    @Override
    public boolean createPlaylist(int userId) {
        if (userId <= 0) {
            System.out.println("A valid user ID is required.");
            return false;
        }
        return playlistRepo.createPlaylist(userId);
    }

    @Override
    public boolean deletePlaylist(int id) {
        if (id <= 0) {
            System.out.println("Invalid playlist ID.");
            return false;
        }
        return playlistRepo.deletePlaylist(id);
    }

    @Override
    public List<Song> getSongsInPlaylist(int playlistId) {
        if (playlistId <= 0) {
            System.out.println("Invalid playlist ID.");
            return List.of();
        }
        return playlistRepo.getSongsInPlaylist(playlistId);
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        if (playlistId <= 0 || songId <= 0) {
            System.out.println("A valid playlist ID and song ID are required.");
            return false;
        }
        return playlistRepo.addSongToPlaylist(playlistId, songId);
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        if (playlistId <= 0 || songId <= 0) {
            System.out.println("A valid playlist ID and song ID are required.");
            return false;
        }
        return playlistRepo.removeSongFromPlaylist(playlistId, songId);
    }
}
