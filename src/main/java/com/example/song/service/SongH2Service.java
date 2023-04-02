
package com.example.song.service;

import com.example.song.model.Song;
import com.example.song.model.SongRowMapper;
import com.example.song.repository.SongRepository;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

// Don't modify the below code
@Service
public class SongH2Service implements SongRepository {

    @Autowired
    private JdbcTemplate db;
   
    @Override
    public ArrayList<Song> getSongs(){
        List<Song> p = db.query("SELECT * FROM PLAYLIST",new SongRowMapper());
        ArrayList<Song> songs = new ArrayList<>(p);
        return songs;
    }

    @Override
    public Song addSong(Song song){
       db.update("INSERT INTO PLAYLIST(SONGNAME,LYRICIST,SINGER,MUSICDIRECTOR) VALUES (?,?,?,?)",song.getSongName(),song.getLyricist(),song.getSinger(),song.getMusicDirector());
        Song savedPlayer = db.queryForObject("SELECT * FROM PLAYLIST WHERE SONGNAME = ? AND LYRICIST = ? AND SINGER = ? AND MUSICDIRECTOR = ?", new SongRowMapper(),song.getSongName(),song.getLyricist(),song.getSinger(),song.getMusicDirector());
        return savedPlayer;
    }

	@Override
    public Song getSong(int songId){
        try{

            Song song = db.queryForObject("SELECT * FROM PLAYLIST WHERE SONGID = ?",new SongRowMapper(),songId);
            return song;

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public Song updateSong(int songId, Song song){
        try{

            if(song.getSongName() != null){
                db.update("UPDATE PLAYLIST SET SONGNAME = ? WHERE SONGID = ?",song.getSongName(),songId);
            }
            if(song.getLyricist() != null){
                db.update("UPDATE PLAYLIST SET LYRICIST = ? WHERE SONGID = ?",song.getLyricist(),songId);
            }
            if(song.getSinger() != null){
                db.update("UPDATE PLAYLIST SET SINGER = ? WHERE SONGID = ?",song.getSinger(),songId);
            }
            if(song.getMusicDirector() != null){
                db.update("UPDATE PLAYLIST SET MUSICDIRECTOR = ? WHERE SONGID = ?",song.getMusicDirector(),songId);
            }
            return getSong(songId);

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
   
    }
    @Override
    public void deleteSong(int songId){
        db.update("DELETE FROM PLAYLIST WHERE SONGID = ?",songId);
    }
}