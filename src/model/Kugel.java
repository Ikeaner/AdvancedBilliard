/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
    private double geschwindigkeit = 1;
    private int posY;
    private Point2D richtung;
    private Point2D position;
    private double xx = -0.5;
    private double yy = 0.77;

    public Kugel(int x, int y, int r) {
        rad = r;
        posX = x;
        posY = y;

        position = new Point2D(x, y);
        richtung = new Point2D(xx, yy);
    }

    public void bewegen(Point2D anstoss) {
        double yPos = position.getY();
        double xPos = position.getX();
        if (yPos > 480 || yPos < 20) {
            yy = richtung.getY() * -1;
            geschwindigkeit = geschwindigkeit - 0.5;
            System.out.println("Oben oder Unten bumm");
        }
        if (xPos > 730 || xPos <= 20) {
            xx = richtung.getX() * -1;
            geschwindigkeit = geschwindigkeit - 0.5;
            System.out.println("Links oder Rechts bumm");
        }
        richtung = new Point2D(xx, yy);
        geschwindigkeit = geschwindigkeit * 1.01;
        position = position.add(richtung.multiply(geschwindigkeit));
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
}
