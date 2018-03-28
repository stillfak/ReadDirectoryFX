package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Класс отслеживает действия пользователя с графическим интерфейсом
 *
 * @author Gavrikov V 15it18
 */
public class Controller {
    private static File startDirectory;
    @FXML
    private Label path;

    @FXML
    private TextField input;

    @FXML
    private TextField type;

    @FXML
    private Button open;

    @FXML
    private Button search;

    @FXML
    private Label statusReady;


    @FXML
    void initialize() {
        open.setOnAction(event -> {
            DirectoryChooser directory = new DirectoryChooser();
            directory.setTitle("Select directory for open");
            startDirectory = new File(String.valueOf(directory.showDialog(new Stage())));
            path.setText(startDirectory.getPath());
        });
        search.setOnAction((ActionEvent event) -> {
            try {
                SearchInFiles.starter(startDirectory, String.valueOf(input.getCharacters()), String.valueOf(type.getCharacters()));
            } catch (IOException | InterruptedException | IndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            }
            statusReady.setText("успешно");
        });
    }

}




