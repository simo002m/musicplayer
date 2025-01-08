package com.musicplayer;

import Services.SongDAO;
import Services.SongDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {


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

    // Initialiser controlleren
    @FXML
    public void initialize() {
        // initialisere nogle elementer
        currentSongLabel.setText("Sang: [Ingen sang valgt]");
    }

    // Tilføj event handlers
    @FXML
    private void handleNextButtonAction() {
        System.out.println("Næste sang blev trykket");

    }

    @FXML
    private void handlePlayPauseButtonAction() {
        System.out.println("Afspil/Pause blev trykket");

    }

    @FXML
    private void handlePreviousButtonAction() {
        System.out.println("Forrige sang blev trykket");

    }
    @FXML
    private void handleStopButtonAction() {
        System.out.println("Stop blev trykket");

    }
    @FXML
    private void handleVolumeButtonAction() {
        System.out.println("Volume blev trykket");
    }
   @FXML
    private void searchClick() {
    System.out.println(searchField.getText());
    searchField.setText("Simon tag et bad");

    }
}
