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
    private double geschwindigkeit=1;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    private int posY;

    private Point2D position;

    public Point2D getPosition() {
        return position;
    }

    private Point2D richtung;

    public Kugel(int x, int y, int r) {
        rad = r;
        posX = x;
        posY = y;

        position = new Point2D(x, y);
        richtung = new Point2D(1, 1);
    }

    public void bewegen(Point2D anstoss) {
        geschwindigkeit = geschwindigkeit*1.01;
        position = position.add(richtung.multiply(geschwindigkeit));
    }
}
