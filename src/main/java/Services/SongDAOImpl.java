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

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                songs.add(setSongProperties(rs, new Song()));
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
        Connection con = null;
        List<Song> songs = new ArrayList<Song>();

        try
        {
            String sql = "SELECT * FROM Songs WHERE songName LIKE ? OR artistName LIKE ?";
            con = SqlConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                songs.add(setSongProperties(rs, new Song()));
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return songs;
    }

    @Override
    public List<Song> getSongsByPlaylistID(int playlistID) throws Exception
    {
        Connection con = null;
        List<Song> songs = new ArrayList<Song>();

        try
        {
            String sql = "SELECT * FROM Songs " +
                    "INNER JOIN SongsPlaylists ON Song.songID = SongsPlaylists.songID " +
                    "INNER JOIN Playlists ON Playlists.playlistID = SongsPlaylists.playlistID " +
                    "WHERE Playlists.playlistID = ?";
            con = SqlConnection.getConnection();

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, playlistID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                Song song = new Song();

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
    public void addSong(Song song) throws Exception
    {
        Connection con = null;

        try
        {
            String sql = "INSERT INTO Songs (songName, artistName, duration, filePath, genre) " +
                    "VALUES (?, ?, ?, ?,?)";
            con = SqlConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, song.getSongName());
            ps.setString(2, song.getArtistName());
            ps.setInt(3, song.getDuration());
            ps.setString(4, song.getFilePath());
            ps.setString(5, song.getGenre());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Song Added successfully");
            }
            else
            {
                System.out.println("Add song failed");
            }
        }
        catch(Exception e)
        {
            System.out.println("Add song failed with exception");
        }
    }

    @Override
    public void updateSong(Song song) throws Exception
    {
        Connection con = null;

        try
        {
            String sql = "UPDATE Songs SET songName = ?, artistName = ?, duration, = ?, filePath = ?, genre = ? WHERE songID = ?)";
            con = SqlConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, song.getSongName());
            ps.setString(2, song.getArtistName());
            ps.setInt(3, song.getDuration());
            ps.setString(4, song.getFilePath());
            ps.setString(5, song.getGenre());
            ps.setInt(6, song.getSongID());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Song updated successfully");
            }
            else
            {
                System.out.println("Failed to update song");
            }
        }
        catch(Exception e)
        {
            System.out.println("Failed to update song with exception");
        }
    }

    @Override
    public void deleteSong(Song song) throws Exception
    {
        Connection con = null;
        try
        {
            String sql = "DELETE FROM Songs WHERE songID = ?";
            con = SqlConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, song.getSongID());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Song deleted successfully");
            }
            else
            {
                System.out.println("Song deleted failed");
            }
        }
        catch(Exception e)
        {
            System.out.println("Song deleted failed with exception");
        }
    }

    private Song setSongProperties(ResultSet rs, Song song) throws Exception
    {
        song.setSongID(rs.getInt("songID"));
        song.setSongName(rs.getString("songName"));
        song.setArtistName(rs.getString("artistName"));
        song.setDuration(rs.getInt("duration"));
        song.setFilePath(rs.getString("filePath"));
        song.setGenre(rs.getString("genre"));
        return song;
    }
}
