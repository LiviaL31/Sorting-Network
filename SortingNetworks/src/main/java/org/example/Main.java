package org.example;

import java.util.Arrays;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;


import javafx.scene.control.Alert.AlertType;

public class Main extends Application {
    private TextField inputField;
    private Label outputLabel;
    private TextArea historyArea;
    private SortingNetworks sortingNetworks;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Network App");

        Label inputLabel = new Label("Introduceți numere separate prin virgulă:");
        inputField = new TextField();
        Button sortButton = new Button("Sortează");
        sortButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                sortNumbers();
                openSortingVisualizer();
            }
        });

        outputLabel = new Label();
        historyArea = new TextArea();
        historyArea.setEditable(false);
        Button loadButton = new Button("Încarcă Istoricul");
        loadButton.setOnAction(e -> loadHistory());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(inputLabel, inputField, sortButton, outputLabel, loadButton, historyArea);

        // Adăugarea VBox-ului într-un ScrollPane
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(false);  // Ajustează scroll pe lățime
        scrollPane.setFitToHeight(false); // Ajustează scroll pe înălțime
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        layout.setMaxHeight(Region.USE_PREF_SIZE);
        layout.setMaxWidth(Region.USE_PREF_SIZE);


        Scene scene = new Scene(scrollPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveSortedNumbers(int[] numbers) {
        StringBuilder result = new StringBuilder();
        for (int num : numbers) {
            result.append(num).append(" ");
        }
        String sortedNumbersStr = result.toString().trim();

        try (FileWriter writer = new FileWriter("sorted_numbers.txt")) {
            writer.write(sortedNumbersStr);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file writing error message to the user
            showAlert("Error", "Failed to save sorted numbers: " + e.getMessage());
        }
    }

    //12, 45, 23, 78, 34, 56, 90, 67, 19, 83, 41, 62, 3, 88, 29, 71, 52, 95, 16, 30, 54, 99, 11, 38, 72, 43, 5, 80, 26, 50, 64, 13, 32, 87, 8, 77, 28 -nu la sortat corect
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void sortNumbers() {
        String input = inputField.getText();
        if (!NumberInputValidator.isValidInput(input)) {
            outputLabel.setText("Introduceți numere valide separate prin virgulă!");
            return;
        }
        String[] numbersStr = input.split(",");
        int[] numbers = new int[numbersStr.length];
        for (int i = 0; i < numbersStr.length; i++) {
            try {
                numbers[i] = Integer.parseInt(numbersStr[i].trim());
            } catch (NumberFormatException e) {
                outputLabel.setText("Introduceți numere valide separate prin virgulă!");
                return;
            }
        }
        int nrElements = numbers.length;
        // Create an instance of SortingNetworks and sort the numbers
        SortingNetworks sortingNetworks = new SortingNetworks(nrElements);
        // Example parameters
        // sortingNetworks.sort();
        //SortingNetworks sortingNetworks = new SortingNetworks(numbers);
        // Update output label
        outputLabel.setText("Tabloul sortat: " + Arrays.toString(numbers));
    }

    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("history.txt"))) {
            StringBuilder historyBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                historyBuilder.append(line).append("\n");
            }
            String historyStr = historyBuilder.toString();
            historyArea.setText(historyStr);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file reading error
            System.err.println("Failed to load history: " + e.getMessage());
        }
    }

    private void openSortingVisualizer() {
        String input = inputField.getText();
        if (!NumberInputValidator.isValidInput(input)) {
            outputLabel.setText("Introduceți numere valide separate prin virgulă!");
            return;
        }
        String[] numbersStr = input.split(",");
        int[] numbers = new int[numbersStr.length];
        for (int i = 0; i < numbersStr.length; i++) {
            try {
                numbers[i] = Integer.parseInt(numbersStr[i].trim());
            } catch (NumberFormatException e) {
                outputLabel.setText("Introduceți numere valide separate prin virgulă!");
                return;
            }
        }
        if (numbers.length == 0){
            outputLabel.setText("Sirul furnizat este gol");

    }


        SortingVisualizer visualizer = new SortingVisualizer(numbers);
        visualizer.show(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}