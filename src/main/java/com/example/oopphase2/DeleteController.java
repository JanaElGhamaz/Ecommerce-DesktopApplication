package com.example.oopphase2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class DeleteController {
    @FXML
    private Button Backbutton;
    @FXML
    private Label cp;
    @FXML
    private Button delete;
    @FXML
    private TextField deleteTextField;
    @FXML
    private Label label;

    private Admin currentAdmindelete;
    private boolean deleteBo;

    @FXML
    void delete(MouseEvent event) throws IOException {
        for (int i = 0; i <Database.admins.size() ; i++) {
            if (Database.admins.get(i).getIn()){
                currentAdmindelete=Database.admins.get(i);
            }
        }
        if (deleteTextField.getText().isEmpty()){
            label.setText("Id can't be empty");
        }else{
            try {
                deleteBo =currentAdmindelete.delete(Integer.parseInt(deleteTextField.getText()));
            }catch (NumberFormatException e){
                label.setText("enetr a valid Id");
            }
            if (deleteBo){
                EcommerceApplication.showSuccessMessage("item deleted successfully");
                EcommerceApplication.changeScene("Admin.fxml",(Stage) delete.getScene().getWindow());
            }else{
                label.setText("item not found");
            }

        }

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