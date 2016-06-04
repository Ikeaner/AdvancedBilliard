/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.geometry.Point2D;

/**
 *
 * @author Tom
 */
public class Loch {

    private int posX;
    private int posY;
    private int rad;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getRad() {
        return rad;
    }

    public void setRad(int rad) {
        this.rad = rad;
    }
    
    public Loch(int posX, int posY, int rad)
    {
        this.posX = posX;
        this.posY = posY;
        this.rad = rad;
        
    }
}
