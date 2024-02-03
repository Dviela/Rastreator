package com.svalero.rastreator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private Button btCWords;
    @FXML
    private Button btCwebs;
    @FXML
    private TabPane tpWebs;
    List<String> keyboards = new ArrayList<>();
    List<String> url = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void chargeWords(ActionEvent event) throws IOException {
        keyboards = crearListaDeArchivo("palabrasClave.txt");
        System.out.println(keyboards);
    }

    @FXML
    public void chargeWebs(ActionEvent event) throws IOException {
        url = crearListaDeArchivo("webs.txt");
        agregarTabsDesdeLista(tpWebs, url);
    }


    private List<String> crearListaDeArchivo(String nombreArchivo) throws IOException {
        List<String> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String url = linea.trim(); // Eliminar espacios en blanco alrededor de la URL

                if (!url.isEmpty()) {
                    lista.add(url);

                }
            }


        }return lista;

    }

    public void agregarTabsDesdeLista(TabPane tabPane, List<String> elementos) throws IOException {

        for (String elemento : elementos) {
            String tabName = elemento.substring(8, 25); //Nombre acortado para el pane

            FXMLLoader loader = new FXMLLoader(AppController.class.getResource("search.fxml"));
            loader.setController(new SearchController(elemento, keyboards));
            Tab tab = new Tab(tabName, loader.load());

            System.out.println(tabName);
            tabPane.getTabs().add(tab);
        }
    }


}
