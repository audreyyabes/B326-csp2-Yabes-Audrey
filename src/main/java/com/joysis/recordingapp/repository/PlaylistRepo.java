package com.joysis.recordingapp.repository;

import com.joysis.recordingapp.model.Playlist;
import com.joysis.recordingapp.model.Song;

import java.util.List;

public interface PlaylistRepo {
    List<Playlist> getAllPlaylists();

    List<Playlist> getPlaylistsByUser(int userId);

    boolean createPlaylist(int userId);

    boolean deletePlaylist(int id);

    List<Song> getSongsInPlaylist(int playlistId);

    boolean addSongToPlaylist(int playlistId, int songId);

    boolean removeSongFromPlaylist(int playlistId, int songId);
}
