package com.company.Figures;

import org.w3c.dom.css.Rect;

import java.awt.*;

/**
 * Created by Belousov on 17.09.2016.
 */
public abstract class Figures {
    public com.company.Figures.Rect boundingBox;
    public abstract double area();
    public abstract boolean isPointInside(Point point);

    protected double distance (Point A, Point B)
    {
        return distance(A.x, A.y, B.x, B.y);
    }

    protected double distance(int x1, int y1, int x2, int y2)
    {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
