package me.fingerprint.presence.marker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.fingerprint.presence.marker.database.Database;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("css/style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        stage = primaryStage;
        Database.connect();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
