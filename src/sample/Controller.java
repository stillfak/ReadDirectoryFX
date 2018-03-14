package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML
    private Label path;

    @FXML
    private TextField input;

    @FXML
    private Button open;

    @FXML
    private Button search;
    @FXML
    void initialize() {
        assert open != null : "fx:id=\"open\" was not injected: check your FXML file 'sample.fxml'.";
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'sample.fxml'.";
        File[] file1 = new File[1];
        open.setOnAction(event -> {
            DirectoryChooser directory = new DirectoryChooser();
//            fileChooser.setInitialDirectory();
            directory.setTitle("Select directory for open");
//            directory.setInitialDirectory(new File(String.valueOf(input.getCharacters())));


            file1[0] = new File(String.valueOf(directory.showDialog(new Stage()).getPath()));
//            System.out.println(file1[0].getPath());
            path.setText(file1[0].getPath());
        });
        search.setOnAction(event -> {
            searchDirectory(file1[0],String.valueOf(input.getCharacters()));
        });
    }
    private static void searchDirectory(File pathDirectory, String searchMsg) {
        File[] files = pathDirectory.listFiles();
        assert files != null;

        for (File file : files) {

            if (file.isFile() && file.getName().matches(".*\\.html$")) {
                new searchInFiles(file, searchMsg).start();
            } else if (file.isDirectory()) {
                searchDirectory(file, searchMsg);
            }
        }
    }
}




