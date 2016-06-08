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
    private double geschwindigkeit = 1;
    private static Point2D[] richtung= new Point2D[6];
    private static Point2D[] position=new Point2D[6] ;
    private double[] xx=new double[6];
    private double[] yy=new double[6];
    Kollision col = new Kollision();
    private boolean bereitsBerechnet = false;
    private Point2D noChange = new Point2D(0, 0);
    private int material;
    private Simulation sim;
    private Color color;

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
    private double nextBallX;
    private double nextBallY;
    private double nextBallRad;

    public Kugel(int x, int y, int r, Simulation s,int index) {
        rad = r;
        posX = x;
        posY = y;
        position[index] = new Point2D(posX, posY);
        richtung[index] = new Point2D(0, 0);
        sim = s;
        
    }

    public void bewegen(Point2D anstoss, double radi, double stoWi, double stoKra, int index) {


        double rollReib = Reibung(radi);
        if (bereitsBerechnet == false) {
            stossWinKraft(stoWi, stoKra);
        }

        for (Kugel k : sim.getKugeln()) {
            if (sim.getKugeln().indexOf(k) != index) {
                nextBallX = position[sim.getKugeln().indexOf(k)].getX();
                nextBallY = position[sim.getKugeln().indexOf(k)].getY();
                nextBallRad = k.getRad();
                // System.out.println(nextBallX+"   "+nextBallY+"    "+circNum);
            

            double ablenkung[] = col.checkKollision(position[index].getX(), position[index].getY(), radi, nextBallX, nextBallY, nextBallRad, xx[index], yy[index]);
            if (ablenkung[0 ]!= 0 || ablenkung[1] != 0) {
                xx[index] = ablenkung[0];
                yy[index] = ablenkung[1];
                xx[sim.getKugeln().indexOf(k)] = ablenkung[0]*-1;
                yy[sim.getKugeln().indexOf(k)] = ablenkung[1]*-1;
                break;
                
            }
            }
        }
        
        for (int i=0;i<=5;i++){
        if (position[i].getY() > 480 - radi && geschwindigkeit > 0 || position[i].getY() - radi < 20 && geschwindigkeit > 0) {
            yy[i] = yy[i] * -1;
            //System.out.println("Oben oder Unten bumm");
        }
        if (position[i].getX() > 730 - radi && geschwindigkeit > 0 || position[i].getX() - radi <= 20 && geschwindigkeit > 0) {
            xx[i] = xx[i] * -1;;
            //System.out.println("Links oder Rechts bumm");
        }
        }
        
        for (int i=0;i<=5;i++){
        richtung[i] = new Point2D(xx[i], yy[i]);
        }
        
        if (geschwindigkeit < 0.005) {
            geschwindigkeit = 0;
        } else {
            //double bremswirkung = 1 - (0.01 / radi * rollReib);
            double bremswirkung = 1;
            geschwindigkeit = geschwindigkeit * bremswirkung;
        }
        
        for (int i=0;i<=5;i++){
        position[i] = position[i].add(richtung[i].multiply(geschwindigkeit));
        }
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
        xx[0] = sin(Math.toRadians(winkel)); //Math.toRadians grad in rad da cos in rad rechnet
        yy[0] = cos(Math.toRadians(winkel));
        xx[0] = xx[0] * stoKra / 10;
        yy[0] = yy[0] * stoKra / 10;
        bereitsBerechnet = true;
    }
}
