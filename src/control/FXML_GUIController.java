/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javafx.geometry.Point2D;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.AB_Model;
import model.Kugel;

/**
 *
 * @author Tom
 */
public class FXML_GUIController implements Initializable, Observer {

    @FXML
    private AnchorPane root;

    @FXML
    private MenuItem termButton;
    @FXML
    private Pane playPane;
    @FXML
    private Button anstossButton;
    @FXML
    private Slider groSlider;
    @FXML
    private Slider stoWinSlider;
    @FXML
    private Slider stoKraSlider;
    @FXML
    private Label groSliAnz;
    @FXML
    private Label stoWinSliAnz;
    @FXML
    private Label stoKraSliAnz;
    @FXML
    private GridPane kugelGrid;

    private AB_Model model;

    private ArrayList<Circle> circles = new ArrayList<Circle>();

    private ObservableList<String> materialien = FXCollections.observableArrayList("Standard", "Holz", "Eisen");
    
    private int index = 0;

    //Initialisiert den Controller. Erstellt ein model und fügt diesen Controller als Observer hinzu.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new AB_Model();
        model.addObserver(this);
        initSliders();
    }

    //initialisiert Slider. Setzt ihre maximalen und minimalen Werte und verbindet die Anzeigefelder.
    private void initSliders() {

        groSlider.setValue(5);
        groSlider.setMax(50);
        groSlider.setMin(1);
        groSliAnz.textProperty().bindBidirectional(groSlider.valueProperty(), NumberFormat.getNumberInstance());

        stoWinSlider.setValue(0);
        stoWinSlider.setMax(360);
        stoWinSlider.setMin(0);
        stoWinSliAnz.textProperty().bindBidirectional(stoWinSlider.valueProperty(), NumberFormat.getNumberInstance());

        stoKraSlider.setValue(10);
        stoKraSlider.setMax(100);
        stoKraSlider.setMin(1);
        stoKraSliAnz.textProperty().bindBidirectional(stoKraSlider.valueProperty(), NumberFormat.getNumberInstance());
    }

//Methode des Observer Modells. Momentan: Levelwechsel.
    @Override
    public void update(Observable AB_Model, Object obj) {

        circles.clear();
        Rectangle rect1 = new Rectangle(450, 110, 50, 200);
        rect1.setArcWidth(5);
        rect1.setArcHeight(5);
        rect1.setFill(Color.BLUE);
        
        //Erstellt einen Kreis für jede Kugel
        for (Kugel k : model.getCurrentSimulation().getKugeln()) {
            
            int posX = k.getPosX();
            int posY = k.getPosY();
            int rad = k.getRad();
            Circle c = new Circle(posX, posY, rad);
            circles.add(c);
            playPane.getChildren().add(c);
        }
        playPane.getChildren().add(rect1);
        
        
        //Gibt den Kreisen Farbe je nach ihrer Nummer
        int num = 0;
        for (Circle c : circles) {

            Circle cEins = null;

            if (num != 0) {
                cEins = new Circle(30);
            } else {
                //bindet den Radius der weißen Kugel an den Sliderwert
                c.radiusProperty().bind(groSlider.valueProperty());
            }

            switch (num) {
                case (0):
                    c.setFill(Color.WHITE);
                    break;
                case (1):
                    c.setFill(Color.RED);
                    cEins.setFill(Color.RED);
                    break;
                case (2):
                    c.setFill(Color.AQUA);
                    cEins.setFill(Color.AQUA);
                    break;
                case (3):
                    c.setFill(Color.GREENYELLOW);
                    cEins.setFill(Color.GREENYELLOW);
                    break;
                case (4):
                    c.setFill(Color.YELLOW);
                    cEins.setFill(Color.YELLOW);
                    break;
                case (5):
                    c.setFill(Color.PINK);
                    cEins.setFill(Color.PINK);
                    
                    break;
                    
            }

            Label beschreibung = new Label("Material");
            ComboBox cbx = new ComboBox(materialien);

            if (num != 0) {
                kugelGrid.add(cEins, 0, (num - 1) * 2, 1, 2);
                kugelGrid.add(beschreibung, 1, (num - 1) * 2);
                kugelGrid.add(cbx, 1, (num - 1) * 2 + 1);
            }
            num++;
        }
        
        anstossButton.setDisable(false);
        groSlider.setDisable(false);
        stoKraSlider.setDisable(false);
        stoWinSlider.setDisable(false);
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
                //move(model.getCurrentSimulation().getStosskugel());
                for (index=0;index<=5;index++){
                move(model.getCurrentSimulation().getKugeln().get(index));
                }
            }
        };
        KeyFrame f = new KeyFrame(Duration.millis(1.66), handler);
        Timeline timer = new Timeline(f);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        anstossButton.setDisable(true);
        groSlider.setDisable(true);
        stoKraSlider.setDisable(true);
        stoWinSlider.setDisable(true);
    }

    //bewegt die Kugel und updated die beiden kreis positionen.
    public void move(Kugel k) {

        Point2D anstoss = new Point2D(1, 0);
        double radi = getRadi(index);
        double stoWi = getStoWi(index);
        double stoKra = getStoKra(index);
        k.bewegen(anstoss,radi,stoWi,stoKra,index);
        circles.get(index).setCenterX(k.getPosition(index).getX());
        circles.get(index).setCenterY(k.getPosition(index).getY());

    }

    //beendet das Programm
    @FXML
    private void terminieren(ActionEvent event) {
        System.out.println("TERMINIERT!");
        Platform.exit();
    }

    //öffnet den LoaderF
    @FXML
    private void loadFile(ActionEvent event) {
        System.out.println("loadfile");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lade deinen Spielstand");
        fileChooser.showOpenDialog(root.getScene().getWindow());
    }
    
    public final double getRadi(int x) { 
            if (x == 0){
            return groSlider.valueProperty().get();  
            }
            if (x == 1){
            return (double) (Integer)(model.getCurrentSimulation().getRadValue().get(1));
            }
            if (x == 2){
            return (double) (Integer)(model.getCurrentSimulation().getRadValue().get(2));
            }
            if (x == 3){
            return (double) (Integer)(model.getCurrentSimulation().getRadValue().get(3));
            }
            if (x == 4){
            return (double) (Integer)(model.getCurrentSimulation().getRadValue().get(4));
            }
            if (x == 5){
            return (double) (Integer)(model.getCurrentSimulation().getRadValue().get(5));
            }
            else{
                return 0;
            }
            
    } 
    
    public final double getStoWi(int x) { 
            if (x == 0){
            return stoWinSlider.valueProperty().get();
            }
            else{
                return 0;
            }
    } 
     public final double getStoKra(int x) { 
            if (x == 0){
            return stoKraSlider.valueProperty().get();  
            }
            else{
                return 0;
            }
    } 

    //öffnet den Saver
    /*
    private void saveFile(ActionEvent event) {
        System.out.println("saveFile");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Speichere deinen Spielstand");
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
    }
     */
}
