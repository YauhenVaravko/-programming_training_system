package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/Menu/menu.fxml"));
        primaryStage.setTitle("Интерактивная электронная система обучения программированию на Java");
        primaryStage.setScene(new Scene(root, 1280, 710));
        root.requestFocus();
        primaryStage.getProperties().put("hostServices", this.getHostServices());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
