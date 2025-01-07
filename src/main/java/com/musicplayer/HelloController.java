package com.musicplayer;

import Services.SongDAO;
import Services.SongDAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void initialize() {
        SongDAO songdao = new SongDAOImpl();

        try {
            songdao.getAllSongs();
        } catch (Exception e) {
        }
    }
}