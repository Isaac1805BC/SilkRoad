package silkRoad;
import java.awt.*;

import shapes.*;
import shapes.Canvas;

public class cell {
    private int size = 100;
    private String color = "white";
    private boolean hasRobot;
    private boolean hasStore;

    private int positionx;
    private int positiony;
    private boolean visible;
    private Store store;
    private Robot robot;

    public cell(int positionx, int positiony){
        this.hasStore = false;
        this.positionx = positionx;
        this.positiony = positiony;
        this.visible = false;
    }

    public void visible(){
        this.visible = true;
        draw();
    }

    public void invisible() {
        this.visible = false;

        if (this.store != null) {
            this.store.invisible();
        }

        if (this.robot != null) {
            this.robot.invisible();
        }

        Canvas canvas = Canvas.getCanvas();
        canvas.erase(this); 
    }

    public void draw() {
        if(visible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new java.awt.Rectangle(positionx, positiony, size, size));
            canvas.wait(10);
        }
    }

    public void setStore(Store store){
        if(this.hasStore){
            System.err.println("¡Ya tiene tienda!");
        } else {
            this.store = store;
            this.hasStore = true;
        }
    } 

    public void setRobot(Robot robot){
        if(this.hasRobot){
            System.err.println("¡Ya tiene robot!");
        } else {
            this.robot = robot;
            this.hasRobot = true;
        }
    }

    public void botVisible(){
        if(robot != null) robot.evisible();
    }

    public void remove(){
        if(store != null) store.invisible();
        this.hasStore = false;
        this.store = null;
    }

    public void removeBot(){
        if(robot != null) robot.invisible();
        this.robot = null;
        this.hasRobot = false;
    }

    public boolean hasStore() {
        return store != null;
    }

    public Store getStore(){
        return store;
    }

    public Robot getRobot(){
        return robot;
    }

    public int getPixelX(){
        return positionx;
    }

    public int getPixelY(){
        return positiony;
    }

    public void ressuplyHS(){
        if(store != null) store.resupply();
    }
}
