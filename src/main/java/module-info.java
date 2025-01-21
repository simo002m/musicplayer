module com.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires javafx.media;
    requires java.desktop;

    opens com.musicplayer to javafx.fxml;
    exports com.musicplayer;
    exports com.musicplayer.controllers;
    opens com.musicplayer.controllers to javafx.fxml;
}
