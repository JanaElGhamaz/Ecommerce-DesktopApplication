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

public class updateController {
    @FXML
    private Button Backbutton;
    @FXML
    private AnchorPane root;
    @FXML
    private Label label;
    @FXML
    private Button update;
    @FXML
    private Label cp;

    private boolean manager = false;
    private Admin currentAdminUpdate;
    private List<TextField> textFields = new ArrayList<>();
    private Category updatedCategory;
    private Product updatedProduct;
    private boolean updated = false;
    private boolean empty = false;

    @FXML
    void update(MouseEvent event) throws IOException {
        String[] texts = new String[textFields.size()];
        int id = 0;
        if (!textFields.get(0).getText().isEmpty()) {
            try {
                id = Integer.parseInt(textFields.get(0).getText());
            } catch (NumberFormatException e) {
                label.setText("Please enter valid numeric values for ID, Stock, and Price.");
            }
        } else {
            label.setText("ID can't be empty");
            return;
        }

        if (manager) {
            for (int i = 0; i < Database.categories.size(); i++) {
                if (id == Database.categories.get(i).getId()) {
                    updatedCategory = Database.categories.get(i);
                    updated = true;
                }
            }
        } else {
            for (int i = 0; i < Database.products.size(); i++) {
                if (id == Database.products.get(i).getProductId()) {
                    updatedProduct = Database.products.get(i);
                    updated = true;
                }
            }
        }
        if (!updated) {
            label.setText("Item not found");
            return;
        }

        for (int i = 1; i < textFields.size(); i++) {
            if (!textFields.get(i).getText().isEmpty()) {
                empty = true;
                texts[i] = textFields.get(i).getText();
                boolean found = updateItems(i, texts[i]);
                if (!found) {
                    label.setText("Category not found");
                    return;
                }
            }
        }
        if (!empty) {
            label.setText("Please enter things to be changed");
            return;
        }
        if (manager) {
            EcommerceApplication.showSuccessMessage("Category updated successfully");
        } else {
            EcommerceApplication.showSuccessMessage("Product updated successfully");
        }
        EcommerceApplication.changeScene("Admin.fxml", (Stage) update.getScene().getWindow());
    }

    boolean updateItems(int i, String text) {
        if (manager) {
            switch (i) {
                case 1: updatedCategory.setName(text); return true;
                case 2: updatedCategory.setDescription(text); return true;
            }
        } else {
            try {
                switch (i) {
                    case 1: updatedProduct.setName(text); return true;
                    case 2: updatedProduct.setDescription(text); return true;
                    case 3: updatedProduct.setProductId(Integer.parseInt(text)); return true;
                    case 7: return updatedProduct.setCategory(text);
                    case 5: updatedProduct.setStock(Integer.parseInt(text)); return true;
                    case 6: updatedProduct.setPrice(Double.parseDouble(text)); return true;
                    case 4: updatedProduct.setImagePath(text); return true;
                }
            } catch (NumberFormatException e) {
                label.setText("Please enter valid numeric values for ID, Stock, and Price.");
            }
        }
        return false;
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < Database.admins.size(); i++) {
            if (Database.admins.get(i).getIn() && "Manager".equals(Database.admins.get(i).getRole())) {
                manager = true;
            }
            if (Database.admins.get(i).getIn()) {
                currentAdminUpdate = Database.admins.get(i);
            }
        }
        cp.setText(manager ? "Update Category:" : "Update Product:");

        double fieldStartY = 150;
        double fieldSpacing = 50;
        double windowWidth = root.getPrefWidth();

        if (manager) {
            addField(0, "ID of item to be updated", windowWidth, fieldStartY, fieldSpacing);
            addField(1, "Name:", windowWidth, fieldStartY, fieldSpacing);
            addField(2, "Description:", windowWidth, fieldStartY, fieldSpacing);
        } else {
            addField(0, "ID of item to be updated", windowWidth, fieldStartY, fieldSpacing);
            addField(1, "Name:", windowWidth, fieldStartY, fieldSpacing);
            addField(2, "Description:", windowWidth, fieldStartY, fieldSpacing);
            addField(3, "ID:", windowWidth, fieldStartY, fieldSpacing);
            addField(4, "Image URL:", windowWidth, fieldStartY, fieldSpacing);
            addField(5, "Stock:", windowWidth, fieldStartY, fieldSpacing);
            addField(6, "Price:", windowWidth, fieldStartY, fieldSpacing);
            addField(7, "Category:", windowWidth, fieldStartY, fieldSpacing);
        }
    }

    private void addField(int index, String labelText, double windowWidth, double startY, double spacing) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-");
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
