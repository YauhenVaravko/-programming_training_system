package Menu;


import GeneralControllerMethods.ControllerMethods;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class menuController implements Initializable {

    @FXML
    private AnchorPane les1_main_panel;

    @FXML
    private ImageView image_icon;

    @FXML
    private Label menu_title;

    @FXML
    private Button close_window_button;

    @FXML
    private Button minimize_window_button;

    @FXML
    private Label menu_title1;

    @FXML
    private Label menu_title11;

    @FXML
    private Hyperlink user_manual_button;

    @FXML
    private Button button_toLesson1;

    @FXML
    private Button button_toLesson2;

    @FXML
    private Button button_toLesson3;

    @FXML
    private Button button_toLesson4;

    @FXML
    private Button button_toLesson5;

    @FXML
    private Button button_toLesson6;

    @FXML
    private Button button_toLesson7;

    private Stage stage;

    public Stage getStage() {
        if(this.stage==null)
            this.stage = (Stage) this.les1_main_panel.getScene().getWindow();
        return stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close_window_button.setOnAction(this::closeButtonClickAction);
        minimize_window_button.setOnAction(this::minimizeButtonClickAction);
        button_toLesson1.setOnAction(this::goToLesson1);
        button_toLesson2.setOnAction(this::goToLesson2);
        button_toLesson3.setOnAction(this::goToLesson3);
        button_toLesson4.setOnAction(this::goToLesson4);
        button_toLesson5.setOnAction(this::goToLesson5);
        button_toLesson6.setOnAction(this::goToLesson6);
        button_toLesson7.setOnAction(this::goToLesson7);
        user_manual_button.setOnAction(this::goToUserManual);

    }


    //button click methods
    public void goToUserManual(ActionEvent event){
        HostServices hostServices = (HostServices)this.getStage().getProperties().get("hostServices");
        hostServices.showDocument(getClass().getResource("/usersManual.pdf").toString());
    }

    public void closeButtonClickAction(ActionEvent event) {
        Stage stage = (Stage) close_window_button.getScene().getWindow();
        stage.close();
    }
    public void minimizeButtonClickAction(ActionEvent event) {
        Stage stage = (Stage) minimize_window_button.getScene().getWindow();
        stage.setIconified(true);
    }
    public void goToLesson1(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Lesson1/Lesson1.fxml", button_toLesson1 , (HostServices) this.getStage().getProperties().get("hostServices"));
    }
    public void goToLesson2(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Lesson2/Lesson2.fxml", button_toLesson2, (HostServices) this.getStage().getProperties().get("hostServices"));
    }
    public void goToLesson3(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Lesson3/Lesson3.fxml", button_toLesson3, (HostServices) this.getStage().getProperties().get("hostServices"));
    }
    public void goToLesson4(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Lesson4/Lesson4.fxml", button_toLesson4, (HostServices) this.getStage().getProperties().get("hostServices"));
    }
    public void goToLesson5(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Lesson5/Lesson5.fxml", button_toLesson5, (HostServices) this.getStage().getProperties().get("hostServices"));
    }
    public void goToLesson6(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Lesson6/Lesson6.fxml", button_toLesson6, (HostServices) this.getStage().getProperties().get("hostServices"));
    }
    public void goToLesson7(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Lesson7/Lesson7.fxml", button_toLesson7, (HostServices) this.getStage().getProperties().get("hostServices"));
    }

}
