package com.musicplayer;

import Models.*;
import Services.PlaylistDAO;
import Services.PlaylistDAOImpl;
import Services.SongDAO;
import Services.SongDAOImpl;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.File;
import java.util.List;

public class HelloController
{
    SongDAO songdao = new SongDAOImpl();
    PlaylistDAO playlistdao = new PlaylistDAOImpl();

    @FXML
    ListView songsListView = new ListView();
    private ObservableList<Song> songsOList = FXCollections.observableArrayList();

    private MediaPlayer mediaPlayer;
    private Song playingSong;

    @FXML
    private Slider timeSlider;
    private Duration duration;

    @FXML
    ListView playlistListView = new ListView();
    private ObservableList<Playlist> playlistsOList = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private TextField playlistNameTextField;


    @FXML
    private Label currentSongLabel;

    @FXML
    private Slider volumeSlider;



    // Initialize method to load all songs (you already have this method)
    public void initialize()
    {
        // initialisere nogle elementer
        currentSongLabel.setText("Sang: [Ingen sang valgt]");

        try
        {
            songsOList.addAll(songdao.getAllSongs());
            fillPlaylistsOList();
            fillSongListView();
            fillPlaylistListView();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void playSongFromListClick(int songID)
    {
        try
        {
            playingSong = null;
            //Searches for the chosen song to be played
            for (Song song : songsOList)
            {
                if (song.getSongID() == songID)
                {
                    playingSong = song;
                    break;
                }
            }

            // if the song was found it will create the media to start playing the song
            if(playingSong != null)
            {
                if (mediaPlayer != null)
                {
                    mediaPlayer.stop();
                }

                currentSongLabel.setText("Sang: " + playingSong.getSongName());
                Media media = new Media(getClass().getResource("/media/" + playingSong.getFilePath()).toURI().toString());
                mediaPlayer = new MediaPlayer(media);

                mediaPlayer.currentTimeProperty().addListener(new InvalidationListener()
                {
                    @Override
                    public void invalidated(Observable ov)
                    {
                        updateValues();
                    }
                });

                //adds listener on mediaplayer to update values
                mediaPlayer.setOnReady(new Runnable()
                {
                    @Override
                    public void run() {
                        duration = mediaPlayer.getMedia().getDuration();
                        updateValues();
                    }
                });

                HBox.setHgrow(timeSlider, Priority.ALWAYS);
                timeSlider.setMinWidth(50);
                timeSlider.setMaxWidth(Double.MAX_VALUE);
                duration = media.getDuration();

                //adds listener on time slider
                timeSlider.valueProperty().addListener(new InvalidationListener()
                {
                    @Override
                    public void invalidated(Observable ov)
                    {
                        if (timeSlider.isValueChanging())
                        {
                            mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                        }
                    }
                });


                volumeSlider.setPrefWidth(70);
                volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
                volumeSlider.setMinWidth(30);

                //adds listener on volume slider
                volumeSlider.valueProperty().addListener(new InvalidationListener()
                {
                    @Override
                    public void invalidated(Observable ov)
                    {
                        if (volumeSlider.isValueChanging())
                        {
                            mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
                        }
                    }
                });

                mediaPlayer.play();
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * pauses and starts the currently playing song
     */
    @FXML
    private void handlePlayPauseButtonAction()
    {
        if (mediaPlayer != null)
        {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
            {
                mediaPlayer.pause();
            }
            else
            {
                mediaPlayer.play();
            }
        }
    }

    // Tilføj event handlers
    @FXML
    private void handleNextButtonAction()
    {
        if (mediaPlayer != null)
        {
            int indexOfNextSong = songsOList.indexOf(playingSong) +1;
            if (indexOfNextSong > songsOList.size() - 1)
            {
                indexOfNextSong = 0;
            }
            playingSong = songsOList.get(indexOfNextSong);
            playSongFromListClick(playingSong.getSongID());
        }
    }

    @FXML
    private void handlePreviousButtonAction()
    {
        if (mediaPlayer != null)
        {
            int indexOfPreviousSong = songsOList.indexOf(playingSong)-1;
            if (indexOfPreviousSong >= 0)
            {
                playingSong = songsOList.get(indexOfPreviousSong);
            }
            playSongFromListClick(playingSong.getSongID());
        }
    }

    @FXML
    private void handleStopButtonAction()
    {
        mediaPlayer.stop();
    }

    @FXML
    private void searchClick()
    {
        try
        {
            songsOList.clear();
            songsOList.addAll(songdao.getSongsBySearch(searchField.getText()));
            fillSongListView();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates values for media player
     */
    protected void updateValues()
    {
        if (mediaPlayer != null)
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    timeSlider.setDisable(duration.isUnknown());

                    if (!timeSlider.isDisable()
                    && duration.greaterThan(Duration.ZERO)
                    && !timeSlider.isValueChanging())
                    {
                        timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                    }
                    if (!volumeSlider.isValueChanging())
                    {
                        volumeSlider.setValue((int)Math.round(mediaPlayer.getVolume() * 100));
                    }
                }
            });
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
                    //Sets event handler when click is registered to play the song
                    grid.setOnMousePressed((new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent mouseEvent)
                        {
                            playSongFromListClick(song.getSongID());
                        }
                    }));


                    ChoiceBox<Playlist> cb = new ChoiceBox<>();
                    Playlist playlistlabel = new Playlist("Playlists");
                    cb.getItems().add(playlistlabel);
                    for (Playlist playlist : playlistsOList)
                    {
                        if (playlist.getPlaylistID() != 0)
                        {
                            cb.getItems().add(playlist);
                        }
                    }
                    cb.setValue(playlistlabel);
                    // event handler for adding song to playlist
                    cb.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent actionEvent)
                        {
                            if (!cb.getValue().equals(playlistlabel))
                            {
                                addSongToPlaylist(song.getSongID(), cb.getValue().getPlaylistID());
                                fillPlaylistListView();
                            }
                            cb.setValue(playlistlabel);
                        }
                    });


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
                                songdao.deleteSong(song);
                                songsOList.remove(song);
                                fillPlaylistListView();
                            }
                            catch (Exception e)
                            {
                                System.out.println(e.getMessage());
                            }
                        }
                    });

                    grid.add(cb, 0, 0);
                    grid.add(songNameLabel, 1, 0);
                    grid.add(artistNameLabel, 2, 0);
                    grid.add(songDurationLabel, 3, 0);
                    grid.add(deleteSong, 4, 0);



                    grid.getColumnConstraints().add(new ColumnConstraints(80));
                    grid.getColumnConstraints().add(new ColumnConstraints(210));
                    grid.getColumnConstraints().add(new ColumnConstraints(230));
                    grid.getColumnConstraints().add(new ColumnConstraints(50));
                    grid.getColumnConstraints().add(new ColumnConstraints(50));

                    setGraphic(grid);
                }
            }
        });
    }

    private void addSongToPlaylist(int songID, int playlistID)
    {
        try
        {
            playlistdao.addSongToPlaylist(songID, playlistID);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void fillPlaylistListView()
    {
        playlistListView.setItems(playlistsOList);

        playlistListView.setCellFactory(lv -> new ListCell<Playlist>()
        {
            @Override
            protected void updateItem(Playlist playlist, boolean empty)
            {
                super.updateItem(playlist, empty);
                if (empty || playlist == null)
                {
                    setText(null);
                    setGraphic(null);
                }
                else
                {
                    GridPane grid = new GridPane();
                    grid.setOnMousePressed(new EventHandler<MouseEvent>()
                    {
                        @Override
                        public void handle(MouseEvent mouseEvent)
                        {
                            getPlaylistSongs(playlist.getPlaylistID());
                        }
                    });
                    if (playlist.getPlaylistID() != 0)
                    {
                        Button deleteButton = new Button("X");
                        deleteButton.setOnMousePressed(new EventHandler<MouseEvent>()
                        {
                            @Override
                            public void handle(MouseEvent mouseEvent)
                            {
                                try
                                {
                                    playlistdao.deletePlaylistById(playlist.getPlaylistID());
                                    playlistsOList.remove(playlist);
                                    fillPlaylistListView();
                                }
                                catch (Exception e)
                                {
                                    System.out.println(e.getMessage());
                                }
                            }
                        });
                        grid.add(deleteButton, 0, 0);
                    }

                    Label label = new Label(playlist.getPlaylistName());

                    String totalDuration = "";
                    try
                    {
                        List<Song> songs;
                        if (playlist.getPlaylistID() == 0)
                        {
                            songs = songdao.getAllSongs();
                        }
                        else
                        {
                            songs = songdao.getSongsByPlaylistID(playlist.getPlaylistID());
                        }

                        int totalSeconds = 0;
                        for (Song song : songs)
                        {
                            totalSeconds += song.getDuration();
                        }
                        totalDuration = (int)totalSeconds/60 + ":" + (int)totalSeconds%60;
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }

                    Label playlistDurationLabel = new Label(totalDuration);


                    grid.add(label, 1, 0);
                    grid.add(playlistDurationLabel, 2, 0);

                    grid.getColumnConstraints().add(new ColumnConstraints(30));
                    grid.getColumnConstraints().add(new ColumnConstraints(60));
                    setGraphic(grid);
                }
            }
        });
    }

    @FXML
    private void addPlaylistClick()
    {
        try
        {
            playlistdao.createPlaylist(new Playlist(playlistNameTextField.getText()));
            fillPlaylistsOList();
            fillPlaylistListView();
            fillSongListView();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void fillPlaylistsOList()
    {
        playlistsOList.clear();
        Playlist all = new Playlist("Alle sange");
        all.setPlaylistID(0);
        playlistsOList.add(all);
        playlistsOList.addAll(playlistdao.getAllPlaylists());
        playlistNameTextField.clear();
    }

    private void getPlaylistSongs(int playlistID)
    {
        try
        {
            songsOList.clear();
            if (playlistID == 0)
            {
                songsOList.addAll(songdao.getAllSongs());
            }
            else
            {
                songsOList.addAll(songdao.getSongsByPlaylistID(playlistID));
            }
            fillSongListView();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    // This method will be triggered when the user clicks the "Choose File" button
    @FXML
    protected void chooseFile()
    {
        // Create a new FileChooser instance
        FileChooser fileChooser = new FileChooser();

        // Set up the file filters for MP3 and WAV files
        FileChooser.ExtensionFilter mp3Filter = new FileChooser.ExtensionFilter("MP3 Files", "*.mp3");
        FileChooser.ExtensionFilter wavFilter = new FileChooser.ExtensionFilter("WAV Files", "*.wav");

        // Add the filters to the file chooser
        fileChooser.getExtensionFilters().add(mp3Filter);
        fileChooser.getExtensionFilters().add(wavFilter);

        // Open the file chooser and get the selected file
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // If a file is selected, do something with the file (e.g., display the file name)
        if (selectedFile != null)
        {
            // Here you can use the file for further processing, such as playing it
            //welcomeText.setText("Selected file: " + selectedFile.getName());

            // Example: you can add functionality to play the music using MediaPlayer
            // For instance, create a MediaPlayer here to play the song.
        } else
        {
            //welcomeText.setText("No file selected");
        }
    }
}
