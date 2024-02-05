package org.sersun.tfcc.tfcc;

import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class TFCCController {
    @FXML
    private ImageView imageView; // This should match the fx:id
    @FXML
    private ListView<String> CoeficientListView; // Ensure the generic type matches the type of items in the list
    private ObservableList<String> listItems = FXCollections.observableArrayList();
    @FXML
    private TextField TotalDistanceField;
    @FXML
    private TextField ChargetDistanceField;
    @FXML
    private TextField WeightField;
    @FXML
    private TextField tonoKmField;
    @FXML
    private TextField ConsumField;

    private String selectedValue;

    // Method to load data from JSON file
    private List<String> loadData() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(getClass().getResource("/org/sersun/tfcc/tfcc/setting.json").getPath())) {
            Type type = new TypeToken<List<String>>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            return new ArrayList<>(); // Если файл не найден, возвращаем пустой список
        }
    }

    public void refreshListView() {
        System.out.println("Обновление ListView...");
        List<String> updatedValues = loadData(); // Загрузка обновSystem.out.println("Обновление ListView...");ленных значений
        listItems.clear(); // Очистка текущих элементов
        listItems.addAll(updatedValues); // Добавление новых значений
        System.out.println("ListView обновлен.");
    }



    public void initialize() {
        // Assuming your image path is correct and accessible
        //Image image = new Image(getClass().getResourceAsStream("/org/sersun/tfcc/tfcc/classic-american-truck-600nw-1152318527.png"));
        //imageView.setImage(image);
        // Populate the list
        //listItems.addAll("0.395", "0.375", "0.550", "0.650",
         //       "0.00", "0.00", "0.00", "0.00");

        // Bind the ObservableList to the ListView
        List<String> loadedData = loadData();
        listItems.addAll(loadedData);
        CoeficientListView.setItems(listItems);

        // add listener for selection in the ListView
        CoeficientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedValue = newValue;
        });
    }

    // Method to add new values to the ListView
    public void addValuesToListView(List<String> newValues) {
        listItems.addAll(newValues);
    }





    // Method to handle Calculate button action
    @FXML
    private void Calculate() {
        try {
            double totalDistance = Double.parseDouble(TotalDistanceField.getText());
            double chargeDistance = Double.parseDouble(ChargetDistanceField.getText());
            double weight = Double.parseDouble(WeightField.getText());
            double consumPerKm = Double.parseDouble(selectedValue);

            // Calculate tonokm
            double tonoKmValue = chargeDistance * weight;
            tonoKmField.setText(String.valueOf(tonoKmValue));

            // Calculate consum of fuel
            // Расход топлива = (1.3 * Тонно-километры / 100) + (Общее расстояние * Норма потребления топлива)
            double consumValue = consumPerKm * totalDistance + 1.3 * tonoKmValue / 100;
            ConsumField.setText(String.valueOf(consumValue));

        } catch (NumberFormatException e) {
            // Handle parse error or invalid input
            tonoKmField.setText("Invalid input");
        }
    }


    // Method to handle Clear button action
    @FXML
    private void ClearForm() {
        TotalDistanceField.setText("");
        ChargetDistanceField.setText("");
        WeightField.setText("");
        tonoKmField.setText("");
        ConsumField.setText("");

        // Перемещение фокуса на первое текстовое поле
        TotalDistanceField.requestFocus();
    }

    // Method to handle Copy action to copy value from tonoKmField to clipboard
    @FXML
    private void copyTonoKmToClipboard() {
        String text = tonoKmField.getText();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    // Method to handle Copy action to copy value from ConsumField to clipboard
    @FXML
    private void copyConsumToClipboard() {
        String text = ConsumField.getText();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }


    @FXML
    private void handleSettingAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsWindow.fxml"));
            Parent root = loader.load();

            SettingsController settingsController = loader.getController();
            settingsController.setTFCCController(this); // Устанавливаем ссылку на основной контроллер


            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));

            // Получаем текущие значения из ListView
            List<String> currentValues = new ArrayList<>(CoeficientListView.getItems());
            // Передаем значения в контроллер настроек
            settingsController.initData(currentValues);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}