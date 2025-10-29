package silkRoad;

import java.util.ArrayList;
import java.util.List;

public class SilkRoadContest extends silkRoad
{
    public SilkRoadContest()
    {
        super();
    }

    public List<Integer> solve(int y)
    {
        List<Integer> gananciasPorDia = new ArrayList<>();
        int totalGanancia = 0;
        gananciasPorDia.add(totalGanancia);
        int h = 0;
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

    public void simulate(boolean slow)
    {
        List<Integer> gananciasPorDia = new ArrayList<>();
        int totalGanancia = 0;
        gananciasPorDia.add(totalGanancia);
        int h = 0;
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
                            mejorGanancia = ganancia;
                            mejorPosicion = tienda.getLocation();
                            bestRobotLocation = robot.getLocation();
                            stepsToBestStore = mejorPosicion - robot.getLocation();
                        } 
                    }
                }
            }

            if (slow && bestRobotLocation != -1) {
                int direction = stepsToBestStore > 0 ? 1 : -1;
                int steps = Math.abs(stepsToBestStore);
                for (int i = 0; i < steps; i++) {
                    m(stepsToBestStore > 0 ? bestRobotLocation + i : bestRobotLocation - i, direction, steps);
                    try {
                    Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                movimientoRealizado = true;
            }
            if (!slow && bestRobotLocation != -1) {
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
        System.out.println(">> Juego simulado. Ganancias por día: " + gananciasPorDia);
    }

    public void moveRobotSlow(int location, int steps) {
        Robot r1 = road[location].getRobot();
        if (r1 == null) {   
            System.out.println("!! No hay robot en la posición " + location);
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
    }

    public void m(int location, int steps, int stepsTotal) {
        Robot r1 = road[location].getRobot();
        if (r1 == null) {   
            System.out.println("!! No hay robot en la posición " + location);
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
    
            int plataEnTienda = st1.getTenges();

            int recolected = r1.getRecolected() + plataEnTienda - Math.abs(stepsTotal);
            System.out.println(recolected);
            r1.setTenges(recolected);

            st1.robotPassed(); 
            r1.invisible();
            r1.evisible();

            int totalRecolectado = 0;
            totalRecolectado += r1.getRecolected();
            int totalEnTiendas = 0;
            for (Store st : lstores) totalEnTiendas += st.getTenges();
            bar.update(totalRecolectado, totalRecolectado + totalEnTiendas);
        }

    }
}
