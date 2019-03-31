/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faa;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author iamrehman
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private JFXButton login;
    @FXML
    private JFXTextField idLogin;
    @FXML
    private JFXPasswordField password;
    @FXML
    private AnchorPane ImagePane;
    @FXML
    private ImageView image;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginHandler(ActionEvent event) throws IOException {
         if(true || password.getText().equals("rehman") && idLogin.getText().equals("rehman")){

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            /* first close this window */
            Stage st = (Stage) login.getScene().getWindow();
            // do what you have to do
            st.close();
     
            Stage stage = new Stage();
            stage.hide();
            stage.setScene(new Scene(root1)); 
            stage.show();
        }
        else{
            
            System.out.println("UI.FXMLDocumentController.loginHandler()");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("warning.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            /* first close this window */
  

            Stage stage = new Stage();

            stage.setScene(new Scene(root1)); 
            stage.show();
        }
    }
    
}
