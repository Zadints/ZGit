package org.zadints.zgit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("index-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        URL css = getClass().getResource("/org/zadints/zgit/app.css");

        if (css == null) {
            throw new RuntimeException("No se encontró app.css");
        }

        scene.getStylesheets().add(css.toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
