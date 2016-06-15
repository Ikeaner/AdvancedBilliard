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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.AB_Model;
import model.Kugel;
import model.Loch;
import model.Objekt;

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

    private Timeline timer;
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
    private ArrayList<Circle> holes = new ArrayList<Circle>();
    private ArrayList<ChoiceBox> cbxs = new ArrayList<ChoiceBox>();

    private ObservableList<String> materialien = FXCollections.observableArrayList("Standard", "Holz", "Glas", "Keramik", "Gummi");

    @FXML
    private MenuItem sim1;
    @FXML
    private MenuItem sim2;
    @FXML
    private MenuItem sim3;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label simNameLabel;
    @FXML
    private ScrollPane kugelEinsScrollPane;
    @FXML
    private GridPane hindernisGrid;
    @FXML
    private TitledPane einstellungsFenster;
    @FXML
    private ChoiceBox anstossKugelMat;
    @FXML
    private Button resetButton;
    @FXML
    private Label versucheLabel;

    //Initialisiert den Controller. Erstellt ein model und fügt diesen Controller als Observer hinzu.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new AB_Model();
        model.addObserver(this);
        initSliders();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
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
        stoKraSlider.setMax(20);
        stoKraSlider.setMin(1);
        stoKraSliAnz.textProperty().bindBidirectional(stoKraSlider.valueProperty(), NumberFormat.getNumberInstance());
    }

//Methode des Observer Modells. Momentan: Levelwechsel.
    @Override
    public void update(Observable ABModel, Object obj) {

        //überprüft ob ein neues Level geladen wurde oder nicht
        if (!model.getCurrentSimulation().toString().equals(model.getPreviousSimulation().toString())) {
            levelLaden();
        }
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
        if (anstossButton.getText().equals("Anstoß!")) {
            model.getCurrentSimulation().getKugeln().get(0).setRad(groSlider.valueProperty().intValue());
            System.out.println("Anstoß!");

            EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //move(model.getCurrentSimulation().getStosskugel());

                    for (Kugel k : model.getCurrentSimulation().getKugeln()) {
                        move(k);
                    }
                    model.getCurrentSimulation().checkLöcher();
                    int i = model.getCurrentSimulation().checkStatus();

                    if (i == 1) {
                        gewonnen();
                    } else if (i == 2) {
                        falschesLoch();
                    }
                }
            };
            KeyFrame f = new KeyFrame(Duration.millis(1.66), handler);
            timer = new Timeline(f);
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.playFromStart();

            groSlider.setDisable(true);
            stoKraSlider.setDisable(true);
            stoWinSlider.setDisable(true);
            sim1.setDisable(true);
            sim2.setDisable(true);
            sim3.setDisable(true);
            for (ChoiceBox c : cbxs) {
                c.setDisable(true);
            }

            resetButton.setText("Abbrechen");
            resetButton.setDisable(false);
            anstossButton.setText("Anhalten");
        } else if (anstossButton.getText().equals("Anhalten")) {
            timer.pause();
            anstossButton.setText("Weiterspielen");
        } else if (anstossButton.getText().equals("Weiterspielen")) {
            timer.play();
            anstossButton.setText("Anhalten");
        }
    }

    public void gewonnen() {
        timer.stop();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Level " + model.getCurrentSimulation().toString() + " geschafft!");
        alert.setHeaderText(null);
        alert.setContentText("Sie haben das Level geschafft! Hierfür haben Sie " + " Versuche benötigt.");

        alert.show();
    }

    public void falschesLoch() {
        timer.stop();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Level " + model.getCurrentSimulation().toString() + " nicht geschafft...");
        alert.setHeaderText(null);
        alert.setContentText("Eine Kugel ist in ein falsches Loch gefallen. Achten Sie darauf, dass Kugeln in gleich große Löcher fallen!");

        alert.show();

        model.getCurrentSimulation().reload();
        timer.stop();
        levelLaden();
        anstossButton.setText("Anstoß!");
        resetButton.setText("Zurücksetzen");
        model.getCurrentSimulation().plusEinsVersuch();
        versucheLabel.setText("Versuche: " + Integer.toString(model.getCurrentSimulation().getVersuche()));
        sim1.setDisable(false);
        sim2.setDisable(false);
        sim3.setDisable(false);
    }

    @FXML
    private void reset() {
        if (resetButton.getText().equals("Zurücksetzen")) {
            levelLaden();
        } else if (resetButton.getText().equals("Abbrechen")) {
            model.getCurrentSimulation().reload();
            timer.stop();
            levelLaden();
            anstossButton.setText("Anstoß!");
            resetButton.setText("Zurücksetzen");
            model.getCurrentSimulation().plusEinsVersuch();
            versucheLabel.setText("Versuche: " + Integer.toString(model.getCurrentSimulation().getVersuche()));

            sim1.setDisable(false);
            sim2.setDisable(false);
            sim3.setDisable(false);
        }
    }

    //bewegt die Kugel und updated die beiden kreis positionen.
    public void move(Kugel k) {

        int index = model.getCurrentSimulation().getKugeln().indexOf(k);
        Point2D anstoss = new Point2D(1, 0);
        double radi = getRadi(index);
        double stoWi = getStoWi(index);
        double stoKra = getStoKra(index);
        double radiSlider = getRadi(0);
        k.bewegen(anstoss, radi, stoWi, stoKra, radiSlider);
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

    @FXML
    private void about(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Über...");
        alert.setHeaderText("Über Advanced Billiard");
        alert.setContentText("Version: 1.0, Programm von Tom Quinders, Alexander Dmitriev, Jaqueline Timmermann und Markus Roth");

        alert.showAndWait();
    }

    private void levelLaden() {
        System.out.println("level geladen");

        versucheLabel.setText("Versuche: " + Integer.toString(model.getCurrentSimulation().getVersuche()));

        System.out.println("Versuche: " + Integer.toString(model.getCurrentSimulation().getVersuche()));

        model.setPreviousSimulation(model.getCurrentSimulation());

        circles.clear();
        rectangles.clear();
        holes.clear();
        cbxs.clear();
        playPane.getChildren().clear();
        kugelGrid.getChildren().clear();
        hindernisGrid.getChildren().clear();

        simNameLabel.setText("Simulation " + model.getCurrentSimulation().toString());
        einstellungsFenster.setText("Simulation " + model.getCurrentSimulation().toString() + " Einstellungen");

        for (Loch l : model.getCurrentSimulation().getLöcher()) {

            int posX = l.getPosX();
            int posY = l.getPosY();
            int rad = l.getRad();
            Circle c = new Circle(posX, posY, rad);

            holes.add(c);
            playPane.getChildren().add(c);
        }

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
            model.getCurrentSimulation().getKugeln().get(circles.indexOf(c)).setColor((Color) c.getFill());

            kugelGrid.setVgap(5);

            if (num != 0) {
                Label beschreibung = new Label("Material");
                ChoiceBox cbx = new ChoiceBox(materialien);
                cbx.setValue("Standard");
                cbx.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        System.out.println(new_value.toString());

                        switch (new_value.intValue()) {
                            //Standard
                            case 0:
                                c.setFill(model.getCurrentSimulation().getKugeln().get(circles.indexOf(c)).getColor());
                                model.getCurrentSimulation().setMat(0.35, 1, circles.indexOf(c));
                                break;
                            //Holz
                            case 1:
                                c.setFill(Color.BROWN);
                                model.getCurrentSimulation().setMat(0.025, 0.8, circles.indexOf(c));
                                break;
                            //Glas
                            case 2:
                                c.setFill(Color.LIGHTBLUE);
                                model.getCurrentSimulation().setMat(0.015, 2.55, circles.indexOf(c));
                                break;
                            //Keramik
                            case 3:
                                c.setFill(Color.LIGHTGREY);
                                model.getCurrentSimulation().setMat(0.030, 3.0, circles.indexOf(c));
                                break;
                            //Gummi
                            case 4:
                                c.setFill(Color.PINK);
                                model.getCurrentSimulation().setMat(0.035, 0.94, circles.indexOf(c));
                                break;
                        }
                        model.getCurrentSimulation().getKugeln().get(circles.indexOf(c)).setMaterial(new_value.intValue());
                        model.getCurrentSimulation().getKugeln().get(circles.indexOf(c)).calcMass(circles.indexOf(c), 0);
                    }
                });

                cbxs.add(cbx);

                kugelGrid.add(cEins, 0, (num - 1) * 2, 1, 2);
                kugelGrid.add(beschreibung, 1, (num - 1) * 2);
                kugelGrid.add(cbx, 1, (num - 1) * 2 + 1);
            }
            num++;
        }

        //enabled den Material Picker für Anstosskugel
        Circle anstossCircle = circles.get(0);
        anstossKugelMat.setDisable(false);
        anstossKugelMat.setItems(materialien);
        anstossKugelMat.setValue("Standard");
        anstossKugelMat.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) {
                System.out.println(new_value.toString());

                switch (new_value.intValue()) {
                    //Standard
                    case 0:
                        anstossCircle.setFill(model.getCurrentSimulation().getKugeln().get(0).getColor());
                        model.getCurrentSimulation().setMat(0.35, 1, 0);
                        break;
                    //Holz
                    case 1:
                        anstossCircle.setFill(Color.BROWN);
                        model.getCurrentSimulation().setMat(0.025, 0.8, 0);
                        break;
                    //Glas
                    case 2:
                        anstossCircle.setFill(Color.LIGHTBLUE);
                        model.getCurrentSimulation().setMat(0.015, 2.55, 0);
                        break;
                    //Keramik
                    case 3:
                        anstossCircle.setFill(Color.LIGHTGREY);
                        model.getCurrentSimulation().setMat(0.030, 3.0, 0);
                        break;
                    //Gummi
                    case 4:
                        anstossCircle.setFill(Color.PINK);
                        model.getCurrentSimulation().setMat(0.035, 0.94, 0);
                        break;
                }
                model.getCurrentSimulation().getKugeln().get(0).setMaterial(new_value.intValue());

                double radiSlider = getRadi(0);
                model.getCurrentSimulation().getKugeln().get(0).calcMass(0, radiSlider);
            }
        });
        cbxs.add(anstossKugelMat);

        //Erstellt ein Rechteck für jedes Hindernis
        for (Objekt o : model.getCurrentSimulation().getHindernisse()) {

            int posX = o.getPosX();
            int posY = o.getPosY();
            int width = o.getDimX();
            int height = o.getDimY();
            Rectangle r = new Rectangle(posX, posY, width, height);
            r.getTransforms().add(new Rotate(45, 0, 0)); //Drehen Drehachse linke Ecke
            r.setArcHeight(5);
            r.setArcWidth(5);
            rectangles.add(r);
            playPane.getChildren().add(r);
        }

        //Gibt den Rechtecken die Holz Farbe
        int num2 = 0;
        hindernisGrid.setVgap(5);
        for (Rectangle r : rectangles) {

            Rectangle rEins = new Rectangle(75, 50);
            rEins.setFill(Color.BROWN);

            Label beschreibung = new Label("Material");
            ChoiceBox cbx = new ChoiceBox(materialien);

            cbx.setValue("Holz");
            r.setFill(Color.BROWN);
            model.getCurrentSimulation().getHindernisse().get(rectangles.indexOf(r)).setMaterial(1);

            cbx.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue ov, Number value, Number new_value) {
                    System.out.println(new_value.toString());

                    switch (new_value.intValue()) {
                        case 0:
                            r.setFill(Color.WHITE);
                            break;
                        case 1:
                            r.setFill(Color.BROWN);
                            break;
                        case 2:
                            r.setFill(Color.GRAY);
                            break;
                    }
                    model.getCurrentSimulation().getHindernisse().get(rectangles.indexOf(r)).setMaterial(new_value.intValue());

                }
            });

            cbxs.add(cbx);

            hindernisGrid.add(rEins, 0, num2 * 2, 1, 2);
            hindernisGrid.add(beschreibung, 1, num2 * 2);
            hindernisGrid.add(cbx, 1, (num2 * 2) + 1);

            num2++;
        }

        anstossButton.setDisable(false);
        groSlider.setDisable(false);
        stoKraSlider.setDisable(false);
        stoWinSlider.setDisable(false);
    }

    public final double getRadi() {

        return groSlider.valueProperty().get();
    }

    public final double getStoWi() {

        return stoWinSlider.valueProperty().get();
    }

    public final double getStoKra() {

        return stoKraSlider.valueProperty().get();
    }

    public final double getRadi(int x) {
        if (x == 0) {
            return groSlider.valueProperty().get();
        } else {
            return model.getCurrentSimulation().getKugeln().get(x).getRad();
        }

    }

    public final double getStoWi(int x) {
        if (x == 0) {
            return stoWinSlider.valueProperty().get();
        } else {
            return 0;
        }
    }

    public final double getStoKra(int x) {
        if (x == 0) {
            return stoKraSlider.valueProperty().get();
        } else {
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
