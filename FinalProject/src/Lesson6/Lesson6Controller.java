package Lesson6;


import GeneralControllerMethods.ControllerMethods;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Lesson6Controller implements Initializable {

    @FXML
    private ScrollPane les_main_scroll_panel;

    @FXML
    private AnchorPane les_main_panel;

    @FXML
    private Label title_text;

    @FXML
    private Label task_text;

    @FXML
    private TextArea console_text_area;

    @FXML
    private Button to_menu_button;

    @FXML
    private Button check_button;

    @FXML
    private Button run_button;

    @FXML
    private Button reset_button;

    @FXML
    private TabPane code_tab_pane;

    @FXML
    private Tab lesson6_tab;

    @FXML
    private TextArea code_text_area_lesson6;

    @FXML
    private Tab car_tab;

    @FXML
    private TextArea code_text_area_car;

    @FXML
    private Button close_window_button;

    @FXML
    private Button minimize_window_button;

    @FXML
    private Hyperlink link_to_theory;

    private Stage stage;

    public Stage getStage() {
        if(this.stage==null)
            this.stage = (Stage) this.les_main_scroll_panel.getScene().getWindow();
        return stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        les_main_scroll_panel.setVvalue(0);
        setDefaultTextAreas();
        run_button.setOnAction(this::runButtonClickAction);
        reset_button.setOnAction(this::resetButtonClickAction);
        check_button.setOnAction(this::checkButtonClickAction);
        close_window_button.setOnAction(this::closeButtonClickAction);
        minimize_window_button.setOnAction(this::minimizeButtonClickAction);
        link_to_theory.setOnAction(this::goToTheory) ;
        to_menu_button.setOnAction(this::goToMenu);
    }
    //button click methods
    private void goToMenu(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Menu/menu.fxml", to_menu_button, (HostServices) this.getStage().getProperties().get("hostServices"));
    }
    public void goToTheory(ActionEvent event){
        HostServices hostServices = (HostServices)this.getStage().getProperties().get("hostServices");
        hostServices.showDocument("http://bsac.by/projects/eemc/java/theory/files/L5.pdf");
    }
    public void closeButtonClickAction(ActionEvent event) {
        Stage stage = (Stage) close_window_button.getScene().getWindow();
        stage.close();
    }
    public void minimizeButtonClickAction(ActionEvent event) {
        Stage stage = (Stage) minimize_window_button.getScene().getWindow();
        stage.setIconified(true);
    }
    private void resetButtonClickAction(ActionEvent event)  {
        code_text_area_lesson6.clear();
        code_text_area_car.clear();
        setDefaultTextAreas();
    }
    private void runButtonClickAction(ActionEvent event)  {
        reloadFiles();
        runTask();
        resultToConsole();
    }
    private void checkButtonClickAction(ActionEvent event){
        reloadFiles();
        runTask();
        checkAnswer();
    }

    // others

    private void setDefaultTextAreas() {
        ControllerMethods.setDefaultTextArea("./src/Lesson6/Lesson6Default.txt",code_text_area_lesson6);
        ControllerMethods.setDefaultTextArea("./src/Lesson6/CarDefault.txt",code_text_area_car);

    }
    private void reloadFiles(){
        ControllerMethods.reloadFile("./src/Lesson6/Car.java", code_text_area_car);
    }
    private void runTask()  {
        ControllerMethods.runTask("./src/Lesson6/runTask.bat");
    }
    private void resultToConsole(){
        ControllerMethods.resultToConsole("./src/Lesson6/answer.txt",console_text_area);
    }
    private void checkAnswer(){
        console_text_area.clear();
        Map<String, String> checkList = new HashMap<String, String>();
        checkList.put("public String model",
                "- Класс Car должен содержать публичную строковую переменную model.");
        checkList.put("public Car(String",
                "- Класс Car должен иметь конструктор, который принимает в качестве параметра строковую переменную.");
        checkList.put("public void run(",
                "- Класс Car должен иметь публичный метод run, который не возвращает никаких данных.");
        checkList.put("public class SteeringWheel",
                "- Класс Car должен содержать публичный внутренний класс SteeringWheel.");
        checkList.put("System.out.println(\"Руль вправо!\");",
                "- Публичный метод right класса SteeringWheel должен выводить на консоль сообщение \"Руль вправо!\"(без ковычек).");
        checkList.put("System.out.println(\"Руль влево!\");",
                "- Публичный метод left  класса SteeringWheel должен выводить на консоль сообщение \"Руль влево!\"(без ковычек).");
        Map<String, Boolean> boolCheckList = new HashMap<String, Boolean>();
        boolCheckList.put("public String model",false);
        boolCheckList.put("public Car(String",false);
        boolCheckList.put("public void run(",false);
        boolCheckList.put("public class SteeringWheel",false);
        boolCheckList.put("System.out.println(\"Руль вправо!\");",false);
        boolCheckList.put("System.out.println(\"Руль влево!\");",false);
        ControllerMethods.searchForMatches(boolCheckList,"./src/Lesson6/Car.java");
        boolean haveDifferent = false;
        haveDifferent=ControllerMethods.haveDifferent("./src/Lesson6/answer.txt","./src/Lesson6/correctAnswer.txt");
        if(haveDifferent){
            console_text_area.appendText("Результат проверки : Задание не выполнено \n");
            console_text_area.appendText("Выполненные требования : \n");
            for (Map.Entry<String, String> element : checkList.entrySet()) {
                if(boolCheckList.get(element.getKey())==true){
                   console_text_area.appendText("   " + element.getValue() + "\n");
                }
            }
            console_text_area.appendText("Не выполненные требования : \n");
            console_text_area.appendText("   - Результат выполнения программы должен соответсвовать заданию.\n");
            for (Map.Entry<String, String> element : checkList.entrySet()) {
                if(boolCheckList.get(element.getKey())==false){
                    console_text_area.appendText("   " + element.getValue() + "\n");
                }
            }

        } else {
            console_text_area.setText("Результат проверки : Задание выполнено\n");
            console_text_area.appendText("Выполненные требования : \n");
            console_text_area.appendText("   - Результат выполнения программы должен соответсвовать заданию.\n");
            for (Map.Entry<String, String> element : checkList.entrySet()) {
                if(boolCheckList.get(element.getKey())==true){
                    console_text_area.appendText("   " + element.getValue() + "\n");
                }
            }
        }

    }
}
