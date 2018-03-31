package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * Класс отслеживает действия пользователя с графическим интерфейсом
 *
 * @author Gavrikov V 15it18
 */
public class Controller {
    private static final String CHECK_FILE = "result.txt";
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
    void initialize() throws NullPointerException{
        open.setOnAction(event -> {
            DirectoryChooser directory = new DirectoryChooser();
            directory.setTitle("Select directory for open");
            startDirectory = new File(String.valueOf(directory.showDialog(new Stage())));
            path.setText(startDirectory.getPath());
            if (startDirectory.getPath() != null){
                statusReady.setText("");

            }
        });
        search.setOnAction((ActionEvent event) -> {
            try {
                SearchInFiles.starter(startDirectory, String.valueOf(input.getCharacters()), String.valueOf(type.getCharacters()));
            } catch (IOException | InterruptedException | IndexOutOfBoundsException | NullPointerException e) {
//                e.printStackTrace();
            }
            try (BufferedReader bf = new BufferedReader(new FileReader(new File(CHECK_FILE)))){

                if (bf.readLine()==null) statusReady.setText("ничего не найдено");

                else statusReady.setText("успешно");

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

}




