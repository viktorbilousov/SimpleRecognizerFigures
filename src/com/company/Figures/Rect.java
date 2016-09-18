package com.company.Figures;

import java.awt.*;

/**
 * Created by Belousov on 17.09.2016.
 */
public class Rect extends Figures {
    private int _x;
    private int _y;
    private int _weight;
    private int _height;

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public int getWeight() {
        return _weight;
    }

    public int getHeight() {
        return _height;
    }

    public Point[] getPointsArr()
    {
         return new Point[]{
                new Point(_x, _y),
                new Point(_x + _weight, _y),
                new Point(_x, _y + _height),
                new Point(_x + _weight, _y + _height)
        };

    }

    public Rect(int x, int y , int lengthWeight, int lengthHeith) {
        _x = x;
        _y = y;
        _height = lengthHeith;
        _weight = lengthWeight;
        boundingBox = this;
    }

    public boolean isCrossRect(Rect R)
    {
        //проверка на внутрешиние точки
        Point upLeftInput = new Point(R._x, R._y);
        Point upRigthInput = new Point(R._x + R._weight, R._y);
        Point downLeftInput = new Point(R._x, R._y + R._height);
        Point downRightInput = new Point(R._x + R._weight, R._y + R._height);

        Point upLeft = new Point(_x, _y);
        Point upRigth = new Point(_x + _weight, _y);
        Point downLeft = new Point(_x, _y + _height);
        Point downRight = new Point(_x + _weight, _y + _height);


        if(isPointInside(upLeftInput) || isPointInside(upRigthInput) || isPointInside(downLeftInput) || isPointInside(downRightInput))
            return true;

        if(R.isPointInside(upLeft) || R.isPointInside(upRigth) || R.isPointInside(downLeft) || R.isPointInside(downRight))
            return true;

        // проверка на пересечение сторон(находятся ли все точки по  сторону ребер)

        // вектора, показывающие в каком направлении от ребра прямугольника находятся все остальные ребра
        // если пересекаются, то вектора противоположных сторон разного направления
        // false - справа от ребра либо сверху, true - слева или снизу

        boolean RIGHT = false;
        boolean LEFT = true;
        boolean UP = false;
        boolean DOWN = true;

        boolean vectorLeftEdge = false;
        boolean vectorRightEdge = false;
        boolean vectorUpEdge = false;
        boolean vectorDownEdge = false;

        if(upLeft.x  < upLeftInput.x)
            vectorLeftEdge = RIGHT;
        else
            vectorLeftEdge = LEFT;

        if(upRigth.x < upRigthInput.x)
            vectorRightEdge = RIGHT;
        else
            vectorRightEdge = LEFT;

        if(upLeft.y > upLeftInput.y)
            vectorUpEdge = UP;
        else
            vectorUpEdge = DOWN;

        if(downLeft.y > downLeftInput.y)
            vectorDownEdge = UP;
        else
            vectorDownEdge = DOWN;

        if(vectorDownEdge != vectorUpEdge && vectorLeftEdge != vectorRightEdge)
            return true;

        return false;

    }

    @Override
    public String toString() {
        String output = "StartPoint: (" + _x + " ; " + _y + ") " +
                "LeightEdges (W,H) = (" + _weight + " ; " + _height + ")";
        return output;
    }

    @Override
    public double area() {
        return _weight * _height;
    }

    @Override
    public boolean isPointInside(Point point) {
        int upX = _x; // координата верхнего угла
        int upY = _y;
        int downX = _x + _weight; // координата нижнего угла
        int downY = _y + _height;

        if(point.getX() >= upX && point.getY() >= upY  && point.getX() <= downX && point.getY() <= downY)
                return true;

        return false;
    }
}
