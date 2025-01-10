package Models;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
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

    public Song(String songName, String artistName, int duration, String filePath, String genre)
    {
        this.songName = songName;
        this.artistName = artistName;
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

    public void setDuration(int duration) throws UnsupportedAudioFileException, IOException {
        File file = new File(filePath);

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        double durationInSeconds = (frames + 0.0) / format.getFrameRate();

        double hours = durationInSeconds / 3600;
        double minutes = (durationInSeconds % 3600) / 60;
        double seconds = durationInSeconds % 60;

        String timeString = String.format("%02d:%02d:%02d", (int) hours, (int) minutes, (int) seconds);

        this.duration = timeString;
    }

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
