package org.zadints.zgit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("index-view.fxml")
        );

        Parent root = fxmlLoader.load();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double width = bounds.getWidth() * 0.60;
        double height = bounds.getHeight() * 0.80;

        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("/org/zadints/zgit/app.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("ZGit");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setX(
                bounds.getMinX() + (bounds.getWidth() - width) / 2
        );

        stage.setY(
                bounds.getMinY() + (bounds.getHeight() - height) / 2
        );

        stage.show();
    }


    public static void main(String[] args) {
        /*
        for (Octicons icon : Octicons.values()) {
            System.out.println(icon.name());
        }*/
        launch(App.class, args);
    }
}
