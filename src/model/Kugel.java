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
        rad = r;
        posX = x;
        posY = y;
        this.index = index;
        position[index] = new Point2D(posX, posY);
        richtung[index] = new Point2D(0, 0);
        radius[index] = r;
        sim = s;
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
        
        if (bereitsBerechnet == false) {
            stossWinKraft(stoWi, stoKra, radiSlider,sim.getMatV2(0));
        }
        thisLoop:
        for (int i = 0; i < sim.getKugeln().size(); i++) {
            for (int a = 0; a < sim.getKugeln().size(); a++) {
                if (a != i) {
                    double ablenkung[] = col.checkKollision(position[i].getX(), position[i].getY(), radius[i], position[a].getX(), position[a].getY(), radius[a], xx[i], yy[i], xx[a], yy[a], i, a, masse[i], masse[a]);
                    if (ablenkung[0] != 0) {
                        geschwindigkeit[i] = geschwindigkeit[i] - (geschwindigkeit[i]*0.25);
                        geschwindigkeit[a] = geschwindigkeit[i];
                        xx[i] = ablenkung[0];
                        yy[i] = ablenkung[1];
                        xx[a] = ablenkung[2] * -1;
                        yy[a] = ablenkung[3] * -1;
                        break thisLoop;

                    }
                }
            }
        }
        for (int i = 0; i < sim.getKugeln().size(); i++) {
            if (position[i].getY() > 480 - radius[i] && geschwindigkeit[i] > 0 && yy[i] > 0) {
                yy[i] = yy[i] * -1;
                 geschwindigkeit[i] = geschwindigkeit[i] - (geschwindigkeit[i]*0.25);
                //System.out.println("Unten bumm");
            }
            if (position[i].getY() - radius[i] <= 20 && geschwindigkeit[i] > 0 && yy[i] < 0) {
                yy[i] = yy[i] * -1;
                 geschwindigkeit[i] = geschwindigkeit[i]- (geschwindigkeit[i]*0.25);
                //System.out.println("Oben bumm");
            }
            if (position[i].getX() > 730 - radius[i] && geschwindigkeit[i] > 0 && xx[i] > 0) {
                xx[i] = xx[i] * -1;
                 geschwindigkeit[i] = geschwindigkeit[i]- (geschwindigkeit[i]*0.25);
                //System.out.println("Rechts bumm");
            }
            if (position[i].getX() - radius[i] <= 20 && geschwindigkeit[i] > 0 && xx[i] < 0) {
                xx[i] = xx[i] * -1;
                geschwindigkeit[i] = geschwindigkeit[i]- (geschwindigkeit[i]*0.25);
                //System.out.println("Links bumm");
            }
            if (position[i].getX() - radius[i] > 1300 && geschwindigkeit[i] > 0 && xx[i] < 0) {
                xx[i] = 0;
                yy[i] = 0;
                //System.out.println("Links bumm");
            }
        }

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

    public double Masse(double radi,double matV2) {
        double pi = 1.333333333333333333 * Math.PI;
        double vol = pi * Math.pow((radi*0.1), 3);       
        double mass = (matV2 * vol) / 1000;
        System.out.println(mass);
        return mass;
    }
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

/*
strecke = geschwindigkeit mal zeit PLUS 1/2beschleunigungmalzeit^2
xx u. yy * 0.001 + 1/2






 */
