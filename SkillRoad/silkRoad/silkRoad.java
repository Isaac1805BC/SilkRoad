package silkRoad;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shapes.Canvas;
import shapes.*;
/**
 * Clase principal del simulador Silk Road. 
 * Se encarga de crear el camino, las celdas, tiendas y robots, 
 * además de controlar los movimientos y la lógica de recolección.
 * 
 * Permite ejecutar simulaciones automáticas y pruebas para verificar
 * el comportamiento de cada tipo de robot y tienda.
 */

public class silkRoad {

    public List<Store> lstores;
    public List<Robot> lrobots;
    public cell[] road;
    public int length;
    public ProgressBar bar;
    public Robot maxRobot;
    public List<Integer> robots;
    public List<int[]> tiendas;
    
public silkRoad() {
   
    bar = new ProgressBar();
    this.lstores = new ArrayList<>();
    this.lrobots = new ArrayList<>();
    this.robots = new ArrayList<>();
    this.tiendas = new ArrayList<>();
    this.length = 28; 
    road = new cell[length];

    int x = 0;
    spiral(28);
    makeVisible();
}

public silkRoad(int length){ 
    bar = new ProgressBar();
    this.length = length;
    road = new cell[length];
    this.lstores = new ArrayList<>();
    this.lrobots = new ArrayList<>();
    this.robots = new ArrayList<>();
    this.tiendas = new ArrayList<>();
    watchRobot();

    if (length > 28) {
        System.err.println("No se puede: longitud no permitida");
        return;
    }

    if (length < 8) {
        int x = 0;
        for (int i = 0; i < length; i++) {
            road[i] = new cell(x, 60);
            x = x + 100 + 5;
        }
    } else { 
        spiral(this.length);
    }

    makeVisible();
}


    public List<Integer> solveGame() {
        // Inicializa la ganancia por cada día (o movimiento)
        List<Integer> gananciasPorDia = new ArrayList<>();
        int totalGanancia = 0;
        gananciasPorDia.add(totalGanancia);
        int h = 0;
        // Mientras haya tiendas con tenges y robots disponibles
        while (h < 30) {
            boolean movimientoRealizado = false;
            int bestRobotLocation = -1;
            int stepsToBestStore = 0;
            int mejorGanancia = Integer.MIN_VALUE;
            int mejorPosicion = -1;
            for (Robot robot : lrobots) {
                // Busca la mejor tienda para este robot
                for (Store tienda : lstores) {
                    if (tienda.getTenges() > 0) {
                        int pasos = Math.abs(tienda.getLocation() - robot.getLocation());
                        int ganancia = tienda.getTenges() - pasos;
                        if (ganancia > mejorGanancia) {
                            System.out.println("Mejor robot en: " + robot.getLocation() + " con pasos a tienda: " + pasos);
                            mejorGanancia = ganancia;
                            mejorPosicion = tienda.getLocation();
                            bestRobotLocation = robot.getLocation();
                            stepsToBestStore = mejorPosicion - robot.getLocation();
                        } 
                    }
                }
            }
            
            if (bestRobotLocation != -1) {
                moveRobot(bestRobotLocation, stepsToBestStore);
                movimientoRealizado = true;
            }
            // Suma la ganancia total actual
            int gananciaActual = 0;
            for (Robot robot : lrobots) {
                gananciaActual += robot.getRecolected();
            }
            gananciasPorDia.add(gananciaActual);
            h ++;
            // Si ningún robot pudo moverse, termina
            if (!movimientoRealizado) break;
        }
        System.out.println(">> Juego resuelto. Ganancias por día: " + gananciasPorDia);
        return gananciasPorDia;
    }


    public void initGame() {
        this.length = 0;
        ArrayList<int[]> matrizInicial = new ArrayList<>(Arrays.asList(
            new int[]{3},
            new int[]{1, 15},
            new int[]{2, 12, 30},
            new int[]{2, 14, 10},
            new int[]{1, 11},
            new int[]{2, 20, 40}
        ));
        int maxPositon = 0;
        for (int[] datos : matrizInicial) {
            if (datos.length == 1) continue;

            if (datos.length > 2) {
                tiendas.add(new int[]{datos[1], datos[2]});
            }
            else if (datos.length == 2) {
                robots.add(datos[1]);
            }

            if (datos[1] > this.length) this.length = datos[1];
            maxPositon = datos[1] > maxPositon ? datos[1] : maxPositon;
        }
        this.length ++;
        road = new cell[this.length];

        if (length > 28) {
            System.err.println("No se puede: longitud no permitida tiene que ser menos de 28");
            return;
        }

        if (length < 8) {
            int x = 0;
            for (int i = 0; i < length; i++) {
                road[i] = new cell(x, 60);
                x = x + 100 + 5;
            }
        } 
        else { 
            spiral(this.length);
        }

        makeVisible();

        for (int[] tienda : tiendas) {
            int pos = tienda[0];
            int teng = tienda[1];
            placeStore(pos, teng);
        }
        for(int r : robots){
            placeRobot(r);
        }
        
        watchRobot();
    }

    public void spiral(int length){
        int centerX = 400;  
        int centerY = 300;  
        int step = 105;     

        int x = centerX;
        int y = centerY;
        int dir = 0;

        int stepsInDir = 1; 
        int moved = 0;      

        for (int i = 0; i < length; i++) {
            road[i] = new cell(x, y);

            switch (dir) {
                case 0: x += step; break;
                case 1: y += step; break;
                case 2: x -= step; break;
                case 3: y -= step; break;
            }

            moved++;
            if (moved == stepsInDir) {
                dir = (dir + 1) % 4;   
                moved = 0;
                stepsInDir++;          
            }
        }
    }

    public void mejorRobot() {
        if (lrobots.isEmpty()) {
            return;
        }

        Robot anterior = this.maxRobot;
        Robot nuevo = lrobots.get(0);

        for (Robot r : lrobots) {
            if (r.getRecolected() > nuevo.getRecolected()) {
                nuevo = r;
            }
        }

        if (anterior != null && anterior != nuevo) {
            anterior.setStarMode(false);
            anterior.originalColor();
            anterior.evisible();
        }

        this.maxRobot = nuevo;

        if (!maxRobot.isStarMode()) {
            robotColor(maxRobot);
        }

        System.out.println(">> El robot con más plata está en la celda " 
            + this.maxRobot.getLocation() + " con " + this.maxRobot.getRecolected() + " tenges.");
    }

    private void watchRobot() {
        mejorRobot();
    }

    private void robotColor(Robot cambiante) {
        new Thread(() -> {
            String[] colores = {"yellow", "red", "green", "cyan", "magenta"};
            int i = 0;
            cambiante.setStarMode(true);
            while (cambiante.isStarMode()) {
                cambiante.setColor(colores[i % colores.length]); 
                i++;
                try { Thread.sleep(200); } catch (InterruptedException e) { }
                cambiante.evisible();
            }
            cambiante.originalColor(); 
            cambiante.evisible();
        }).start();
    }
    
    /**
     * Mueve un robot desde la posición indicada un número determinado de pasos.
     * Si el destino tiene una tienda, el robot intenta recolectar según su tipo.
     *
     * @param location posición actual del robot en la carretera
     * @param steps número de pasos a mover (positivo hacia adelante, 
     *              negativo hacia atrás; los NeverBackRobot no retroceden)
     */
    public void moveRobot(int robotLocation) {
        Robot ro1 = road[robotLocation].getRobot(); 
        if (ro1 == null){
            System.out.println("No existe un robot en esta ubicacion " +robotLocation); 
            return; 
        } 

        Store bestStore = null; 
        int maxGain = -1; 
        int newLocation = -1; 
        for (Store st : lstores){
            if (st.getTenges() > 0){
                int steps = st.getLocation() - robotLocation; 
                int gain = st.getTenges() - Math.abs(steps);  

                if (gain > maxGain) {
                    maxGain = gain; 
                    bestStore = st; 
                    newLocation = st.getLocation(); 
                }
            }
        } 

        if (bestStore == null || maxGain <= 0){
            System.out.println("El robot en " + robotLocation + "no encontro tenges"); 
            return; 
        }
        road [robotLocation].removeBot(); 

        int newX = road[newLocation].getPixelX() + 50; 
        int newY = road [newLocation].getPixelY() + 50; 
        ro1.setCoordinates(newX, newY); 
        ro1. setLocation(newLocation); 

        road [newLocation].setRobot(ro1); 
        road [newLocation].botVisible(); 

        int tengesInStore = bestStore.getTenges(); 
        int recolected = ro1.getRecolected() + maxGain; 
        ro1.setTenges(recolected); 

        ro1.addMovementGain(tengesInStore - Math.abs(bestStore.getLocation() - robotLocation)); 
        bestStore.robotPassed(); 
        ro1.invisible();
        ro1.evisible();
        int totalRecoleted = 0; 
        totalRecoleted += ro1.getRecolected(); 
        int totalInStores = 0; 
        for (Store st : lstores) totalInStores += st.getTenges(); 
        bar.update(totalRecoleted, totalRecoleted + totalInStores); 

        System.out.println ("Robot se mueve de "+ robotLocation + "a" + newLocation + "total tenges del robot "+ maxGain); 

    }
    /**
     * Crea una tienda normal en la celda indicada.
     * 
     * @param location índice de la celda donde se ubicará la tienda
     * @param tenges cantidad inicial de dinero que tendrá la tienda
     */
    public void placeStore(int location, int tenges){
        int x = road[location].getPixelX();
        int y = road[location].getPixelY();
        Store s = new NormalStore(location, tenges, x + 50, y + 50);

        road[location].setStore(s);
        lstores.add(s);

        System.out.println(">> Tienda creada en celda " + location + " con " + tenges + " tenges");
        int totalRecolectado = 0;
        for (Robot k : lrobots) totalRecolectado += k.getRecolected();
        int totalEnTiendas = 0;
        for (Store t : lstores) totalEnTiendas += t.getTenges();

        bar.update(totalRecolectado, totalRecolectado + totalEnTiendas);
    }

    public void removeStore(int location){
        road[location].remove();
        for (Store s : lstores) {
            if ( s.getLocation() == location) {
                lstores.remove(s);
                break;
            }
        }
        int totalRecolectado = 0;

        for (Robot k : lrobots) totalRecolectado += k.getRecolected();

        int totalEnTiendas = 0;
        for ( Store t : lstores) totalEnTiendas += t.getTenges();
        bar.update(totalRecolectado, totalRecolectado + totalEnTiendas);
        System.out.println(">> Tienda eliminada en celda " + location);
    } 

    public void removeRobot(int location) {
        road[location].removeBot();
        lrobots.removeIf(rb -> rb.getLocation() == location);
        System.out.println(">> Robot eliminado en celda " + location);
    }
    /**
     * Reabastece todas las tiendas del camino a su valor inicial.
     * También actualiza la barra de progreso para reflejar el nuevo total disponible.
     */
    public void resupplyStores (){
        for(Store st : lstores){
            st.resupply();
        }
        int totalRecolectado = 0;
        for (Robot rb : lrobots) totalRecolectado += rb.getRecolected();
        int totalEnTiendas = 0;
        for (Store st : lstores) totalEnTiendas += st.getTenges();
        bar.update(totalRecolectado, totalRecolectado + totalEnTiendas);
    } 

    public int porfit() {
        int total = 0;
        for (Robot rb : lrobots) {
            total += rb.getRecolected();
        } 
        return total;
    }

    
    public int[][] stores() { 
        int[][] tiendas = new int[lstores.size()][2];
        for (int i = 0; i < lstores.size(); i++) {
            Store st = lstores.get(i);
            tiendas[i][0] = st.getLocation();
            tiendas[i][1] = st.getTenges();
        }
        return tiendas;
    }

    public int[][] robots () { 
        int[][] robots = new int[lrobots.size()][3];
        for (int i = 0; i < lrobots.size(); i++) {
            Robot rb = lrobots.get(i);
            robots[i][0] = rb.getLocation();
            robots[i][1] = rb.getRecolected();
        }
        return robots;
    } 

    public void makeVisible (){
        for(int i = 0; i < length; i++){
            road[i].visible();
            road[i].draw();
        }
        bar.makeVisible();
    }

    public void placeRobot (int location) {
        int x = road[location].getPixelX() + 50;
        int y = road[location].getPixelY() + 50;
        Robot r = new NormalRobot(location, x, y);
        road[location].setRobot(r);
        lrobots.add(r);
        System.out.println(">> Robot colocado en celda " + location);
    }
    
        public void placeTenderRobot(int location) {
        int x = road[location].getPixelX() + 50;
        int y = road[location].getPixelY() + 50;
        Robot r = new TenderRobot(location, x, y);
        road[location].setRobot(r);
        lrobots.add(r);
        System.out.println(">> TenderRobot colocado en celda " + location);
    }
    
    public void placeNeverBackRobot(int location) {
        int x = road[location].getPixelX() + 50;
        int y = road[location].getPixelY() + 50;
        Robot r = new NeverBackRobot(location, x, y);
        road[location].setRobot(r);
        lrobots.add(r);
        System.out.println(">> NeverBackRobot colocado en celda " + location);
    }
    
    public void placeFighterStore(int location, int tenges) {
        int x = road[location].getPixelX();
        int y = road[location].getPixelY();
        Store s = new Fighter(location, tenges, x + 50, y + 50);
        road[location].setStore(s);
        lstores.add(s);
        System.out.println(">> FighterStore creada en celda " + location + " con " + tenges + " tenges");
    }
    
    public void placeAutonomousStore(int ignoredLocation, int tenges) {
    int pos = bestAutonomousSpot();
    if (pos == -1) {
        System.out.println("!! No hay celdas libres para Autonomous");
        return;
    }
    int x = road[pos].getPixelX();
    int y = road[pos].getPixelY();
    Store s = new Autonomous(pos, tenges, x + 50, y + 50);
    road[pos].setStore(s);
    lstores.add(s);
    System.out.println(">> AutonomousStore creada en celda " + pos + " con " + tenges + " tenges");
    }
    private int bestAutonomousSpot() {
    if (length == 0) return -1;
    int center = length / 2;
    int best = -1;
    int bestDist = Integer.MAX_VALUE;

    for (int i = 0; i < length; i++) {
        if (!road[i].hasStore()) {                 
            int dist = Math.abs(i - center);
            if (dist < bestDist) {
                bestDist = dist;
                best = i;
            }
        }
    }
    return best; }
    


    public void moveRobot(int location, int steps) {
        Robot r1 = road[location].getRobot();
        if (r1 == null) {   
            System.out.println("!! No hay robot en la posición " + location);
            return;
        }
        
        if (r1 instanceof NeverBackRobot && steps < 0) {
            System.out.println("!! NeverBackRobot no puede retroceder (steps=" + steps + ")");
            return;
        }

        int newLocation = location + steps;
        if (newLocation < 0 || newLocation >= length) {
            System.out.println("!! Movimiento inválido: fuera de rango");
            return;
        }

        road[location].removeBot();

        int newX = road[newLocation].getPixelX() + 50;
        int newY = road[newLocation].getPixelY() + 50;
        r1.setCoordinates(newX, newY);
        r1.setLocation(newLocation);
        road[newLocation].setRobot(r1);
        road[newLocation].botVisible();
        

            if (road[newLocation].hasStore()) {
            Store st1 = road[newLocation].getStore();
            int stepCost = Math.abs(steps);
        
            int gain = st1.collectBy(r1, stepCost);
        
            
            if (r1 instanceof TenderRobot) {
                gain = gain / 2;
            }
        
            int recolected = r1.getRecolected() + gain;
            r1.setTenges(recolected);
        
            r1.invisible();
            r1.evisible();
        
            int totalRecolectado = 0;
            totalRecolectado += r1.getRecolected();
            int totalEnTiendas = 0;
            for (Store st : lstores) totalEnTiendas += st.getTenges();
            bar.update(totalRecolectado, totalRecolectado + totalEnTiendas);
        }

    }

    public void returnRobots() {
        for (Robot r : lrobots) {
            if (!r.shouldReturn()) continue;
            int currentLocation = r.getLocation();
            road[currentLocation].removeBot();

            int initLoc = r.getInitialpos();  
            int x0 = road[initLoc].getPixelX();
            int y0 = road[initLoc].getPixelY();

            r.returnBot(x0, y0); 
            road[initLoc].setRobot(r);
            road[initLoc].botVisible();
        }
    }

    public void reboot() {
        for (Robot rb : lrobots) {
            int loc = rb.getLocation();
            road[loc].removeBot();
        }

        for (Store st : lstores) {
            int loc = st.getLocation();
            road[loc].remove();
        }

        lrobots.clear();
        lstores.clear();

        bar.update(0, 0);

        System.out.println(">> Juego reiniciado.");
    }

    public void makeInvisible() {
        for (int i = 0; i < length; i++) {
            road[i].invisible();
        }
        bar.makeInvisible();
    }

    public void finish() {
        makeInvisible();
        System.out.println(">> Juego finalizado.");
    }

    public boolean ok(){ return true; } 

    public int[][] profitPerMove() {
        List<Robot> sortedRobots = new ArrayList<>(lrobots);
        sortedRobots.sort((a, b) -> Integer.compare(a.getLocation(), b.getLocation()));

        int[][] result = new int[sortedRobots.size()][2];
        for (int i = 0; i < sortedRobots.size(); i++) {
            Robot r = sortedRobots.get(i);
            result[i][0] = r.getLocation();
            result[i][1] = r.getMovementGains().stream().mapToInt(Integer::intValue).sum();
        }
        return result;
    }

    public int[][] emptiedStores() {
        List<Store> sortedStores = new ArrayList<>(lstores);
        sortedStores.sort((a, b) -> Integer.compare(a.getLocation(), b.getLocation()));

        int[][] result = new int[sortedStores.size()][2];
        for (int i = 0; i < sortedStores.size(); i++) {
            Store s = sortedStores.get(i);
            result[i][0] = s.getLocation();
            result[i][1] = s.getTimesEmptied();
        }
        return result;
    }

    public int getLength(){
        return this.length;
    }
    public class NormalStore extends Store {
    public NormalStore(int position, int tenges, int x, int y) {
        super(position, tenges, x, y);
        
    }
    
}
}
