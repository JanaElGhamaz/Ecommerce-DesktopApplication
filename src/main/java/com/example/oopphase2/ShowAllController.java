package com.example.oopphase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowAllController {
    @FXML
    private Button profile;
    @FXML
    private Button Backbutton;
    @FXML
    private ListView<String> adminsListView;
    @FXML
    private ListView<String> customersListView;
    @FXML
    private ListView<String> productsListView;
    @FXML
    private ListView<String> categoriesListView;
    @FXML
    private ListView<String> ordersListView;

    @FXML
    public void initialize() {
        showAll();
    }

    public void showAll() {
        ObservableList<String> admins = convertToStringList(Database.admins);
        ObservableList<String> customers = convertToStringList(Database.customers);
        ObservableList<String> products = convertToStringList(Database.products);
        ObservableList<String> categories = convertToStringList(Database.categories);
        ObservableList<String> orders = convertToStringList(Database.orders);
        adminsListView.setItems(admins);
        customersListView.setItems(customers);
        productsListView.setItems(products);
        categoriesListView.setItems(categories);
        ordersListView.setItems(orders);
    }

    private <T> ObservableList<String> convertToStringList(Iterable<T> list) {
        ObservableList<String> stringList = FXCollections.observableArrayList();
        for (T item : list) {
            stringList.add(item.toString());
        }
        return stringList;
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