package com.musicplayer;

import Services.SongDAO;
import Services.SongDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class HelloController {

    @FXML
    private Label welcomeText;

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
            songdao.getAllSongs();
        }
        catch (Exception e)
        {
            // Handle any exceptions here, if needed
            e.printStackTrace();
        }

    }

    // Add event handlers
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

    public void createSong() throws IOException {
        /* TODO:
        * åbn nyt vindue
        * indtast sangens navn, kunstner, genre
        * vælg fil via chooseFile()
        * */

        Stage createSongStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-song.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load(), 300, 400);
        createSongStage.setTitle("Tilføj Sang");
        createSongStage.setResizable(false);
        createSongStage.setScene(scene2);
        createSongStage.show();
    }

}
