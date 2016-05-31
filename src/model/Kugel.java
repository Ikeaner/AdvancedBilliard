/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.util.Duration;

/**
 *
 * @author Tom
 */
public class Kugel {

    private int rad;
    private int posX;
    private int posY;
    private double geschwindigkeit = 1;
    private Point2D richtung;
    private Point2D position;
    private double xx;
    private double yy;
    Kollision col = new Kollision();
    private boolean bereitsBerechnet = false;

    public Kugel(int x, int y, int r) {
        rad = r;
        posX = x;
        posY = y;

        position = new Point2D(x, y);
        richtung = new Point2D(xx, yy);
    }

    public void bewegen(Point2D anstoss,double radi,double stoWi,double stoKra) {
        double yPos = position.getY();
        double xPos = position.getX();
        
        if (bereitsBerechnet == false){
        stossWinKraft(stoWi,stoKra);
        }
        double rollReib = Reibung(radi);
        
        col.checkKollision(xPos, yPos,radi, 100, 150);
        
        if (yPos > 480-radi && geschwindigkeit > 0 || yPos-radi < 20 && geschwindigkeit > 0) {
            yy = richtung.getY() * -1;
            geschwindigkeit = geschwindigkeit -0.01;
            System.out.println("Oben oder Unten bumm");
        }
        if (xPos > 730-radi && geschwindigkeit > 0 || xPos-radi <= 20 && geschwindigkeit > 0 ) {
            xx = richtung.getX() * -1;
            geschwindigkeit = geschwindigkeit -0.01;
            System.out.println("Links oder Rechts bumm");
        }
        richtung = new Point2D(xx, yy);
        if (geschwindigkeit < 0.005){
            geschwindigkeit = 0;
        }
        else{
        //double bremswirkung = 1- (0.01/radi*rollReib);  
        double bremswirkung = 0.999;
        geschwindigkeit = geschwindigkeit * bremswirkung;
        }        
        position = position.add(richtung.multiply(geschwindigkeit));
    }
    public double Reibung(double radi){
        double radius = radi;
        double pi = 1.333333333333333333*Math.PI;
        double vol = pi * Math.pow(radius, 3);
        //System.out.println(mass);
        double mass = (0.5 * vol)/1000;
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

    public Point2D getPosition() {
        return position;
    }
    
    public void stossWinKraft(double winkel,double stoKra){
        xx = sin(Math.toRadians(winkel+90)); //Math.toRadians grad in rad da cos in rad rechnet
        yy = cos(Math.toRadians(winkel+90));
        xx = xx*stoKra/10;
        yy = yy*stoKra/10;
        bereitsBerechnet = true;
    }
}
