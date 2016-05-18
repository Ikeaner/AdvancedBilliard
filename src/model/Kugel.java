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

    private Point2D velocity;

    public Kugel(int x, int y, int r) {
        rad = r;
        posX = x;
        posY = y;

        position = new Point2D(x, y);
        velocity = new Point2D(0, 0.1);
    }

    public void bewegen(double t, Point2D anstoss) {
        position = position.add(velocity.multiply(t)).add(anstoss.multiply(0.5).multiply(t));
    }
}
