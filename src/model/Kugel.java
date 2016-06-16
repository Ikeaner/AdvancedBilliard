/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 *
 * @author Tom
 */
public class Kugel {

    private int rad;
    private int posX;
    private int posY;
    int colBall;
    private double[] geschwindigkeit = new double[6];
    private static Point2D[] richtung = new Point2D[6];
    private static Point2D[] position = new Point2D[6];
    private double[] xx = new double[6];
    private double[] yy = new double[6];
    private double[] rollReib = new double[6];
    private static double[] masse = new double[6];
    private static double[] radius = new double[6];
    Kollision col = new Kollision();
    public static boolean bereitsBerechnet = false;
    private Point2D noChange = new Point2D(0, 0);
    private int material;
    private Simulation sim;
    private Color color;
    private int index;
    private boolean isInHole = false;
    private boolean isInRightHole = false;

    public Kugel(int x, int y, int r, Simulation s, int index,double mat,double matV2) {
        //die aus der Simulation weitergegebenen Werte werden in Variablen und Arrays abgespeichert die mit einem Index versehen sind um diese den Kugeln zu ordnen zu können
        rad = r;
        posX = x;
        posY = y;
        this.index = index;
        position[index] = new Point2D(posX, posY);
        richtung[index] = new Point2D(0, 0);
        radius[index] = r;
        sim = s;
        //berechnet die Reibungskraft und die Masse der Kugeln anhand der weitergegebenen Daten und speichert diese in mit Index versehenen Arrays ab
        rollReib[index] = Reibung(r,mat,matV2);
        masse[index] = Masse(r,matV2);
    }

    public boolean isInHole() {
        return isInHole;
    }

    public void setIsInHole(boolean isInHole) {
        this.isInHole = isInHole;
    }

    public boolean isInRightHole() {
        return isInRightHole;
    }

    public void setIsInRightHole(boolean isInRightHole) {
        this.isInRightHole = isInRightHole;
    }

    public void setXx(int i, double d) {
        xx[i] = d;
    }

    public void setYy(int i, double d) {
        yy[i] = d;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        //0 = Standard
        //1 = Holz
        //2 = Eisen
        this.material = material;
        System.out.println(Integer.toString(material));
    }

    public void setPosition(int i, double x, double y) {
        position[i] = new Point2D(x, y);
    }

    public void bewegen(Point2D anstoss, double radi, double stoWi, double stoKra, double radiSlider) {
        //in dieser Methode werden die Anfangswerte für die Kugeln festgelegt wie z.B. deren Anfangsgeschwindigkeit, der Winkel in der sie angestoßen werden usw.
        //außerdem wird dies nur ein mal am Anfang ausgeführt um im Spielverlauf die dann veränderten Werte nicht zu verfälsche (bereitsBerechnet)
        if (bereitsBerechnet == false) {
            stossWinKraft(stoWi, stoKra, radiSlider,sim.getMatV2(0));
        }
        //in diesen doppelten Schleifen werden die Kugel Kollisionen abgefragt. Hierzu läuft er die Menge der Kugeln mit einem Index durch
        //Beispiel: Kugel mit index 0 wird auf kollision mit Kugel index 1, dann 2, dann 3 etc geprüft
        //Dann wird Kugel mit dem index 1 auf Kollision mit Kugeln 0,2,3 etc geprüft bis jede Mögliche Kollision zwischen den verschiedenen Kugeln abgefragt ist
        thisLoop:
        for (int i = 0; i < sim.getKugeln().size(); i++) {
            for (int a = 0; a < sim.getKugeln().size(); a++) {
                if (a != i) {
                    //Die Kollision prüft bei jedem Aufruf 2 Kugeln zueinander, die mit dem Index der ersten Schleife "i" und der zweiten "a" 
                    //Und gibt deren werte wie Position, bewegungsrichtung, radius, und masse an die Kollisionsklasse weiter wo dann die berechnungen stattfinden
                    //aus der Kollisionsklasse wird ein 4 Einträge langes double array zurückggeben dessen 0. und 1. index die neue x und y Richtung der ersten Kugel
                    //und 2. und 3. index die der zweiten Kugel angeben
                    double ablenkung[] = col.checkKollision(position[i].getX(), position[i].getY(), radius[i], position[a].getX(), position[a].getY(), radius[a], xx[i], yy[i], xx[a], yy[a], i, a, masse[i], masse[a]);
                    if (ablenkung[0] != 0) {
                        geschwindigkeit[i] = geschwindigkeit[i] - (geschwindigkeit[i]*0.1);
                        geschwindigkeit[a] = geschwindigkeit[i];
                        xx[i] = ablenkung[0];
                        yy[i] = ablenkung[1];
                        xx[a] = ablenkung[2] * -1;
                        yy[a] = ablenkung[3] * -1;
                        //wird eine Kollision entdeckt wird die äußere Schleife unterbrochen und in diesem Frame keine weitere Kollision abgefragt da sonst die zuerst entdeckte überschrieben wird
                        break thisLoop;

                    }
                }
            }
        }
        //Abfrage für Hindernisse (Rechteck) berechnet Kollision der aktuellen Kugel mit dem Hindernis
        for (int i = 0; i < sim.getKugeln().size(); i++) {
            for (Hindernisse h:sim.getHindernisse()){
        int coll = col.rectCol(position[i].getX(), position[i].getY(), radius[i],h.getPosX(),h.getPosY(),h.getDimX(),h.getDimY());
        if (coll == 1){
        yy[i] = yy[i] *-1;
        break;
        }
        if (coll == 2){
        xx[i] = xx[i] *-1;
        break;
        }
            }
        }
        
        
        //hier wird die Kollision mit dem Rand für jede einzelne Kugel geprüft. Ist diese vorhanden wird die Kugel gleich den eintrittswinkel abgelenkt
        for (int i = 0; i < sim.getKugeln().size(); i++) {
            if (position[i].getY() > 480 - radius[i] && geschwindigkeit[i] > 0 && yy[i] > 0) {
                yy[i] = yy[i] * -1;
                 geschwindigkeit[i] = geschwindigkeit[i] - (geschwindigkeit[i]*0.1);
                //System.out.println("Unten bumm");
            }
            if (position[i].getY() - radius[i] <= 20 && geschwindigkeit[i] > 0 && yy[i] < 0) {
                yy[i] = yy[i] * -1;
                 geschwindigkeit[i] = geschwindigkeit[i]- (geschwindigkeit[i]*0.1);
                //System.out.println("Oben bumm");
            }
            if (position[i].getX() > 730 - radius[i] && geschwindigkeit[i] > 0 && xx[i] > 0) {
                xx[i] = xx[i] * -1;
                 geschwindigkeit[i] = geschwindigkeit[i]- (geschwindigkeit[i]*0.1);
                //System.out.println("Rechts bumm");
            }
            if (position[i].getX() - radius[i] <= 20 && geschwindigkeit[i] > 0 && xx[i] < 0) {
                xx[i] = xx[i] * -1;
                geschwindigkeit[i] = geschwindigkeit[i]- (geschwindigkeit[i]*0.1);
                //System.out.println("Links bumm");
            }
            if (position[i].getX() - radius[i] > 1300 && geschwindigkeit[i] > 0 && xx[i] < 0) {
                xx[i] = 0;
                yy[i] = 0;
                //System.out.println("Rechts bumm");
            }
        }
        //hier findet ein Teil der Berechnung der Bewegung statt. Hierzu wird die Masse der Kugel und die Reibungskraft in Betracht gezogen.
        //sollte die Geschwindigkeit der Kugel unter Null fallen wird diese aufgehalten statt die Kugel in die Entgegengesetzte Richtung rollen zu lassen
        //die dann berechnete Beschleunigung wird auf die Bewegung und aktuelle Position der Kugel drauf gerechnet
        for (int i = 0; i < sim.getKugeln().size(); i++) {
            richtung[i] = new Point2D(xx[i], yy[i]);
            double tempDecelaration = (rollReib[i] / masse[i])*1.66;
            geschwindigkeit[i] = geschwindigkeit[i] - tempDecelaration; 
            if (geschwindigkeit[i]<=0){
                geschwindigkeit[i] = 0;
            }
            position[i] = position[i].add(richtung[i].multiply(geschwindigkeit[i]));
        }
         
    }
    //in dieser Methode wird erst das Volumen, dann die Masse und anschließend die Reibungskraft berechnet
    //und als return an die Methode die sie aufruft zurückgegeben um diesen Wert dann in dem Array abzuspeichern
    public double Reibung(double radi,double mat,double matV2) {
        double pi = 1.333333333333333333 * Math.PI;
        double vol = pi * Math.pow((radi*0.1), 3);
        //System.out.println(mass);
        double mass = (mat * vol) / 1000;
        //System.out.println(mass);
        double Fn = (mass * Math.pow(9.81, 1));
        //System.out.println(Fn);
        double Rn = Fn * mat;
        //System.out.println(Rn);
        return Rn;
    }
    //gleicher Fall wie oben, nur dass diesmal schon bei der Masse aufgehört wird und ein return die Masse zum abspeichern zurückgibt
    public double Masse(double radi,double matV2) {
        double pi = 1.333333333333333333 * Math.PI;
        double vol = pi * Math.pow((radi*0.1), 3);       
        double mass = (matV2 * vol) / 1000;
        System.out.println(mass);
        return mass;
    }
    //in dieser Methode wird ein erneuter Aufruf zur neuberechnung der Masse gestartet für den Fall, dass ein anderes Material ausgewählt wurde
    public void calcMass(int i,double radi){
        double materi = sim.getMatV2(i);
        if (i != 0){
        masse[i] = Masse(radius[i],materi);
        }
        else{
            
        masse[0] = Masse(radi,materi);
        }
    }

    public int getRad() {
        return rad;
    }

    public void setRad(int rad) {
        this.rad = rad;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Point2D getPosition(int x) {
        return position[x];
    }
    //hier werden wie vorher schon beschrieben die Arrays und Stoßkraft/Winkel initiiert
    public void stossWinKraft(double winkel, double stoKra, double radiW,double matV2) {
        xx[0] = sin(Math.toRadians(winkel)); //Math.toRadians grad in rad da cos in rad rechnet
        yy[0] = cos(Math.toRadians(winkel));
        radius[0] = radiW;
        masse[0] = Masse(radiW,matV2);
        xx[0] = xx[0] * stoKra / 10;
        yy[0] = yy[0] * stoKra / 10;
        xx[1] = 0;
        yy[1] = 0;
        xx[2] = 0;
        yy[2] = 0;
        xx[3] = 0;
        yy[3] = 0;
        xx[4] = 0;
        yy[4] = 0;
        xx[5] = 0;
        yy[5] = 0;
        geschwindigkeit[0] = 1;
        geschwindigkeit[1] = 1;
        geschwindigkeit[2] = 1;
        geschwindigkeit[3] = 1;
        geschwindigkeit[4] = 1;
        geschwindigkeit[5] = 1;
        

        bereitsBerechnet = true;
    }
}
