package silkRoad;

public class TenderRobot extends Robot {

    public TenderRobot(int position, int x, int y) {
        super(position, x, y);
    }

   
    @Override
    public void setTenges(int recolected) {
        super.setTenges(recolected / 2);
    }
}
