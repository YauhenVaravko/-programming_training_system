package Lesson5;


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

public class Lesson5Controller implements Initializable {

    @FXML
    private ScrollPane les_main_scroll_panel;

    @FXML
    private AnchorPane les_main_panel;

    @FXML
    private Button to_menu_button;

    @FXML
    private Label title_text;

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
    private Tab lesson5_tab;

    @FXML
    private TextArea code_text_area_lesson5;

    @FXML
    private Tab pet_tab;

    @FXML
    private TextArea code_text_area_pet;

    @FXML
    private Tab cat_tab;

    @FXML
    private TextArea code_text_area_cat;

    @FXML
    private Tab dog_tab;

    @FXML
    private TextArea code_text_area_dog;

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
        hostServices.showDocument("http://bsac.by/projects/eemc/java/theory/files/L4.pdf");
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
        code_text_area_lesson5.clear();
        code_text_area_cat.clear();
        code_text_area_dog.clear();
        code_text_area_pet.clear();
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
        ControllerMethods.setDefaultTextArea("./src/Lesson5/Lesson5Default.txt",code_text_area_lesson5);
        ControllerMethods.setDefaultTextArea("./src/Lesson5/PetDefault.txt",code_text_area_pet);
        ControllerMethods.setDefaultTextArea("./src/Lesson5/CatDefault.txt",code_text_area_cat);
        ControllerMethods.setDefaultTextArea("./src/Lesson5/DogDefault.txt",code_text_area_dog);
    }
    private void reloadFiles(){
        ControllerMethods.reloadFile("./src/Lesson5/Dog.java", code_text_area_dog);
        ControllerMethods.reloadFile("./src/Lesson5/Cat.java", code_text_area_cat);
        ControllerMethods.reloadFile("./src/Lesson5/Pet.java", code_text_area_pet);
    }
    private void runTask()  {
        ControllerMethods.runTask("./src/Lesson5/runTask.bat");
    }
    private void resultToConsole(){
        ControllerMethods.resultToConsole("./src/Lesson5/answer.txt",console_text_area);
    }
    private void checkAnswer(){
        console_text_area.clear();
        Map<String, String> checkList = new HashMap<String, String>();
        checkList.put("public String name",
                "- Класс Pet должен содержать публичную строковую переменную name.");
        checkList.put("System.out.println(\"Некий питомец\")",
                "- Класс Pet должeн  содержать метод getName, который выводит на консоль сообщение \"Некий питомец\".");
        checkList.put("System.out.println(\"Кот по кличке \" + this.name);",
                "- Класс Cat должeн  содержать метод getName, который выводит на консоль сообщение \"Кот по кличке (имя_питомца)\".");
        checkList.put("System.out.println(\"Пес по кличке \" + this.name);",
                "- Класс Dog должeн  содержать метод getName, который выводит на консоль сообщение \"Пес по кличке (имя_питомца)\".");
        checkList.put("Cat extends Pet",
                "- Класс Cat должен быть классом-потомком класса Pet.");
        checkList.put("Dog extends Pet",
                "- Класс Dog должен быть классом-потомком класса Pet.");
        Map<String, Boolean> boolCheckList = new HashMap<String, Boolean>();
        boolCheckList.put("public String name",false);
        boolCheckList.put("System.out.println(\"Некий питомец\")",false);
        boolCheckList.put("System.out.println(\"Пес по кличке \" + this.name);",false);
        boolCheckList.put("System.out.println(\"Кот по кличке \" + this.name);",false);
        boolCheckList.put("Cat extends Pet",false);
        boolCheckList.put("Dog extends Pet",false);
        ControllerMethods.searchForMatches(boolCheckList,"./src/Lesson5/Pet.java");
        ControllerMethods.searchForMatches(boolCheckList,"./src/Lesson5/Cat.java");
        ControllerMethods.searchForMatches(boolCheckList,"./src/Lesson5/Dog.java");
        boolean haveDifferent = false;
        haveDifferent=ControllerMethods.haveDifferent("./src/Lesson5/answer.txt","./src/Lesson5/correctAnswer.txt");
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
