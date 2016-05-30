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

    private ArrayList<Kugel> kugeln = new ArrayList<Kugel>();
    private ArrayList<Objekt> hindernisse = new ArrayList<Objekt>();
    private ArrayList<Loch> löcher = new ArrayList<Loch>();

    private Kugel stosskugel = new Kugel(30, 30, 20);

    public Kugel getStosskugel() {
        return stosskugel;
    }

    public void setStosskugel(Kugel stosskugel) {
        this.stosskugel = stosskugel;
    }

    public Simulation() {
        ladeKugeln();
    }

    private void ladeKugeln() {
        kugeln.add(stosskugel);

        Kugel k1 = new Kugel(100, 150, 30);
        Kugel k2 = new Kugel(200, 100, 25);
        Kugel k3 = new Kugel(150, 300, 30);
        Kugel k4 = new Kugel(200, 300, 25);
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
}
