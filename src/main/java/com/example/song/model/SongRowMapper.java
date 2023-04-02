
package com.example.song.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongRowMapper implements RowMapper<Song> {

    public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Song(
                rs.getInt("SONGID"),
                rs.getString("SONGNAME"),
                rs.getString("LYRICIST"),
                rs.getString("SINGER"),
                rs.getString("MUSICDIRECTOR")
        );
    }
}