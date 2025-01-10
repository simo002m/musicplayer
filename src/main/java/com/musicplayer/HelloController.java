package com.musicplayer;

import Models.*;
import Services.SongDAO;
import Services.SongDAOImpl;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

import java.io.File;

public class HelloController
{
    private ObservableList<Song> songsOList = FXCollections.observableArrayList();

    private MediaPlayer mediaPlayer;

    @FXML
    private Slider timeSlider;
    private Duration duration;

    @FXML
    ListView songsListView = new ListView();

    @FXML
    private TextField searchField;

    @FXML
    private TableView<?> playlistTable;


    @FXML
    private Label currentSongLabel;

    @FXML
    private Slider volumeSlider;



    // Initialize method to load all songs (you already have this method)
    public void initialize()
    {
        // initialisere nogle elementer
        currentSongLabel.setText("Sang: [Ingen sang valgt]");

        SongDAO songdao = new SongDAOImpl();

        try
        {
            songsOList.addAll(songdao.getAllSongs());
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

                        Button playSongButton = new Button("Play");
                        playSongButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                playSongFromListClick(song.getSongID());
                            }
                        });
                        Label songNameLabel = new Label("Song: " + song.getSongName());
                        Label artistNameLabel = new Label("Artist: " + song.getArtistName());
                        Label songDurationLabel = new Label("Duration: " + song.getDuration());

                        grid.add(playSongButton, 0, 0);
                        grid.add(songNameLabel, 1, 0);
                        grid.add(artistNameLabel, 2, 0);
                        grid.add(songDurationLabel, 3, 0);



                        grid.getColumnConstraints().add(new ColumnConstraints(40));
                        grid.getColumnConstraints().add(new ColumnConstraints(140));
                        grid.getColumnConstraints().add(new ColumnConstraints(250));
                        grid.getColumnConstraints().add(new ColumnConstraints(200));


                        setGraphic(grid);
                    }
                }
            });
        }
        catch (Exception e)
        {
            // Handle any exceptions here, if needed
            e.printStackTrace();
        }
    }

    // Tilføj event handlers
    @FXML
    private void handleNextButtonAction()
    {

        System.out.println("Næste sang blev trykket");

    }

    @FXML
    private void playSongFromListClick(int songID)
    {
        try
        {
            Song foundSong = null;
            //Searches for the chosen song to be played
            for (Song song : songsOList)
            {
                if (song.getSongID() == songID)
                {
                    foundSong = song;
                    break;
                }
            }

            // if the song was found it will create the media to start playing the song
            if(foundSong != null)
            {
                if (mediaPlayer != null)
                {
                    mediaPlayer.stop();
                }

                currentSongLabel.setText("Sang: " + foundSong.getSongName());
                Media media = new Media(getClass().getResource("/media/" + foundSong.getFilePath()).toURI().toString());
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

                volumeSlider = new Slider();
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



    @FXML
    private void handlePreviousButtonAction()
    {
        System.out.println("Forrige sang blev trykket");

    }
    @FXML
    private void handleStopButtonAction()
    {
        System.out.println("Stop blev trykket");

    }
    @FXML
    private void handleVolumeSlider()
    {
        System.out.println("Volume blev trykket");

    }

    @FXML
    private void searchClick()
    {
        System.out.println(searchField.getText());
        searchField.setText("Simon tag et bad");
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
