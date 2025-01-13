package Services;

import Models.Playlist;
import util.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAOImpl implements PlaylistDAO
{
    @Override
    public List<Playlist> getAllPlaylists()
    {
        Connection con = null;
        List<Playlist> playlists = new ArrayList<Playlist>();
        try
        {
            String sql = "SELECT * FROM playlists";
            con = SqlConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                playlists.add(setPlaylistProperties(rs, new Playlist()));
            }
        }
        catch(Exception e)
        {
            System.out.println("Failed to get all playlists with exception");
        }
        return playlists;
    }

    @Override
    public Playlist getPlaylistById(int id)
    {
        Connection con = null;
        try
        {
            String sql = "SELECT * FROM playlists WHERE playlistID = ?";
            con = SqlConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return setPlaylistProperties(rs, new Playlist());
            }
        }
        catch(Exception e)
        {
            System.out.println("Failed to get playlist with exception");
        }
        return null;
    }

    @Override
    public void addSongToPlaylist(int playlistID, int songID)
    {
        Connection con = null;

        try
        {
            String sql = "INSERT INTO songsPlaylist VALUES(?,?)";
            con = SqlConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(2, songID);
            ps.setInt(1, playlistID);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Song added to playlist");
            }
            else
            {
                System.out.println("Failed to add song to playlist");
            }
        }
        catch (Exception e)
        {
            System.out.println("Failed to add song to playlist with exception");
        }
    }

    @Override
    public void deletePlaylistById(int id)
    {
        Connection con = null;
        try
        {
            String sql = "DELETE FROM playlists WHERE playlistID = ?";
            con = SqlConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0)
            {
                System.out.println("Playlist deleted successfully");
            }
            else
            {
                System.out.println("Playlist deletion failed");
            }
        }
        catch(Exception e)
        {
            System.out.println("Failed to delete playlist with exception");
        }
    }

    @Override
    public void updatePlaylist(Playlist playlist)
    {
        Connection con = null;
        try
        {
            String sql = "UPDATE playlists SET playlistName = ? WHERE playlistID = ?";
            con = SqlConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, playlist.getPlaylistName());
            ps.setInt(2, playlist.getPlaylistID());
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0)
            {
                System.out.println("Playlist updated successfully");
            }
            else
            {
                System.out.println("Playlist update failed");
            }
        }
        catch(Exception e)
        {
            System.out.println("Failed to update playlist with exception");
        }
    }

    @Override
    public void createPlaylist(Playlist playlist)
    {
        Connection con = null;
        try
        {
            String sql = "INSERT INTO playlists VALUES (?)";
            con = SqlConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, playlist.getPlaylistName());
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0)
            {
                System.out.println("Playlist created successfully");
            }
            else
            {
                System.out.println("Playlist creation failed");
            }
        }
        catch(Exception e)
        {
            System.out.println("Failed to create playlist with exception");
        }
    }

    private Playlist setPlaylistProperties(ResultSet rs, Playlist playlist) throws Exception
    {
        playlist.setPlaylistID(rs.getInt("playlistID"));
        playlist.setPlaylistName(rs.getString("playlistName"));
        return playlist;
    }
}
