/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Tom
 */
public class AB_Model extends Observable {

    private static ArrayList<Simulation> simulationen = new ArrayList<Simulation>();
    private static Simulation currentSimulation;

    public AB_Model() {

        ladeSimulationen();

    }

    private void ladeSimulationen() {
        Simulation sim1 = new Simulation();
        simulationen.add(sim1);

    }

    public void setCurrentSimulation(int i) {
        try {
            currentSimulation = simulationen.get(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers();
    }

    public Simulation getCurrentSimulation() {
        return currentSimulation;
    }
}
