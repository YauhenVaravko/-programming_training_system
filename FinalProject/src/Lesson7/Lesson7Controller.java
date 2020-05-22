package Lesson7;


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

public class Lesson7Controller implements Initializable {

    @FXML
    private ScrollPane les_main_scroll_panel;

    @FXML
    private AnchorPane les_main_panel;

    @FXML
    private Label title_text;

    @FXML
    private Label task_text;

    @FXML
    private Button to_menu_button;

    @FXML
    private TextArea console_text_area;

    @FXML
    private Button check_button;

    @FXML
    private Button run_button;

    @FXML
    private Button reset_button;

    @FXML
    private TabPane code_tab_pane;

    @FXML
    private Tab lesson7_tab;

    @FXML
    private TextArea code_text_area_lesson7;

    @FXML
    private Tab person_tab;

    @FXML
    private TextArea code_text_area_person;

    @FXML
    private Tab woman_tab;

    @FXML
    private TextArea code_text_area_woman;

    @FXML
    private Tab ieating_tab;

    @FXML
    private TextArea code_text_area_ieating;

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
        hostServices.showDocument("http://bsac.by/projects/eemc/java/theory/files/L6.pdf");
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
        code_text_area_lesson7.clear();
        code_text_area_person.clear();
        code_text_area_woman.clear();
        code_text_area_ieating.clear();
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
        ControllerMethods.setDefaultTextArea("./src/Lesson7/Lesson7Default.txt",code_text_area_lesson7);
        ControllerMethods.setDefaultTextArea("./src/Lesson7/PersonDefault.txt",code_text_area_person);
        ControllerMethods.setDefaultTextArea("./src/Lesson7/WomanDefault.txt",code_text_area_woman);
        ControllerMethods.setDefaultTextArea("./src/Lesson7/IEatingDefault.txt",code_text_area_ieating);
    }
    private void reloadFiles(){
        ControllerMethods.reloadFile("./src/Lesson7/Person.java", code_text_area_person);
        ControllerMethods.reloadFile("./src/Lesson7/IEating.java", code_text_area_ieating);
        ControllerMethods.reloadFile("./src/Lesson7/Woman.java", code_text_area_woman);
    }
    private void runTask()  {
        ControllerMethods.runTask("./src/Lesson7/runTask.bat");
    }
    private void resultToConsole(){
        ControllerMethods.resultToConsole("./src/Lesson7/answer.txt",console_text_area);
    }
    private void checkAnswer(){
        console_text_area.clear();
        Map<String, String> checkList = new HashMap<String, String>();
        checkList.put("Person implements IEating",
                "- Класс Person должен реализовать интерфейс IEating");
        checkList.put("Woman extends Person",
                "- Класс Woman должен быть классом-потомком класса Person.");
        checkList.put("System.out.println(\"Девушка завтракает\");",
                "- Публичный метод haveBreakfast класса Woman должен выводить на консоль сообщение \"Девушка завтракает\".");
        checkList.put("System.out.println(\"Девушка обедает\");",
                "- Публичный метод haveDinner класса Woman должен выводить на консоль сообщение \"Девушка обедает\".");
        Map<String, Boolean> boolCheckList = new HashMap<String, Boolean>();
        boolCheckList.put("Person implements IEating",false);
        boolCheckList.put("Woman extends Person",false);
        boolCheckList.put("System.out.println(\"Девушка завтракает\");",false);
        boolCheckList.put("System.out.println(\"Девушка обедает\");",false);
        ControllerMethods.searchForMatches(boolCheckList,"./src/Lesson7/Person.java");
        ControllerMethods.searchForMatches(boolCheckList,"./src/Lesson7/IEating.java");
        ControllerMethods.searchForMatches(boolCheckList,"./src/Lesson7/Woman.java");
        boolean haveDifferent = false;
        haveDifferent=ControllerMethods.haveDifferent("./src/Lesson7/answer.txt","./src/Lesson7/correctAnswer.txt");
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
