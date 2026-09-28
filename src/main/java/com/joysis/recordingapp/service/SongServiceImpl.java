package com.joysis.recordingapp.service;

import com.joysis.recordingapp.model.Song;
import com.joysis.recordingapp.repository.SongRepo;

import java.util.List;

public class SongServiceImpl implements SongService {

    private final SongRepo songRepo;

    public SongServiceImpl(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    @Override
    public List<Song> getAllSong() {
        return songRepo.getAllSong();
    }

    @Override
    public List<Song> searchSong(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search Song Keyword cannot be empty.");
            return List.of();
        }

        return songRepo.searchSong(keyword.trim());
    }

    @Override
    public Song readSongById(int id) {

        if (id <= 0) {
            System.out.println("Invalid Song ID.");
            return null;
        }

        Song song = songRepo.readSongById(id);

        if (song == null) {
            System.out.println("Song not found.");
        }

        return song;
    }

    @Override
    public boolean createSong(Song song) {

        if (song == null) {
            System.out.println("Song object cannot be null.");
            return false;
        }

        if (song.getTitle() == null ||
                song.getTitle().trim().isEmpty()) {

            System.out.println("Song title is required.");
            return false;
        }

        if (song.getLength() < 0) {
            System.out.println("Song length cannot be negative.");
            return false;
        }

        if (song.getAlbum_id() <= 0) {
            System.out.println("Invalid Album ID.");
            return false;
        }

        song.setTitle(song.getTitle().trim());

        return songRepo.createSong(song);
    }

    @Override
    public boolean updateSong(Song song) {

        if (song == null) {
            System.out.println("Song object cannot be null.");
            return false;
        }

        if (song.getId() <= 0) {
            System.out.println("Invalid Song ID.");
            return false;
        }

        if (song.getTitle() == null ||
                song.getTitle().trim().isEmpty()) {

            System.out.println("Song title is required.");
            return false;
        }

        song.setTitle(song.getTitle().trim());

        return songRepo.updateSong(song);
    }

    @Override
    public boolean deleteSong(int id) {

        if (id <= 0) {
            System.out.println("Invalid Song ID.");
            return false;
        }

        return songRepo.deleteSong(id);
    }
}
