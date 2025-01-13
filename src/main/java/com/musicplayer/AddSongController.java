package com.musicplayer;

import Models.Song;
import Services.SongDAO;
import Services.SongDAOImpl;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AddSongController {

    static String fileChosen = null;

    @FXML
    private Label fileMessage;

    @FXML
    private TextField title;

    @FXML
    private TextField artist;

    @FXML
    private TextField genre;

    // This method will be triggered when the user clicks the "Choose File" button
    @FXML
    private void chooseFile()
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
            fileChosen = selectedFile.getAbsolutePath();
            fileMessage.setText(fileChosen);

            // Example: you can add functionality to play the music using MediaPlayer
            // For instance, create a MediaPlayer here to play the song.
        } else
        {
            fileMessage.setText("No file selected");
        }
    }

    private String songDuration(String filePath) throws UnsupportedAudioFileException, IOException {
        File file = new File(filePath);

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        double durationInSeconds = (frames + 0.0) / format.getFrameRate();

        double hours = durationInSeconds / 3600;
        double minutes = (durationInSeconds % 3600) / 60;
        double seconds = durationInSeconds % 60;

        return String.format("%02d:%02d:%02d", (int) hours, (int) minutes, (int) seconds);
    }

    @FXML
    private void addSongToDB() throws Exception {
        SongDAO dao = new SongDAOImpl();
        Song song = new Song(title.getText(), artist.getText(), "", fileChosen, genre.getText());

        dao.addSong(song);
    }

}
