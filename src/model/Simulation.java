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

    private Kugel stosskugel = new Kugel(150, 300, 1);

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

        Kugel k1 = new Kugel(100, 150, 30);
        Kugel k2 = new Kugel(200, 100, 25);

        kugeln.add(k1);
        kugeln.add(k2);
        
        Loch l1 = new Loch(500,200,k1.getRad());
        Loch l2 = new Loch(300,300,k2.getRad());
        
        löcher.add(l1);
        löcher.add(l2);
        
        Objekt o1 = new Objekt("WAND", 300, 100, 50, 100);
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

        Kugel k1 = new Kugel(100, 150, 30);
        Kugel k2 = new Kugel(200, 100, 25);
        Kugel k3 = new Kugel(600, 300, 30);

        kugeln.add(k1);
        kugeln.add(k2);
        kugeln.add(k3);
    }

    private void ladeObjekte3() {
        kugeln.add(stosskugel);

        Kugel k1 = new Kugel(100, 150, 30);
        Kugel k2 = new Kugel(200, 100, 25);
        Kugel k3 = new Kugel(600, 300, 30);
        Kugel k4 = new Kugel(300, 300, 25);
        Kugel k5 = new Kugel(400, 300, 20);
        kugeln.add(k1);
        kugeln.add(k2);
        kugeln.add(k3);
        kugeln.add(k4);
        kugeln.add(k5);

    }

    public ArrayList<Kugel> getKugeln() {
        return kugeln;
    }

    @Override
    public String toString() {
        return ID;
    }
}
