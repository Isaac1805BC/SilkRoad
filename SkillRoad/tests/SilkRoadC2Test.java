package tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import silkRoad.*;

public class SilkRoadC2Test {

    private silkRoad silkRoad1;

    @BeforeEach
    public void setUp() {
        silkRoad1 = new silkRoad(15); 
    }

    //---------------------------------------------------------------------------------
    //  PRUEBAS DE CONSTRUCTORES
    //---------------------------------------------------------------------------------

    @Test
    public void accordingOVshouldConstructWithValidLength() {
        assertNotNull(silkRoad1, "El objeto silkRoad no debe ser nulo.");
        assertEquals(15, silkRoad1.getLength() , "La longitud del camino debe ser 15.");
        assertNotNull(silkRoad1.stores(), "La lista de tiendas no debe ser nula.");
        assertEquals(0, silkRoad1.stores().length, "La lista de tiendas debe estar vacía inicialmente.");
        assertNotNull(silkRoad1.robots(), "La lista de robots no debe ser nula.");
        assertEquals(0, silkRoad1.robots().length, "La lista de robots debe estar vacía inicialmente.");
    }

    //---------------------------------------------------------------------------------
    //  PRUEBAS DE LÓGICA DE COLOCACIÓN Y REMOCIÓN
    //---------------------------------------------------------------------------------

    @Test
    public void accordingOVshouldPlaceStoreAtValidLocation() {
        int location = 5;
        int tenges = 500;
        silkRoad1.placeStore(location, tenges);
        
        assertEquals(1, silkRoad1.stores().length, "Debe haber una tienda en la lista.");
    }
    
    @Test
    public void accordingOVshouldPlaceRobotAtValidLocation() {
        int location = 3;
        silkRoad1.placeRobot(location);
        
        assertEquals(1, silkRoad1.robots().length, "Debe haber un robot en la lista.");
    }
    
    @Test
    public void accordingOVshouldRemoveStore() {
        int location = 5;
        silkRoad1.placeStore(location, 500);
        silkRoad1.removeStore(location);
        
        assertTrue(silkRoad1.stores().length == 0, "La lista de tiendas debe estar vacía después de la remoción.");
    }

    //---------------------------------------------------------------------------------
    //  PRUEBAS DE LÓGICA DE MOVIMIENTO
    //---------------------------------------------------------------------------------

    /*@Test
    public void accordingOVshouldMoveRobotToEmptyCell() {
        int initialLocation = 2;
        int steps = 3;
        silkRoad1.placeRobot(initialLocation);
        
        silkRoad1.moveRobot(initialLocation, steps);
        
        robot movedRobot = silkRoad1.robots().get(0);
        assertEquals(initialLocation + steps, movedRobot.getLocation(), "El robot debe moverse a la nueva ubicación.");
        assertEquals(0, movedRobot.getRecolected(), "La ganancia del robot debe ser 0.");
    }
*/

    //---------------------------------------------------------------------------------
    //  PRUEBAS DE LÓGICA GENERAL
    //---------------------------------------------------------------------------------

    @Test
    public void accordingOVshouldResupplyAllStores() {
        // Aquí va la lógica para probar el reabastecimiento.
    }

    @Test
    public void accordingOVshouldRebootGame() {
        // Aquí va la lógica para probar el reinicio.
    }

    @Test
    public void accordingOVshouldSelectBestRobotBasedOnProfit() {
        // Aquí va la lógica para probar la selección del mejor robot.
    }
}