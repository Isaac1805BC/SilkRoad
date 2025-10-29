package silkRoad;
/**
 * Representa una tienda tipo Fighter, que solo entrega su dinero
 * a robots que tengan más tenges acumulados que ella.
 */
public class Fighter extends Store {

    public Fighter(int position, int tenges, int x, int y) {
        super(position, tenges, x, y);
    }

    @Override
    public int collectBy(Robot r, int stepCost) {
       
        if (r.getRecolected() <= this.getTenges()) {
            return 0; 
        }
        
        int gain = this.getTenges() - stepCost;
        if (gain < 0) gain = 0;
        this.robotPassed();
        return gain;
    }
}