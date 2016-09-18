package com.company;

import com.company.Figures.Circle;
import com.company.Figures.Rect;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        TestInputDrawer drawer = new TestInputDrawer(1200,800);
        Circle C = new Circle(80,80,1);
        Rect R = new Rect(100,100, 10, 10);
        System.out.println(C.isCrossRect(R));


       drawer.drawRandFigures(50,50);
       drawer.writeFile("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\");

    }
}
