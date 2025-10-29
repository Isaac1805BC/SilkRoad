package silkRoad;
import shapes.Rectangle;
import shapes.Triangle;

public abstract class Store {
    private int position;
    private int x;
    private int y;
    protected int tenges;
    private int initialTenges;
    private Triangle triang;
    private Rectangle front;
    private int timesEmptied;
    private String originalColorT;
    private String originalColorF;

    public Store(int position, int tenges, int x, int y){
        this.position = position; 
        this.tenges = tenges;
        this.initialTenges = tenges;
        this.x = x; 
        this.y = y;

        int rectWidth = 40;
        int rectHeight = 40;
        int triHeight = 25;

        int rectX = x - rectWidth / 2;  
        int rectY = y - rectHeight / 2; 

        front = new Rectangle(rectWidth, rectHeight, rectX, rectY, "black");
        int triX = x;
        int triY = rectY - triHeight - 4; 
        triang = new Triangle(triX, triY);
        this.originalColorT = triang.getColor();
        this.originalColorF = front.getColor();
        front.makeVisible();
        triang.draw();
    }

    public void invisible(){
        triang.makeInvisible();
        front.makeInvisible();
    }

    public void resupply(){
        this.tenges = initialTenges;
        triang.changeColor(originalColorT);
        front.changeColor(originalColorF);
    }

    public int getTenges(){ return tenges; }

    public void robotPassed(){
        this.tenges = 0; 
        this.timesEmptied++;
        //triang.changeColor("white");
        //front.changeColor("white");
    } 

    public int getLocation(){ return this.position; } 
    public int getTimesEmptied(){ return this.timesEmptied; }
    
    public int collectBy(Robot r, int stepCost) {
    int gain = this.tenges - stepCost;
    if (gain < 0) gain = 0;
    this.robotPassed();
    return gain;
    }
}
