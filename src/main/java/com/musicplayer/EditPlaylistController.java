package com.musicplayer;

import Models.Playlist;
import Models.Song;
import Services.PlaylistDAO;
import Services.PlaylistDAOImpl;
import Services.SongDAO;
import Services.SongDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditPlaylistController
{
    @FXML
    ListView songsListView;
    private ObservableList<Song> songsOList = FXCollections.observableArrayList();

    @FXML
    private Label messegelabel;

    PlaylistDAO playlistDAO;
    SongDAO songdao;
    Playlist playlist;

    @FXML
    private TextField name;

    public void editPlaylistID(int id)
    {
        System.out.println("Hello method");
        this.playlistDAO = new PlaylistDAOImpl();
        this.songdao = new SongDAOImpl();
        this.playlist = this.playlistDAO.getPlaylistById(id);
        try
        {
            this.songsOList.addAll(this.songdao.getSongsByPlaylistID(this.playlist.getPlaylistID()));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println(this.playlist.getPlaylistID());
        this.name.setText(playlist.getPlaylistName());
        fillSongListView();
    }

    @FXML
    private void backToPlaylist(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleSaveButtonAction()
    {

        try
        {

            String newName = name.getText();
            if (newName == null || newName.isEmpty())
            {
                messegelabel.setText("Please enter a valid name");
            }
            else
            {
                playlist.setPlaylistName(name.getText());
                playlistDAO.updatePlaylist(playlist);
                messegelabel.setText("");
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void fillSongListView()
    {
        songsListView.setItems(songsOList);

        songsListView.setCellFactory(lv -> new ListCell<Song>()
        {
            @Override
            protected void updateItem(Song song, boolean empty)
            {
                super.updateItem(song, empty);
                if (empty || song == null)
                {
                    setText(null);
                    setGraphic(null);
                }
                else
                {
                    GridPane grid = new GridPane();

                    Label songNameLabel = new Label(song.getSongName());
                    Label artistNameLabel = new Label(song.getArtistName());
                    String duration = (int)song.getDuration()/60 + ":" + (int)song.getDuration()%60;
                    Label songDurationLabel = new Label(duration);

                    Button deleteSong = new Button("X");
                    deleteSong.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent actionEvent)
                        {
                            try
                            {
                                playlistDAO.deleteSongFromPlaylist(song.getSongID(), playlist.getPlaylistID());
                                songsOList.remove(song);
                            }
                            catch (Exception e)
                            {
                                System.out.println(e.getMessage());
                            }
                        }
                    });

                    grid.add(songNameLabel, 0, 0);
                    grid.add(artistNameLabel, 1, 0);
                    grid.add(songDurationLabel, 2, 0);
                    grid.add(deleteSong, 3, 0);




                    grid.getColumnConstraints().add(new ColumnConstraints(210));
                    grid.getColumnConstraints().add(new ColumnConstraints(230));
                    grid.getColumnConstraints().add(new ColumnConstraints(50));
                    grid.getColumnConstraints().add(new ColumnConstraints(50));

                    setGraphic(grid);
                }
            }
        });
    }
}
