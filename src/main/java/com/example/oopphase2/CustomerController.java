package com.example.oopphase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class CustomerController {
    @FXML
    private Label stockerror;
    @FXML
    private Button profile;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> imageColumn;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, TextField> quantityColumn;
    @FXML
    private TableColumn<Product, Void> addToCartColumn;

    private ObservableList<Product> productList;

    private Customer currentCustomer;

    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
        System.out.println("Logged-in customer: " + customer.getUsername());
    }

    @FXML
    public void initialize() {
        productList = FXCollections.observableArrayList(Database.products);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));


        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
            {
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null || imagePath.isEmpty()) {
                    setGraphic(null);
                } else {
                    javafx.scene.image.Image image = new javafx.scene.image.Image("file:" + imagePath, true);
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
            }
        });

        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        quantityColumn.setCellFactory(column -> new TableCell<>() {
            private final TextField quantityField = new TextField();
            {
                quantityField.setPromptText("Qty");
                quantityField.setPrefWidth(50);
//                quantityField.prefWidthProperty().bind(imageColumn.widthProperty().multiply(0.1));
                quantityField.setAlignment(Pos.CENTER);
            }
            @Override
            protected void updateItem(TextField item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(quantityField);
                }
            }
        });

        addToCartColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Product, Void> call(TableColumn<Product, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Add to Cart");

                    {
                        btn.setOnAction(event -> {
                            Product product = getTableRow().getItem();
                            TableRow<Product> currentRow = getTableRow();
                            TableCell<Product, TextField> quantityCell = (TableCell<Product, TextField>) currentRow.getChildrenUnmodifiable().get(6);
                            TextField quantityField = (TextField) quantityCell.getGraphic();
                            handleAddToCart(product, quantityField.getText());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
        productTable.setItems(productList);
    }

    public void handleAddToCart(Product product, String quantityStr) {
        if (currentCustomer != null && product != null) {
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity > product.getStock()) {
                    stockerror.setText("Not enough stock available for " + product.getName());
                    stockerror.setVisible(true);
                } else {
                    if (currentCustomer.getCart().addToCart(product.getProductId(), quantityStr)) {
                        stockerror.setVisible(false);
                    } else {
                        System.out.println("Failed to add " + product.getName() + " to cart.");
                    }
                }
            } catch (NumberFormatException e) {
                stockerror.setText("Invalid quantity. Please enter a numeric value.");
                stockerror.setVisible(true);
            }
        }
    }

    @FXML
    public void Cart(MouseEvent event) throws IOException {
        changeToCart("Cart.fxml", currentCustomer);
    }

    private void changeToCart(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        CartController cartController = loader.getController();
        cartController.setCustomer(customer);
        Stage stage = (Stage) productTable.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    public void Orders(MouseEvent event) throws IOException {
        changeToOrders("Orders.fxml", currentCustomer);
    }

    private void changeToOrders(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        OrdersController ordersController = loader.getController();
        ordersController.setCurrentCustomer(customer);
        Stage stage = (Stage) productTable.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    @FXML
    public void Profile(MouseEvent event) throws IOException {
        changeToProfile("Profile.fxml", currentCustomer);
    }
    private void changeToProfile(String fxmlFile, Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        ProfileController profileController = loader.getController();
        profileController.setCurrentCustomer(customer);
        Stage stage = (Stage) productTable.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}