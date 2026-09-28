package com.joysis.recordingapp.controller;

import com.joysis.recordingapp.model.Playlist;
import com.joysis.recordingapp.model.Song;
import com.joysis.recordingapp.service.PlaylistService;

import java.util.List;

public class PlaylistController {
    
    private final PlaylistService playlistService;
    
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public List<Playlist> handleViewPlaylistsByUser(int userId) {
        return playlistService.getPlaylistsByUser(userId);
    }

    public boolean handleCreatePlaylist(int userId) {
        return playlistService.createPlaylist(userId);
    }

    public boolean handleDeletePlaylist(int id) {
        return playlistService.deletePlaylist(id);
    }

    public List<Song> handleViewSongsInPlaylist(int playlistId) {
        return playlistService.getSongsInPlaylist(playlistId);
    }

    public boolean handleAddSongToPlaylist(int playlistId, int songId) {
        return playlistService.addSongToPlaylist(playlistId, songId);
    }

    public boolean handleRemoveSongFromPlaylist(int playlistId, int songId) {
        return playlistService.removeSongFromPlaylist(playlistId, songId);
    }
}
