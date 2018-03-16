package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {
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

        File[] file1 = new File[1];
        open.setOnAction(event -> {
            DirectoryChooser directory = new DirectoryChooser();
            directory.setTitle("Select directory for open");
            file1[0] = new File(String.valueOf(directory.showDialog(new Stage())));
            path.setText(file1[0].getPath());

        });
        search.setOnAction(event -> {
            try {
                searchInFiles.starter(file1[0],String.valueOf(input.getCharacters()),String.valueOf(type.getCharacters()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            statusReady.setText("успешно");
        });
    }

}




