package Services;

import Models.Song;

import java.util.List;

public interface SongDAO
{
    List<Song> getAllSongs() throws Exception;
    List<Song> getSongsBySearch(String searchText) throws Exception;
    List<Song> getSongsByPlaylistID(int playlistID) throws Exception;
    void addSong(Song song) throws Exception;
    void updateSong(Song song) throws Exception;
    void deleteSong(Song song) throws Exception;
}
