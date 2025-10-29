package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import silkRoad.*;

public class SilkRoadCC4Test {

    private silkRoad sr;

    @BeforeEach
    public void setUp() {
        sr = new silkRoad(12);
    }

    @Test
    public void shouldPlaceNormalElementsAndCalculateProfit() {
        sr.placeStore(3, 30);
        sr.placeStore(6, 50);
        sr.placeStore(8, 40);
        sr.placeRobot(0);
        sr.placeRobot(2);
        sr.moveRobot(0, 3);
        sr.moveRobot(2, 4);
        sr.moveRobot(3, 2);
        sr.moveRobot(6, 2);
        int total = sr.porfit();
        assertTrue(total > 0);
        int[][] tiendas = sr.stores();
        boolean algunaVacia = false;
        for (int[] t : tiendas) {
            if (t[1] == 0) {
                algunaVacia = true;
                break;
            }
        }
        assertTrue(algunaVacia);
        int[][] pm = sr.profitPerMove();
        assertTrue(pm.length >= 2);
    }
}
