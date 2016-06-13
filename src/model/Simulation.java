/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 * Klasse für die jeweiligen Level. Level = Simulation in unserem
 * Programmkontext beinhält Listen für Kugeln, Objekte, Löcher. Hat eine
 * Stoßkugel.
 *
 * @author Tom
 */
public class Simulation {

    private String ID;

    private ArrayList<Kugel> kugeln = new ArrayList<Kugel>();
    private ArrayList<Objekt> hindernisse = new ArrayList<Objekt>();
    private ArrayList<Loch> löcher = new ArrayList<Loch>();

    private int status;

    public int getStatus() {
        return status;
    }

    public void checkLöcher() {

        for (Kugel k : kugeln) {
            for (Loch l : löcher) {
                int posX = (int) k.getPosition(kugeln.indexOf(k)).getX();
                int posY = (int) k.getPosition(kugeln.indexOf(k)).getY();

                int centerX = l.getPosX();
                int centerY = l.getPosY();
                int rad = l.getRad();

                if (Math.sqrt(Math.pow((centerX - posX), 2) + Math.pow((centerY - posY), 2)) < rad) {
                    
                    if (k.getRad() == l.getRad()) {
                        System.out.println("in hole");
                        k.setXx(kugeln.indexOf(k), 0);
                        k.setYy(kugeln.indexOf(k), 0);
                        k.setPosition(kugeln.indexOf(k), 1500, 1500);
                        k.setIsInHole(true);
                        k.setIsInRightHole(true);
                    }
                    else if(k.getRad() < l.getRad()) {
                        System.out.println("wrong hole");
                        k.setXx(kugeln.indexOf(k), 0);
                        k.setYy(kugeln.indexOf(k), 0);
                        k.setPosition(kugeln.indexOf(k), 1500, 1500);
                        k.setIsInHole(true);
                    }
                    else if(k.getRad() > l.getRad()) {
                        System.out.println("over hole");
                    }
                }
            }
        }

    }
    
    public void checkStatus()
    {
        
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<Loch> getLöcher() {
        return löcher;
    }

    public void setLöcher(ArrayList<Loch> löcher) {
        this.löcher = löcher;
    }

    private Kugel stosskugel = new Kugel(150, 300, 1, this, 0);

    public Kugel getStosskugel() {
        return stosskugel;
    }

    public void setStosskugel(Kugel stosskugel) {
        this.stosskugel = stosskugel;
    }

    public Simulation(int i) {

        switch (i) {
            case 1:
                ladeObjekte1();
                break;

            case 2:
                ladeObjekte2();
                break;

            case 3:
                ladeObjekte3();
                break;
        }

        ID = Integer.toString(i);
    }

    private void ladeObjekte1() {
        kugeln.add(stosskugel);

        Kugel k1 = new Kugel(100, 150, 30, this, 1);
        Kugel k2 = new Kugel(200, 100, 25, this, 2);

        kugeln.add(k1);
        kugeln.add(k2);

        Loch l1 = new Loch(500, 200, k1.getRad());
        Loch l2 = new Loch(300, 300, k2.getRad());

        löcher.add(l1);
        löcher.add(l2);

        Objekt o1 = new Objekt("WAND", 300, -100, 50, 100); //Recheck y Kordinate, x Kordinate -nach rechts bei Winkel bezogen auf das gedrehte Rechteck

        hindernisse.add(o1);
    }

    public ArrayList<Objekt> getHindernisse() {
        return hindernisse;
    }

    public void setHindernisse(ArrayList<Objekt> hindernisse) {
        this.hindernisse = hindernisse;
    }

    private void ladeObjekte2() {
        kugeln.add(stosskugel);

        Kugel k1 = new Kugel(100, 150, 30, this, 1);
        Kugel k2 = new Kugel(200, 100, 25, this, 2);
        Kugel k3 = new Kugel(600, 300, 30, this, 3);

        kugeln.add(k1);
        kugeln.add(k2);
        kugeln.add(k3);
    }

    private void ladeObjekte3() {
        kugeln.add(stosskugel);

        Kugel k1 = new Kugel(100, 150, 30, this, 1);
        Kugel k2 = new Kugel(200, 100, 25, this, 2);
        Kugel k3 = new Kugel(600, 300, 30, this, 3);
        Kugel k4 = new Kugel(300, 300, 25, this, 4);
        Kugel k5 = new Kugel(400, 400, 20, this, 5);
        kugeln.add(k1);
        kugeln.add(k2);
        kugeln.add(k3);
        kugeln.add(k4);
        kugeln.add(k5);
        /*
        xValue.add(stosskugel.getPosX());
        xValue.add(k1.getPosX());
        xValue.add(k2.getPosX());
        xValue.add(k3.getPosX());
        xValue.add(k4.getPosX());
        xValue.add(k5.getPosX());
        yValue.add(stosskugel.getPosY());
        yValue.add(k1.getPosY());
        yValue.add(k2.getPosY());
        yValue.add(k3.getPosY());
        yValue.add(k4.getPosY());
        yValue.add(k5.getPosY());
        radiusValue.add(stosskugel.getRad());
        radiusValue.add(k1.getRad());
        radiusValue.add(k2.getRad());
        radiusValue.add(k3.getRad());
        radiusValue.add(k4.getRad());
        radiusValue.add(k5.getRad());
         */

    }

    public ArrayList<Kugel> getKugeln() {
        return kugeln;
    }

    @Override
    public String toString() {
        return ID;

        /*
    public static ArrayList getXValue() {
        return (ArrayList) xValue;
    }
    public static ArrayList getYValue() {
        return (ArrayList) yValue;
    }
    public static ArrayList getRadValue() {
        return (ArrayList) radiusValue;*/
    }
}
