package Lesson3;


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

public class Lesson3Controller implements Initializable {

    @FXML
    private ScrollPane les_main_scroll_panel;

    @FXML
    private AnchorPane les_main_panel;

    @FXML
    private Label title_text;

    @FXML
    private Button to_menu_button;

    @FXML
    private Label task_text;

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
    private Tab lesson3_tab;

    @FXML
    private TextArea code_text_area_lesson3;

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
    private void goToMenu(ActionEvent event){
        ControllerMethods cm = new ControllerMethods();
        cm.goToLesson("/Menu/menu.fxml", to_menu_button, (HostServices) this.getStage().getProperties().get("hostServices"));
    }
    public void goToTheory(ActionEvent event){
        HostServices hostServices = (HostServices)this.getStage().getProperties().get("hostServices");
        hostServices.showDocument("http://bsac.by/projects/eemc/java/theory/files/L2.pdf");
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
        code_text_area_lesson3.clear();
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

    // others

    private void setDefaultTextArea() {
        ControllerMethods.setDefaultTextArea("./src/Lesson3/Lesson3Default.txt",code_text_area_lesson3);
    }
    private void reloadFile(){
        ControllerMethods.reloadFile("./src/Lesson3/Lesson3.java", code_text_area_lesson3);
    }
    private void runTask()  {
        ControllerMethods.runTask("./src/Lesson3/runTask.bat");
    }
    private void resultToConsole(){
        ControllerMethods.resultToConsole("./src/Lesson3/answer.txt",console_text_area);

    }
    private void checkAnswer(){
        console_text_area.clear();
        Map<String, String> checkList = new HashMap<String, String>();
        checkList.put("public class Lesson3",
                "- Имя класса должно быть Lesson3 и он должен быть публичным.");
        checkList.put("public static void main",
                "- Текст программы должен включать публичный статический метод main.");
        checkList.put("Integer.parseInt",
                "- Необходимо совершить преобразование массива строк stringArray в массив чисел intArray с помощью метода parseInt класса Integer.");
        Map<String, Boolean> boolCheckList = new HashMap<String, Boolean>();
        boolCheckList.put("public class Lesson3",false);
        boolCheckList.put("public static void main",false);
        boolCheckList.put("Integer.parseInt",false);
        ControllerMethods.searchForMatches(boolCheckList,"./src/Lesson3/Lesson3.java");
        boolean haveDifferent = false;
        haveDifferent=ControllerMethods.haveDifferent("./src/Lesson3/answer.txt","./src/Lesson3/correctAnswer.txt");
        if(haveDifferent){
            console_text_area.appendText("Результат проверки : Задание не выполнено \n");
            console_text_area.appendText("Выполненные требования : \n");
            for (Map.Entry<String, String> element : checkList.entrySet()) {
                if(boolCheckList.get(element.getKey())==true){
                   console_text_area.appendText("   " + element.getValue() + "\n");
                }
            }
            console_text_area.appendText("Не выполненные требования : \n");
            console_text_area.appendText("   - Программа должна выводить отредактированный массив.(Каждый элемент с новой строки).\n");
            for (Map.Entry<String, String> element : checkList.entrySet()) {
                if(boolCheckList.get(element.getKey())==false){
                    console_text_area.appendText("   " + element.getValue() + "\n");
                }
            }

        } else {
            console_text_area.setText("Результат проверки : Задание выполнено\n");
            console_text_area.appendText("Выполненные требования : \n");
            console_text_area.appendText("  - Программа должна выводить отредактированный массив.(Каждый элемент с новой строки).\n");
            for (Map.Entry<String, String> element : checkList.entrySet()) {
                if(boolCheckList.get(element.getKey())==true){
                    console_text_area.appendText("   " + element.getValue() + "\n");
                }
            }
        }

    }
}
