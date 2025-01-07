package Services;

import Models.Song;
import util.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SongDAOImpl implements SongDAO
{
    @Override
    public List<Song> getAllSongs() throws Exception
    {
        Connection con = null;
        List<Song> songs = new ArrayList<Song>();

        try
        {
            String sql = "SELECT * FROM Songs";
            con = SqlConnection.getConnection();

            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                Song song = new Song();
                song.setSongID(rs.getInt("songID"));
                song.setSongName(rs.getString("songName"));
                song.setArtistName(rs.getString("artistName"));
                song.setDuration(rs.getInt("duration"));
                song.setFilePath(rs.getString("filePath"));
                song.setGenre(rs.getString("genre"));
                songs.add(song);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return songs;
    }

    @Override
    public List<Song> getSongsBySearch(String searchText) throws Exception
    {
        return List.of();
    }

    @Override
    public List<Song> getSongsByPlaylistID(int playlistID) throws Exception
    {
        return List.of();
    }

    @Override
    public void addSong(Song song) throws Exception
    {

    }

    @Override
    public void updateSong(Song song) throws Exception
    {

    }

    @Override
    public void deleteSong(Song song) throws Exception
    {

    }
}
