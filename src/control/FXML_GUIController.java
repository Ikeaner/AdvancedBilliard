/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javafx.geometry.Point2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.AB_Model;
import model.Kugel;

/**
 *
 * @author Tom
 */
public class FXML_GUIController implements Initializable, Observer {

    @FXML
    private MenuItem termButton;
    @FXML
    private Pane playPane;
    @FXML
    private Button anstossButton;

    private AB_Model model;

    private ArrayList<Circle> circles = new ArrayList<Circle>();

    //Initialisiert den Controller. Erstellt ein model und fügt diesen Controller als Observer hinzu.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new AB_Model();
        model.addObserver(this);
    }

    //Methode des Observer Modells. Momentan: Levelwechsel.
    @Override
    public void update(Observable AB_Model, Object obj) {

        circles.clear();

        //Erstellt einen Kreis für jede Kugel
        for (Kugel k : model.getCurrentSimulation().getKugeln()) {
            int posX = k.getPosX();
            int posY = k.getPosY();
            int rad = k.getRad();
            Circle c = new Circle(posX, posY, rad);
            circles.add(c);
            playPane.getChildren().add(c);
        }

        //Gibt den Kreisen Farbe je nach ihrer Nummer
        int num = 0;
        for (Circle c : circles) {
            switch (num) {
                case (0):
                    c.setFill(Color.WHITE);
                    break;
                case (1):
                    c.setFill(Color.RED);
                    break;
                case (2):
                    c.setFill(Color.AQUA);
                    break;
            }
            num++;
        }

        anstossButton.setDisable(false);
    }

    //Button Aktionen für Simulationauswahl
    @FXML
    private void sim1Auswaehlen(ActionEvent event) {
        model.setCurrentSimulation(0);
    }
    @FXML
    private void sim2Auswaehlen(ActionEvent event) {
        model.setCurrentSimulation(1);
    }
    @FXML
    private void sim3Auswaehlen(ActionEvent event) {
        model.setCurrentSimulation(2);
    }

    //Anstoß. Startet einen Timer und führt jedes Frame move() aus. 
    @FXML
    private void anstoss(ActionEvent event) {

        System.out.println("Anstoß!");

        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                move(model.getCurrentSimulation().getStosskugel());
            }
        };
        KeyFrame f = new KeyFrame(Duration.millis(16.6), handler);
        Timeline timer = new Timeline(f);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        anstossButton.setDisable(true);
    }

    //bewegt die Kugel und updated die beiden kreis positionen.
    public void move(Kugel k) {

        Point2D anstoss = new Point2D(1, 0);

        k.bewegen(anstoss);
        circles.get(0).setCenterX(k.getPosition().getX());
        circles.get(0).setCenterY(k.getPosition().getY());

    }

    //beendet das Programm
    @FXML
    private void terminieren(ActionEvent event) {
        System.out.println("TERMINIERT!");
        Platform.exit();
    }
}
