package org.sersun.tfcc.tfcc;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsController {
    private TFCCController tfccController;

    public void setTFCCController(TFCCController controller) {
        this.tfccController = controller;
    }

    @FXML private TextField valueField1;
    @FXML private TextField valueField2;
    @FXML private TextField valueField3;
    @FXML private TextField valueField4;
    @FXML private TextField valueField5;
    @FXML private TextField valueField6;
    @FXML private TextField valueField7;
    @FXML private TextField valueField8;


    @FXML
    private void handleSubmit() {
        List<Double> values = new ArrayList<>();
        try {
            values.add(Double.parseDouble(valueField1.getText()));
            values.add(Double.parseDouble(valueField2.getText()));
            values.add(Double.parseDouble(valueField3.getText()));
            values.add(Double.parseDouble(valueField4.getText()));
            values.add(Double.parseDouble(valueField5.getText()));
            values.add(Double.parseDouble(valueField6.getText()));
            values.add(Double.parseDouble(valueField7.getText()));
            values.add(Double.parseDouble(valueField8.getText()));

            // Сохранение измененных значений в файл
            saveData(values);


            // Убедитесь, что tfccController не null перед его использованием
            if(tfccController != null) {
                tfccController.refreshListView();
            }

            // Закрытие окна настроек
            closeSettingsWindow();

        } catch (NumberFormatException e) {
            // Обработка ошибки, если ввод невалиден
            System.out.println("Одно из введенных значений не является числом.");
        }
    }

    private void saveData(List<Double> data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);

        try (FileWriter writer = new FileWriter(getClass().getResource("/org/sersun/tfcc/tfcc/setting.json").getPath());
        ) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeSettingsWindow() {
        Stage stage = (Stage) valueField1.getScene().getWindow(); // Получение stage из любого поля
        stage.close(); // Закрытие окна
    }





    // Инициализация данных
    public void initData(List<String> values) {
        // Проверяем, есть ли данные и достаточно ли полей
        if (!values.isEmpty()) {
            valueField1.setText(values.size() > 0 ? values.get(0) : "");
            valueField2.setText(values.size() > 1 ? values.get(1) : "");
            valueField3.setText(values.size() > 2 ? values.get(2) : "");
            valueField4.setText(values.size() > 3 ? values.get(3) : "");
            valueField5.setText(values.size() > 4 ? values.get(4) : "");
            valueField6.setText(values.size() > 5 ? values.get(5) : "");
            valueField7.setText(values.size() > 6 ? values.get(6) : "");
            valueField8.setText(values.size() > 7 ? values.get(7) : "");
            // Продолжите инициализацию остальных полей если необходимо
        }
    }




}
