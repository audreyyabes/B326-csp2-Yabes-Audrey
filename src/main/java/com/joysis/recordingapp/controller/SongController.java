package com.joysis.recordingapp.controller;

import com.joysis.recordingapp.model.Song;
import com.joysis.recordingapp.service.SongService;

import java.util.List;

public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    public List<Song> handleGetAllSong() {
        return songService.getAllSong();
    }

    public List<Song> handleSearchSong(String keyword) {
        return songService.searchSong(keyword);
    }

    public Song handleReadSongById(int id) {
        return songService.readSongById(id);
    }

    public boolean handleCreateSong(Song song) {
        return songService.createSong(song);
    }

    public boolean handleUpdateSong(Song song) {
        return songService.updateSong(song);
    }

    public boolean handleDeleteSong(int id) {
        return songService.deleteSong(id);
    }
}
