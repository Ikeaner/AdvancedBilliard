/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Kugel;

/**
 *
 * @author Tom
 */
public class FXML_GUIController implements Initializable {

    @FXML
    private MenuItem termButton;
    @FXML
    private Pane playPane;

    private Circle k0;
    private Kugel kug0;

    @FXML
    private void anstoss(ActionEvent event) 
    {
        System.out.println("Anstoß versucht!");
        
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {     
                move(kug0);
            }
        };
        KeyFrame f = new KeyFrame(Duration.millis(16.6), handler);
        Timeline timer = new Timeline(f);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public void move(Kugel k) {
        for (double t = 0; t < 10; t++) {
            k.bewegen(t);
            k0.setCenterX(k.getPosition().getX());
            k0.setCenterY(k.getPosition().getY());
        }
    }

    @FXML
    private void terminieren(ActionEvent event) 
    {
        System.out.println("TERMINIERT!");
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        kug0 = new Kugel(50, 50, 20);
        k0 = new Circle(50.0, 50.0, 20.0);
        playPane.getChildren().add(k0);

    }
}
