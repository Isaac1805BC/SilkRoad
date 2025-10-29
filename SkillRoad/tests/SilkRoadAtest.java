package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import silkRoad.*;

public class SilkRoadAtest {

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    @Test
    public void acceptanceTest1_TypesAndCreation() {
        silkRoad sr = new silkRoad(12);
        sr.placeStore(4, 50);
        sr.placeFighterStore(6, 60);
        sr.placeAutonomousStore(0, 80);
        sr.placeRobot(1);
        sr.placeTenderRobot(3);
        sr.placeNeverBackRobot(5);
        sleep(300);
        int[][] tiendas = sr.stores();
        int[][] robots = sr.robots();
        assertTrue(tiendas.length >= 3);
        assertTrue(robots.length >= 3);
        boolean autonomousReubicada = false;
        for (int[] t : tiendas) {
            if (t[0] != 0 && t[1] == 80) {
                autonomousReubicada = true;
                break;
            }
        }
        assertTrue(autonomousReubicada);
    }

    @Test
    public void acceptanceTest2_NeverBackAndTenderRelativeBehavior() {
        silkRoad sr = new silkRoad(10);
        sr.placeStore(7, 50);
        sr.placeNeverBackRobot(3);
        sr.placeRobot(1);
        sr.placeTenderRobot(2);
        sr.moveRobot(3, -1);
        int[][] afterBackTry = sr.robots();
        assertEquals(3, afterBackTry[0][0]);
        sr.moveRobot(1, 6);
        int normalGain = sr.robots()[1][1];
        sr.resupplyStores();
        sr.moveRobot(2, 5);
        int tenderGain = sr.robots()[2][1];
        assertTrue(tenderGain > 0);
        assertTrue(tenderGain <= normalGain);
    }

    @Test
    public void acceptanceTest3_FullSimulationAndProfit() {
        silkRoad sr = new silkRoad(12);
        sr.placeStore(3, 40);
        sr.placeStore(5, 30);
        sr.placeFighterStore(7, 60);
        sr.placeTenderRobot(2);
        sr.placeNeverBackRobot(6);
        sr.placeAutonomousStore(0, 90);
        sr.moveRobot(2, 1);
        sr.moveRobot(3, 2);
        sr.moveRobot(5, 2);
        sleep(300);
        int profit = sr.porfit();
        int[][] tiendas = sr.stores();
        boolean algunaVacia = false;
        for (int[] t : tiendas) {
            if (t[1] == 0) {
                algunaVacia = true;
                break;
            }
        }
        assertTrue(profit > 0);
        assertTrue(algunaVacia);
    }
}
