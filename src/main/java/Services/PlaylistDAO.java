package Services;

import Models.Playlist;

import java.util.List;

public interface PlaylistDAO
{
    List<Playlist> getAllPlaylists();
    Playlist getPlaylistById(int id);
    void addSongToPlaylist( int songID, int playlistID);
    void deletePlaylistById(int id);
    void updatePlaylist(Playlist playlist);
    void createPlaylist(Playlist playlist);
}
