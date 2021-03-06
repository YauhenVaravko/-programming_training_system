package Lesson1;


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

public class Lesson1Controller implements Initializable {

    @FXML
    private ScrollPane les1_main_scroll_panel;

    @FXML
    private AnchorPane les1_main_panel;

    @FXML
    private Label task_text;

    @FXML
    private TextArea console_text_area;

    @FXML
    private Button check_button;

    @FXML
    private Button run_button;

    @FXML
    private Button to_menu_button;

    @FXML
    private Button reset_button;

    @FXML
    private TabPane code_tab_pane;

    @FXML
    private Tab lesson1_tab;

    @FXML
    private TextArea code_text_area_lesson1;

    @FXML
    private Label title_text;

    @FXML
    private Button close_window_button;

    @FXML
    private Button minimize_window_button;

    @FXML
    private Hyperlink link_to_theory;

    private Stage stage;

    public Stage getStage() {
        if(this.stage==null)
            this.stage = (Stage) this.les1_main_scroll_panel.getScene().getWindow();
        return stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        les1_main_scroll_panel.setVvalue(0);
        setDefaultTextArea();
        run_button.setOnAction(this::runButtonClickAction);
        reset_button.setOnAction(this::resetButtonClickAction);
        check_button.setOnAction(this::checkButtonClickAction);
        close_window_button.setOnAction(this::closeButtonClickAction);
        minimize_window_button.setOnAction(this::minimizeButtonClickAction);
        link_to_theory.setOnAction(this::goToTheory) ;
        to_menu_button.setOnAction(this::goToMenu);
    }
    //button click methods
    public void goToTheory(ActionEvent event){
        HostServices hostServices = (HostServices)this.getStage().getProperties().get("hostServices");
        hostServices.showDocument("http://bsac.by/projects/eemc/java/theory/files/L1.pdf");
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
        code_text_area_lesson1.clear();
        setDefaultTextArea();
    }
    private void runButtonClickAction(ActionEvent event)  {
        reloadFile();
        runTask();
        resultToConsole();
    }
    private void checkButtonClickAction(ActionEvent event){
        reloadFile();
        runTask();
        checkAnswer();
    }
    private void goToMenu(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Menu/menu.fxml", to_menu_button, (HostServices) this.getStage().getProperties().get("hostServices"));
    }

    // others

    private void setDefaultTextArea() {
        ControllerMethods.setDefaultTextArea("./src/Lesson1/Lesson1Default.txt",code_text_area_lesson1);
    }
    private void reloadFile(){
        ControllerMethods.reloadFile("./src/Lesson1/Lesson1.java", code_text_area_lesson1);
    }
    private void runTask()  {
        ControllerMethods.runTask("./src/Lesson1/runTask.bat");
    }
    private void resultToConsole(){
        ControllerMethods.resultToConsole("./src/Lesson1/answer.txt",console_text_area);

    }
    private void checkAnswer(){
        console_text_area.clear();
        Map<String, String> checkList = new HashMap<String, String>();
        checkList.put("public class Lesson1",
                "- Имя класса должно быть Lesson1 и он должен быть публичным.");
        checkList.put("public static void main",
                "- Текст программы должен включать публичный статический метод main.");
        checkList.put("System.out.println",
                "- Для вывода результатов на консоль используйте метод println объекта System.out.");
        Map<String, Boolean> boolCheckList = new HashMap<String, Boolean>();
        boolCheckList.put("public class Lesson1",false);
        boolCheckList.put("public static void main",false);
        boolCheckList.put("System.out.println",false);
        ControllerMethods.searchForMatches(boolCheckList,"./src/Lesson1/Lesson1.java");
        boolean haveDifferent = false;
        haveDifferent=ControllerMethods.haveDifferent("./src/Lesson1/answer.txt","./src/Lesson1/correctAnswer.txt");


        if(haveDifferent){
            console_text_area.appendText("Результат проверки : Задание не выполнено \n");
            console_text_area.appendText("Выполненные требования : \n");
            //console_text_area.appendText("  - Программа должна выводить сообщение \"Я люблю Java!\"(без ковычек) \n");
            for (Map.Entry<String, String> element : checkList.entrySet()) {
                if(boolCheckList.get(element.getKey())==true){
                   console_text_area.appendText("   " + element.getValue() + "\n");
                }
            }
            console_text_area.appendText("Не выполненные требования : \n");
            console_text_area.appendText("  - Программа должна выводить сообщение \"Я люблю Java!\"(без ковычек) \n");
            for (Map.Entry<String, String> element : checkList.entrySet()) {
                if(boolCheckList.get(element.getKey())==false){
                    console_text_area.appendText("   " + element.getValue() + "\n");
                }
            }

        } else {
            console_text_area.setText("Результат проверки : Задание выполнено\n");
            console_text_area.appendText("Выполненные требования : \n");
            console_text_area.appendText("  - Программа должна выводить сообщение \"Я люблю Java!\"(без ковычек) \n");
            for (Map.Entry<String, String> element : checkList.entrySet()) {
                if(boolCheckList.get(element.getKey())==true){
                    console_text_area.appendText("   " + element.getValue() + "\n");
                }
            }
        }

    }
}
