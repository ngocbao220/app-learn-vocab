package org.example.learnvovab;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {

    @FXML
    private Circle circle;

    @FXML
    private Label label;

    @FXML
    private TextField text;

    @FXML
    private Button showAnswer;

    @FXML
    private Label answer;

    private Stage stage;

    Timeline timeline;

    Timeline othertimeline;

    Map<String, String> map = new HashMap<>();
    Set<String> set = map.keySet();

    Random random = new Random();
    String[] array;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setOnAction(e -> {
            String userInput = text.getText().toLowerCase();
            String currentWord = label.getText().toLowerCase();

            if (userInput.equals(map.get(currentWord))) {
                circle.setFill(Color.GREEN);
                changeQuestion();
            } else {
                circle.setFill(Color.RED);
            }
            timeline.play();
            text.clear();
        });

        showAnswer.setOnAction(e -> {
            answer.setText(map.get(label.getText()));
            othertimeline.play();
        });

       timeline = new Timeline(new KeyFrame(Duration.seconds(0.4), e -> {
            circle.setFill(Color.GRAY);
       }));

       othertimeline = new Timeline(new KeyFrame(Duration.seconds(0.4), e -> {
           answer.setText("");
       }));
    }

    @FXML
    private void handleFileSelection() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn Tệp Dữ Liệu");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            readFile(file);
            array = set.toArray(new String[0]);
            changeQuestion();
        }
    }

    private void readFile(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            map.clear(); // Xóa dữ liệu cũ

            while ((line = bufferedReader.readLine()) != null) {
                String[] part = line.split(":");
                if (part.length == 2) {
                    String word = part[0].trim().toLowerCase();
                    String def = part[1].trim().toLowerCase();

                    map.put(def, word);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(map);
    }

    void changeQuestion() {
        if (array.length > 0) {
            label.setText(array[random.nextInt(array.length)]);
        } else {
            label.setText("Không có từ nào.");
        }
    }
}
