package GeneralControllerMethods;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ControllerMethods  {
    public static void setDefaultTextArea(String FileFrom, TextArea textAreaTo){
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(FileFrom),
                        StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null) {
                textAreaTo.appendText(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("input error");
        }
    }

    public static void reloadFile(String FileTo, TextArea textAreaFrom){
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(FileTo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        try {
            outputStreamWriter.write(textAreaFrom.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runTask(String fileFrom){
        try {
            Runtime.getRuntime().exec(fileFrom).waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void resultToConsole(String FileFrom, TextArea textAreaTo){
        textAreaTo.clear();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(FileFrom),
                        StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null) {
                textAreaTo.appendText(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchForMatches(Map<String, Boolean> boolCheckList, String FileWere){
        List<String> codeList = null;
        try {
            codeList = Files.readAllLines(Paths.get(FileWere), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Boolean> entry : boolCheckList.entrySet()) {
            for(String codeListElement : codeList){
                if(codeListElement.indexOf(entry.getKey())!=-1){
                    boolCheckList.put(entry.getKey(),true);
                }
            }
        }
    }

    public static boolean haveDifferent(String link1, String link2){
        List<String> links1 = null;
        List<String> links2 = null;
        boolean haveDifferent = false;
        try {
            links1 = Files.readAllLines(Paths.get(link1), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            links2 = Files.readAllLines(Paths.get(link2), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!(links1.size()==0)) {
            for (int i = 0; i < links1.size(); i++) {

                if (!links1.get(i).equals(links2.get(i))) {
                    haveDifferent = true;
                    break;
                }
            }
        }  else {
            haveDifferent = true;
        }
        return  haveDifferent;
    }

    public void goToLesson(String resource, Button button, HostServices hostServices){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1, 1280,710));
            root1.requestFocus();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Интерактивная электронная система обучения программированию на Java");
            stage.getProperties().put("hostServices", hostServices);
            stage.show();
            Stage stage1 = (Stage) button.getScene().getWindow();
            stage1.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
