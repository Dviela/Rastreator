package com.svalero.rastreator;

import com.svalero.rastreator.task.SearchTask;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    ProgressBar pBar;
    Label lbPorcent;
    private SearchTask searchTask;
    private String url;
    private List<String> keyboard;

    public SearchController(String url, List<String> keyboard) {
        this.url = url;
        this.keyboard = keyboard;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            searchTask = new  SearchTask(this.url, this.keyboard);
            //Barra de progreso
            pBar.progressProperty().unbind();
            pBar.progressProperty().bind(searchTask.progressProperty());
            //Estado de la tarea
            searchTask.stateProperty().addListener((observableValue, state, t1) -> {
                if (t1 == Worker.State.SUCCEEDED){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Busqueda finalizada");
                    alert.show();
                }
            });

            new Thread(searchTask).start();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
