package Models;

import java.util.List;

public class Playlist
{
    private int playlistID;
    private String playlistName;
    private List<Song> songs;

    public int getPlaylistID()
    {
        return playlistID;
    }

    public void setPlaylistID(int playlistID)
    {
        this.playlistID = playlistID;
    }

    public String getPlaylistName()
    {
        return playlistName;
    }

    public void setPlaylistName(String playlistName)
    {
        this.playlistName = playlistName;
    }

    public List<Song> getSongs()
    {
        return songs;
    }

    public void setSongs(List<Song> songs)
    {
        this.songs = songs;
    }

    public Playlist(String playlistName)
    {
        this.playlistName = playlistName;
    }

    public Playlist()
    {
    }
}
