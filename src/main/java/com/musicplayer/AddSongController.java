package com.musicplayer;

import Models.Song;
import Services.SongDAO;
import Services.SongDAOImpl;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AddSongController
{
    private File selectedFile;
    private String fileChosen = null;

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
            fileChosen = selectedFile.getName();
            fileMessage.setText(fileChosen);

            this.selectedFile = selectedFile;
        }
        else
        {
            fileMessage.setText("No file selected");
        }
    }

    private void songDuration(String filePath)
    {
        try
        {
            System.out.println(filePath);
            String path = ("src/main/resources/media/" + filePath);
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaplayer = new MediaPlayer(media);

            mediaplayer.setOnReady(new Runnable()
            {
                @Override
                public void run()
                {
                    insertToDB((int)mediaplayer.getMedia().getDuration().toSeconds());
                }
            });
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private boolean uploadSelectedFile()
    {
        // Get the resources directory path
        String resourcePath = "src/main/resources/media";

        // Ensure the path is properly decoded (handles spaces and special characters)
        Path resourcesDirectory = Path.of(resourcePath);

        // Define the target path in the resources directory
        Path targetPath = resourcesDirectory.resolve(this.selectedFile.getName());
        try
        {
            // Copy the file to the resources directory
            Files.copy(this.selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }
        catch (Exception e)
        {
            System.err.println("Failed to upload file: " + e.getMessage());
            return false;
        }
    }

    private void insertToDB(int duration)
    {
        try
        {
            SongDAO songdao = new SongDAOImpl();

            Song song = new Song(title.getText(), artist.getText(), duration, fileChosen, genre.getText());
            songdao.addSong(song);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void addSong() throws Exception
    {
        try
        {
            if (uploadSelectedFile())
            {
                songDuration(fileChosen);
            }
            else
            {
                fileMessage.setText("Failed to upload file");
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}