package com.company.Figures;

import java.awt.*;

/**
 * Created by Belousov on 17.09.2016.
 */
public class Circle extends Figures {

    private int centerX;
    private int centerY;
    private int radius;

    public int getCenterX() {
        return centerX;
    }
    public int getCenterY() {
        return centerY;
    }
    public int getRadius() {
        return radius;
    }

    public Circle(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;

        boundingBox = new Rect(centerX - radius , centerY - radius, radius * 2, radius * 2);

    }

    public boolean isCrossCircle(Circle C)
    {
        double dis = distance(centerX, centerY, C.getCenterX(), C.getCenterY());
        if(dis <= radius + C.getRadius())
            return true;

        return false;
    }

    public boolean isCrossRect(Rect R)
    {
        Point centerCircle = new Point(centerX, centerY);

        for(Point P : R.getPointsArr())
        {
            double dis = distance(centerCircle, P);
            if(distance(centerCircle, P) <= radius )
                return true;
        }
        if(R.isPointInside(new Point(centerX, centerY)))
            return true;

        int newX = R.getX() - radius;
        int newY = R.getY() - radius;
        int newW = R.getWight() + 2*radius;
        int newH = R.getHeight() + 2*radius;

        if(newX < 0 )
            newX = 0;
        if(newY < 0 )
            newY = 0;

        Rect bigRect = new Rect(newX,newY,newW, newH);
        if(bigRect.isPointInside(centerCircle))
            return true;

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        Circle C = (Circle)obj;
        if(centerX == C.centerX && centerY == C.centerY && radius == C.radius)
            return true;

        return false;
    }

    @Override
    public double area() {
        return 2 * Math.PI * radius;
    }

    @Override
   public boolean isPointInside(Point point) {
        int pointX = (int)point.getX();
        int pointY = (int)point.getY();
        // перенос системы координат в (0;0)

        pointX -= centerX;
        pointY -= centerY;

        if(pointX * pointX + pointY * pointY  < radius * radius )
            return true;

        return false;

    }

    @Override
    public String toString() {
        String output = "Center = (" + centerX + " ; "+ centerY + ")" + " Radius = " + radius;
        return output;

    }
}
