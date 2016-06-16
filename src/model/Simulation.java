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
    private ArrayList<Hindernisse> hindernisse = new ArrayList<Hindernisse>();
    private ArrayList<Loch> löcher = new ArrayList<Loch>();

    private int status;
    private static double[] mat = new double[6];
    private static double[] matV2 = new double[6];
    
    private int versuche = 0;

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

                        if (kugeln.indexOf(k) != 0) {
                            k.setIsInRightHole(true);
                        }
                    } else if (k.getRad() < l.getRad()) {
                        System.out.println("wrong hole");
                        k.setXx(kugeln.indexOf(k), 0);
                        k.setYy(kugeln.indexOf(k), 0);
                        k.setPosition(kugeln.indexOf(k), 1500, 1500);
                        k.setIsInHole(true);
                    } else if (k.getRad() > l.getRad()) {
                        System.out.println("over hole");
                    }
                }
            }
        }
    }

    public void plusEinsVersuch()
    {
        versuche++;
    }
    
    public int getVersuche()
    {
        return versuche;
    }
    
    public int checkStatus() {

        int i = 0;
        int countRight = 0;
        int countWrong = 0;

        for (Kugel k : kugeln) {
            if (k.isInHole()) {
                if (k.isInRightHole()) {
                    countRight++;
                } else {
                    countWrong++;
                }
            }
        }
        if (countRight == kugeln.size() - 1) {
            i = 1;
        }
        if (countWrong > 0) {
            i = 2;
        }
        //STATUS: WEITERMACHEN = 0
        //STATUS: GEWONNEN = 1
        //STATUS: VERLOREN = 2
        return i;
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

    private Kugel stosskugel = new Kugel(150, 300, 1, this, 0, mat[0], matV2[0]);

    public Kugel getStosskugel() {
        return stosskugel;
    }

    public void setStosskugel(Kugel stosskugel) {
        this.stosskugel = stosskugel;
    }

    public Simulation(int i) {

        for (int a = 0; a < mat.length; a++) {
            mat[a] = 0.035;
            matV2[a] = 1;
        }
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

        Kugel stosskugel = new Kugel(150, 300, 1, this, 0, mat[0], matV2[0]);
        Kugel k1 = new Kugel(100, 150, 30, this, 1, mat[1], matV2[1]);
        Kugel k2 = new Kugel(200, 100, 25, this, 2, mat[2], matV2[2]);
        kugeln.add(stosskugel);
        kugeln.add(k1);
        kugeln.add(k2);

        Loch l1 = new Loch(50, 100, k1.getRad());
        Loch l2 = new Loch(500, 200, k2.getRad());

        löcher.add(l1);
        löcher.add(l2);
        //setzt den boolean der verhindert das bei erneutem durchlauf alle Werte neu berechnet werden wieder auf false um beim Reset alles wieder neu auf den Anfang zu setzen
        Kugel.bereitsBerechnet = false;

        //Objekt o1 = new Objekt("WAND", 300, -100, 50, 100); //Recheck y Kordinate, x Kordinate -nach rechts bei Winkel bezogen auf das gedrehte Rechteck

        //hindernisse.add(o1);
    }

    public ArrayList<Hindernisse> getHindernisse() {
        return hindernisse;
    }

    public void setHindernisse(ArrayList<Hindernisse> hindernisse) {
        this.hindernisse = hindernisse;
    }

    public void load() {
        kugeln.clear();
        löcher.clear();
        hindernisse.clear();
        switch (ID) {
            case "1":
                ladeObjekte1();
                break;

            case "2":
                ladeObjekte2();
                break;

            case "3":
                ladeObjekte3();
                break;
        }
    }

    private void ladeObjekte2() {

        Kugel stosskugel = new Kugel(150, 300, 1, this, 0, mat[0], matV2[0]);
        Kugel k1 = new Kugel(75, 370, 30, this, 1, mat[1], matV2[1]);
        Kugel k2 = new Kugel(250, 200, 25, this, 2, mat[2], matV2[2]);
        Kugel k3 = new Kugel(600, 300, 30, this, 3, mat[3], matV2[3]);
        kugeln.add(stosskugel);
        kugeln.add(k1);
        kugeln.add(k2);
        kugeln.add(k3);
        
        Loch l1 = new Loch(100, 450, k1.getRad());
        Loch l2 = new Loch(200, 100, k2.getRad());
        Loch l3 = new Loch(500, 200, k3.getRad());

        löcher.add(l1);
        löcher.add(l2);
        löcher.add(l3);
        
        Kugel.bereitsBerechnet = false;
        
        Hindernisse o1 = new Hindernisse("WAND", 225, 275, 50, 200); //Recheck y Kordinate, x Kordinate -nach rechts bei Winkel bezogen auf das gedrehte Rechteck

        hindernisse.add(o1);
    }

    private void ladeObjekte3() {

        Kugel stosskugel = new Kugel(150, 300, 1, this, 0, mat[0], matV2[0]);
        Kugel k1 = new Kugel(250, 300, 30, this, 1, mat[1], matV2[1]);
        Kugel k2 = new Kugel(350, 300, 25, this, 2, mat[2], matV2[2]);
        Kugel k3 = new Kugel(430, 300, 20, this, 3, mat[3], matV2[3]);
        Kugel k4 = new Kugel(550, 300, 25, this, 4, mat[4], matV2[4]);
        Kugel k5 = new Kugel(650, 300, 20, this, 5, mat[5], matV2[5]);
        kugeln.add(stosskugel);
        kugeln.add(k1);
        kugeln.add(k2);
        kugeln.add(k3);
        kugeln.add(k4);
        kugeln.add(k5);
        
        Loch l1 = new Loch(400, 400, k1.getRad());
        Loch l2 = new Loch(400, 50, k2.getRad());
        Loch l3 = new Loch(50, 300, k3.getRad());
        Loch l4 = new Loch(600, 150, k2.getRad());
        Loch l5 = new Loch(700, 250, k3.getRad());

        löcher.add(l1);
        löcher.add(l2);
        löcher.add(l3);
        löcher.add(l4);
        löcher.add(l5);
        
        Kugel.bereitsBerechnet = false;
    }

    public ArrayList<Kugel> getKugeln() {
        return kugeln;
    }

    public double getMat(int i) {
        return mat[i];
    }

    public double getMatV2(int i) {
        return matV2[i];
    }

    public void setMat(double a, double m, int circle) {
        //setzt die Materialarrays mit index gleich den Werten die aus dem Controller übergeben werden
        mat[circle] = a;
        matV2[circle] = m;

    }

    @Override
    public String toString() {
        return ID;
    }
}
