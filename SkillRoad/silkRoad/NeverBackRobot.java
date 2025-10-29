package silkRoad;
/**
 * Robot especial que nunca puede retroceder ni volver a su posición inicial.
 * Ignora los comandos que impliquen movimiento hacia atrás o retorno.
 */
public class NeverBackRobot extends Robot {

    public NeverBackRobot(int position, int x, int y) {
        super(position, x, y);
    }

    
    @Override
    public boolean shouldReturn() {
        return false;
    }
}
