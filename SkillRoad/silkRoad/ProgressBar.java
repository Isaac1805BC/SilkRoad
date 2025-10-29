package silkRoad;
import shapes.*;

public class ProgressBar {
    private Rectangle outer;
    private Rectangle inner;
    private int maxWidth;

    public ProgressBar() {
        this.maxWidth = 100;
        this.outer = new Rectangle(20, 100, 10, 0, "white");
        this.inner = new Rectangle(10, 10, 10, 5, "red");
    }

    public void update(int recolectado, int total) {
        int porcentaje = (total > 0) ? (recolectado * 100) / total : 0;
        int innerWidth = Math.min((maxWidth * porcentaje) / 100, maxWidth);
        inner.changeSize(10, innerWidth);
    }

    public void makeVisible() {
        outer.makeVisible();
        inner.makeVisible();
    }

    public void makeInvisible(){
        outer.makeInvisible();
        inner.makeInvisible();
    }
}
