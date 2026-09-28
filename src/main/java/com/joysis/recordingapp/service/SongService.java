package com.joysis.recordingapp.service;

import com.joysis.recordingapp.model.Song;

import java.util.List;

public interface SongService {
    List<Song> getAllSong();
    List<Song> searchSong(String keyword);
    Song readSongById(int id);
    boolean createSong(Song song);
    boolean updateSong(Song song);
    boolean deleteSong(int id);
}
