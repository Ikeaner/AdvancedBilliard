/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tom
 */
public class Hindernisse {

    //getter und setter
    private String art;
    private int posX;
    private int posY;
    private int dimX;
    private int dimY;
    private int material;

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

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

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

    public int getDimX() {
        return dimX;
    }

    public void setDimX(int dimX) {
        this.dimX = dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public void setDimY(int dimY) {
        this.dimY = dimY;
    }

    public Hindernisse(String art, int posX, int posY, int dimX, int dimY) {
        this.art = art;
        this.posX = posX;
        this.posY = posY;
        this.dimX = dimX;
        this.dimY = dimY;
    }

}
