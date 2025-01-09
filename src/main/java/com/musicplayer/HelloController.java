package com.musicplayer;

import Models.*;
import Services.SongDAO;
import Services.SongDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HelloController
{
    private ObservableList<Song> songsOList = FXCollections.observableArrayList();

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

    @FXML
    private Button playButton;

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
            songsListView.getItems().add(songsOList);


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

                        Label nameLabel = new Label("Song: " + song.getSongName());
                        Label artistLabel = new Label("Artist: " + song.getArtistName());
                        Label durationLabel = new Label("Duration: " + song.getDuration() + " sec");

                        grid.add(nameLabel, 0, 0);
                        grid.add(artistLabel, 1, 0);
                        grid.add(durationLabel, 2, 0);



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
    private void handlePlayPauseButtonAction()
    {
        System.out.println("Afspil/Pause blev trykket");

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
    private void handleVolumeButtonAction()
    {
        System.out.println("Volume blev trykket");
    }

    @FXML
    private void searchClick()
    {
        System.out.println(searchField.getText());
        searchField.setText("Simon tag et bad");
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
