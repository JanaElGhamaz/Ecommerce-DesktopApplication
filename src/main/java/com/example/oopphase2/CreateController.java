package com.example.oopphase2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateController {
    @FXML
    private Label label;
    @FXML
    private AnchorPane root;
    @FXML
    private Button submit;
    @FXML
    private Button Backbutton;
    @FXML
    private Label cp;

    private boolean manager = false;
    private Admin currentAdminCreate;
    private List<TextField> textFields = new ArrayList<>();

    @FXML
    void submit(MouseEvent event) throws IOException {
        String[] texts = new String[textFields.size()];

        for (int i = 0; i < textFields.size(); i++) {
            TextField textField = textFields.get(i);
            texts[i] = textField.getText();

            if (textField.getText().isEmpty()) {
                label.setText(getErrorMessage(i));
                return;
            }
        }

        try {
            if (manager) {
                Category newCategory = new Category(texts[0], texts[1]);
                currentAdminCreate.create(newCategory);
                EcommerceApplication.showSuccessMessage("Category added successfully");
            } else {
                int id = Integer.parseInt(texts[2]);
                for (int i = 0; i <Database.products.size() ; i++) {
                    if (id==Database.products.get(i).getProductId()){label.setText("Id is already taken"); return;}

                }
                String name = texts[0];
                String description = texts[1];
                String category = texts[3];
                int stock = Integer.parseInt(texts[4]);
                double price = Double.parseDouble(texts[5]);
                String imageUrl = texts[6];
                Product newProduct = new Product(id, name, description, price, stock, category, imageUrl);
                currentAdminCreate.create(newProduct);
                EcommerceApplication.showSuccessMessage("Product added successfully");
            }
            EcommerceApplication.changeScene("Admin.fxml", (Stage) submit.getScene().getWindow());
        } catch (NumberFormatException e) {
            label.setText("Please enter valid numeric values for ID, Stock, Price, and Quantity.");
        }
    }


    private String getErrorMessage(int index) {
        switch (index) {
            case 0: return "Name can't be empty";
            case 1: return "Description can't be empty";
            case 2: return "ID can't be empty";
            case 3: return "Category can't be empty";
            case 4: return "Stock can't be empty";
            case 5: return "Price can't be empty";
            case 6: return "Image URL can't be empty";
            default: return "Unknown error";
        }
    }


    @FXML
    public void initialize() {
        for (int i = 0; i < Database.admins.size(); i++) {
            if (Database.admins.get(i).getIn() && "Manager".equals(Database.admins.get(i).getRole())) {
                manager = true;
            }
            if (Database.admins.get(i).getIn()) {
                currentAdminCreate = Database.admins.get(i);
            }
        }
        cp.setText(manager ? "Create Category:" : "Create Product:");
        double fieldStartY = 150;
        double fieldSpacing = 50;
        double windowWidth = root.getPrefWidth();

        if (manager) {
            addField(0, "Name:", windowWidth, fieldStartY, fieldSpacing);
            addField(1, "Description:", windowWidth, fieldStartY, fieldSpacing);
        } else {
            addField(0, "Name:", windowWidth, fieldStartY, fieldSpacing);
            addField(1, "Description:", windowWidth, fieldStartY, fieldSpacing);
            addField(2, "ID:", windowWidth, fieldStartY, fieldSpacing);
            addField(3, "Category:", windowWidth, fieldStartY, fieldSpacing);
            addField(4, "Stock:", windowWidth, fieldStartY, fieldSpacing);
            addField(5, "Price:", windowWidth, fieldStartY, fieldSpacing);
            addField(6, "Image URL:", windowWidth, fieldStartY, fieldSpacing);
        }
    }

    private void addField(int index, String labelText, double windowWidth, double startY, double spacing) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: white;"); // Set label text color to white
        label.setLayoutY(startY + index * spacing);

        TextField textField = new TextField();
        textField.setPromptText("Enter " + labelText.replace(":", "").toLowerCase());
        textField.setLayoutY(startY + index * spacing);
        textField.setStyle("-fx-background-color: white; -fx-font-size: 14px; -fx-padding: 5px;");
        textField.setPrefWidth(250);
        textField.setPrefHeight(30);
        double fieldWidth = 250;
        double labelWidth = 100;
        double totalWidth = labelWidth + fieldWidth + 10;
        double centerX = (windowWidth - totalWidth) / 2;
        label.setLayoutX(centerX);
        textField.setLayoutX(centerX + labelWidth + 10);
        textFields.add(textField);
        root.getChildren().addAll(label, textField);
    }

    @FXML
    public void backToAdmin() {
        try {
            EcommerceApplication.changeScene("Admin.fxml", (Stage) Backbutton.getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}