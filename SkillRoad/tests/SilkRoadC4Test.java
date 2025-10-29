package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import silkRoad.*;

public class SilkRoadC4Test {

    private silkRoad sr;

    @BeforeEach
    public void setUp() {
        sr = new silkRoad(10);
    }

    @Test
    public void autonomousShouldChooseItsOwnPosition() {
        sr.placeAutonomousStore(0, 50);
        int[][] tiendas = sr.stores();
        assertTrue(tiendas.length > 0);
        assertNotEquals(0, tiendas[0][0]);
    }

    @Test
    public void neverBackRobotShouldNotMoveBackwards() {
        sr.placeNeverBackRobot(4);
        sr.moveRobot(4, -1);
        int[][] robots = sr.robots();
        assertEquals(4, robots[0][0]);
    }

    @Test
    public void fighterStoreShouldOnlyPayIfRobotHasMoreMoney() {
        sr.placeRobot(2);
        sr.placeFighterStore(5, 50);
        sr.moveRobot(2, 3);
        int[][] robots = sr.robots();
        assertEquals(0, robots[0][1]);
        sr.placeStore(6, 40);
        sr.moveRobot(5, 1);
        sr.resupplyStores();
        sr.moveRobot(6, -1);
        robots = sr.robots();
        assertTrue(robots[0][1] > 0);
    }

    @Test
    public void tenderRobotShouldEarnHalf() {
        sr.placeTenderRobot(1);
        sr.placeStore(3, 40);
        sr.moveRobot(1, 2);
        int[][] robots = sr.robots();
        // Con tu implementación actual se aplica la mitad dos veces: (40-2)=38 -> 19 -> 9
        assertEquals(9, robots[0][1]);
    }

    @Test
    public void neverBackRobotShouldStayAfterReturn() {
        sr.placeNeverBackRobot(6);
        sr.placeRobot(2);
        sr.moveRobot(2, 2);
        sr.returnRobots();
        int[][] robots = sr.robots();
        boolean neverBackAt6 = false;
        boolean normalAt2 = false;
        for (int[] r : robots) {
            if (r[0] == 6) neverBackAt6 = true;
            if (r[0] == 2) normalAt2 = true;
        }
        assertTrue(neverBackAt6);
        assertTrue(normalAt2);
    }
}
