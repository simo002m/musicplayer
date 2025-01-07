package Models;

public class Playlist
{
    private int playlistID;
    private String playlistName;

    public int getPlaylistID()
    {
        return playlistID;
    }

    public String getPlaylistName()
    {
        return playlistName;
    }

    public void setPlaylistName(String playlistName)
    {
        this.playlistName = playlistName;
    }

    public Playlist(String playlistName)
    {
        this.playlistName = playlistName;
    }

    public Playlist(int playlistID, String playlistName)
    {
        this.playlistID = playlistID;
        this.playlistName = playlistName;
    }
}
