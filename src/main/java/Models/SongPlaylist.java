package Models;

import java.util.List;

public class SongPlaylist
{
    private int id;
    private List<Song> songs;
    private List<Playlist> playlists;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public List<Song> getSongs()
    {
        return songs;
    }

    public void setSongs(List<Song> songs)
    {
        this.songs = songs;
    }

    public List<Playlist> getPlaylists()
    {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists)
    {
        this.playlists = playlists;
    }

    public SongPlaylist(int id, List<Song> songs, List<Playlist> playlists) {
        this.id = id;
        this.songs = songs;
        this.playlists = playlists;
    }
}
