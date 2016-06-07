/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.util.Duration;

/**
 *
 * @author Tom
 */
public class Kugel {

    private int rad;
    private int posX;
    private int posY;
    int colBall;
    private double geschwindigkeit = 1;
    private Point2D[] richtung= new Point2D[6];
    private Point2D[] position=new Point2D[6] ;
    private double xx;
    private double yy;
    private double yyy;
    private double xxx;
    Kollision col = new Kollision();
    private boolean bereitsBerechnet = false;
    private Point2D noChange = new Point2D(0, 0);
    private int material;
    private Simulation sim;

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
    private double nextBallX;
    private double nextBallY;
    private double nextBallRad;

    public Kugel(int x, int y, int r, Simulation s,int index) {
        rad = r;
        posX = x;
        posY = y;
        position[index] = new Point2D(posX, posY);
        sim = s;
    }

    public void bewegen(Point2D anstoss, double radi, double stoWi, double stoKra, int index) {
        double yPos = position[index].getY();
        double xPos = position[index].getX();

        double rollReib = Reibung(radi);
        if (bereitsBerechnet == false) {
            stossWinKraft(stoWi, stoKra);
        }

        for (Kugel k : sim.getKugeln()) {
            if (sim.getKugeln().indexOf(k) != index) {
                nextBallX = k.getPosX();
                nextBallY = k.getPosY();
                nextBallRad = k.getRad();
                // System.out.println(nextBallX+"   "+nextBallY+"    "+circNum);
            }

            double ablenkung[] = col.checkKollision(xPos, yPos, radi, nextBallX, nextBallY, nextBallRad, xx, yy);
            if (ablenkung[0] != 0 || ablenkung[0] != 0) {
                xx = ablenkung[0];
                yy = ablenkung[1];
                yyy=ablenkung[2];
                xxx=ablenkung[3];
                richtung[1] = new Point2D(xxx, yyy);
                break;
                
            }
        }

        if (yPos > 480 - radi && geschwindigkeit > 0 || yPos - radi < 20 && geschwindigkeit > 0) {
            yy = richtung[index].getY() * -1;
            //System.out.println("Oben oder Unten bumm");
        }
        if (xPos > 730 - radi && geschwindigkeit > 0 || xPos - radi <= 20 && geschwindigkeit > 0) {
            xx = richtung[index].getX() * -1;;
            //System.out.println("Links oder Rechts bumm");
        }

        richtung[index] = new Point2D(xx, yy);
        

        if (geschwindigkeit < 0.005) {
            geschwindigkeit = 0;
        } else {
            //double bremswirkung = 1 - (0.01 / radi * rollReib);
            double bremswirkung = 1;
            geschwindigkeit = geschwindigkeit * bremswirkung;
        }
        position[index] = position[index].add(richtung[index].multiply(geschwindigkeit));
    }

    public double Reibung(double radi) {
        double pi = 1.333333333333333333 * Math.PI;
        double vol = pi * Math.pow(radi, 3);
        //System.out.println(mass);
        double mass = (0.5 * vol) / 1000;
        //System.out.println(mass);
        double Fn = (mass * Math.pow(9.81, 1));
        //System.out.println(Fn);
        double Rn = Fn * 0.025;
        //System.out.println(Rn);
        return Rn;
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

    public void stossWinKraft(double winkel, double stoKra) {
        xx = sin(Math.toRadians(winkel)); //Math.toRadians grad in rad da cos in rad rechnet
        yy = cos(Math.toRadians(winkel));
        xx = xx * stoKra / 10;
        yy = yy * stoKra / 10;
        bereitsBerechnet = true;
    }
}
