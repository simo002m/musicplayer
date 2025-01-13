package Models;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Song
{
    private int songID;
    private String songName;
    private String artistName;
    private String duration;
    private String filePath;
    private String genre;

    public Song()
    {
    }

    public Song(String songName, String artistName, String duration, String filePath, String genre)
    {
        this.songName = songName;
        this.artistName = artistName;
        this.duration = duration;
        this.filePath = filePath;
        this.genre = genre;
    }

    public int getSongID()
    {
        return songID;
    }

    public void setSongID(int songID)
    {
        this.songID = songID;
    }

    public String getSongName()
    {
        return songName;
    }

    public void setSongName(String songName)
    {
        this.songName = songName;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration) { this.duration = duration; }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

}
