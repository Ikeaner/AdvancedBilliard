/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private static List xValue = new ArrayList<>(Arrays.asList());
    private static List yValue = new ArrayList<>(Arrays.asList());
    private static List radiusValue = new ArrayList<>(Arrays.asList());
    
    private Kugel stosskugel = new Kugel(500, 300, 1);
    private Kugel k1 = new Kugel(100, 150, 30);
    private Kugel k2 = new Kugel(200, 100, 25);
    private Kugel k3 = new Kugel(150, 300, 30);
    private Kugel k4 = new Kugel(250, 300, 25);
    private Kugel k5 = new Kugel(400, 300, 20);

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
        kugeln.add(k1);
        kugeln.add(k2);
        kugeln.add(k3);
        kugeln.add(k4);
        kugeln.add(k5);
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
        
    }

    public  ArrayList<Kugel> getKugeln() {
        return kugeln;
    }
    public static ArrayList getXValue() {
        return (ArrayList) xValue;
    }
    public static ArrayList getYValue() {
        return (ArrayList) yValue;
    }
    public static ArrayList getRadValue() {
        return (ArrayList) radiusValue;
    }
}
