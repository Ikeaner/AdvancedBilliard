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
        Simulation sim1 = new Simulation(1);
        Simulation sim2 = new Simulation(2);
        Simulation sim3 = new Simulation(3);
        simulationen.add(sim1);
        simulationen.add(sim2);
        simulationen.add(sim3);

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
