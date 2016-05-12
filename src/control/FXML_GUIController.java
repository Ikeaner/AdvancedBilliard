/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Tom
 */
public class FXML_GUIController implements Initializable {

    @FXML
    private MenuItem termButton;
    
    @FXML
    private void anstoss(ActionEvent event) {
        System.out.println("Anstoß versucht!");
    }
    
    @FXML
    private void terminieren(ActionEvent event) {
        System.out.println("TERMINIERT!");
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
