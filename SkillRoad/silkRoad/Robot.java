package silkRoad;

import java.util.Random;
import java.util.ArrayList; 
import java.util.List;
import shapes.*;

public abstract class Robot {
    private boolean mode;
    private String baseColorC;
    private String baseColorR;
    private int initialpos;
    private int position;
    private int hasTraveled;
    private String color;
    private Circle circle;
    private Rectangle body;
    private int x;           
    private int y;             
    private int tenges; 
    private List<Integer> movementGains;

    private static final String[] COLORS = {
        "red", "blue", "yellow", "green", "magenta",
        "cyan", "orange", "pink", "gray", "lightGray", "darkGray"
    };

    public Robot(int position, int x, int y) {
        this.mode = false;
        this.initialpos = position;
        this.position = position;
        this.hasTraveled = 0;
        Random rand = new Random();
        this.color = COLORS[rand.nextInt(COLORS.length)];
        this.x = x;
        this.y = y;
        this.tenges = 0; 

        this.movementGains = new ArrayList<>(); 
        int bodyWidth = 20;
        int bodyHeight = 30;
        int headRadius = 10;

        int bodyX = x - bodyWidth / 2;
        int bodyY = y - bodyHeight / 2;
        body = new Rectangle(bodyWidth, bodyHeight, bodyX, bodyY, color);
        this.baseColorR = body.getColor();
        int headX = x;
        int headY = bodyY - headRadius;
        circle = new Circle(headX, headY, headRadius, color);
        this.baseColorC = circle.getColor();

        
        circle.makeVisible();
    }
    
    public void invisible() {
        circle.makeInvisible();
       
    }

    public void evisible() {
        
        circle.makeVisible();
    }

    
    public int getPosition() { return position; } 
    public int getHasTraveled() { return hasTraveled; }
    public void setPosition(int newPos) { this.position = newPos; } 
    public void setLocation(int newLocation) { this.position = newLocation; }

    public void setCoordinates(int newX, int newY) {
        this.x = newX;
        this.y = newY;

        int bodyWidth = 20;
        int bodyHeight = 30;
        int headRadius = 10;

        body.changePosition(newX - bodyWidth / 2, newY - bodyHeight / 2);
        circle.changePosition(newX, newY - bodyHeight / 2 - headRadius);
    }

    public void setTenges(int recolected) { this.tenges = recolected; }
    public int getRecolected() { return tenges; }
    public int getLocation(){ return this.position; }

    public void returnBot(int pixelX, int pixelY) {
        this.position = this.initialpos;
        this.x = pixelX + 50;
        this.y = pixelY + 50;

        int bodyWidth = 20;
        int bodyHeight = 30;
        int headRadius = 10;

        body.changePosition(this.x - bodyWidth / 2, this.y - bodyHeight / 2);
        circle.changePosition(this.x, this.y - bodyHeight / 2 - headRadius);
    }

    public int getInitialpos(){ return this.initialpos; } 

    public void addMovementGain (int gain){ this.movementGains.add(gain); } 
    public List<Integer> getMovementGains() { return this.movementGains; }

    public void setStarMode(boolean boleano){ this.mode = boleano; }
    public boolean isStarMode(){ return this.mode; }

    public void originalColor(){
        body.changeColor(baseColorR);
        circle.changeColor(baseColorC);
    }

    public void setColor(String color){
        body.changeColor(color);
        circle.changeColor(color);
    }

    
    public boolean shouldReturn() { 
        return true; 
    }
    
}
