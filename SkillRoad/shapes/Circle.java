package shapes;

import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class Circle {
    public static final double PI = 3.1416;
    private static final String[] COLORS = {
        "red", "blue", "yellow", "green", "magenta", "white",
        "cyan", "orange", "pink", "gray", "lightGray", "darkGray"
    };

    private int diameter;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    // Nuevo constructor (x,y)
    public Circle(int x, int y, int diameter, String color) {
        this.diameter = diameter;
        this.xPosition = x;
        this.yPosition = y;
        this.color = color;
        this.isVisible = false;
    }

    public void makeVisible() {
        isVisible = true;
        draw();
    }

    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new Ellipse2D.Double(xPosition, yPosition,
                diameter, diameter));
            canvas.wait(10);
        }
    }

    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    // Mover horizontal y vertical
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    // Cambiar tamaño
    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    // Nuevo: mover a (x,y)
    public void changePosition(int newX, int newY) {
        erase();
        this.xPosition = newX;
        this.yPosition = newY;
        draw();
    }

    public String getColor(){
        return color;
    }
}
