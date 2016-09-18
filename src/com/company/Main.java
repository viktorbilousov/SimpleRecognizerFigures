package com.company;

import com.company.Figures.Circle;
import com.company.Figures.Rect;

import java.awt.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int cnt = 500;

        TestInputDrawer drawer = new TestInputDrawer(400,400);
     /*   for (int i = 0; i < cnt; i++) {
            drawer.drawRandFigures(0,1);
            drawer.writeFile("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Rects\\TestInput" + (i+1) + ".bmp");
        }
        for (int i = 0; i < cnt; i++) {
            drawer.drawRandFigures(1,0);
            drawer.writeFile("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Circles\\TestInput" + (i+1) + ".bmp");
        }
*/
        for(int i=0; i<cnt; i++) {
            System.out.println(i+1);
            Recognizer recognizer = new Recognizer(Color.white,
                    "C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Rects\\TestInput"+ (i+1) +".bmp");
            if( recognizer.start() == "Rect")
                throw new IllegalAccessError("Error");
            else System.out.println("OK");

            System.out.println("-----");
        }

        for(int i=0; i<cnt; i++) {
            System.out.println(i+1);
            Recognizer recognizer = new Recognizer(Color.white,
                    "C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Circles\\TestInput"+ (i+1) +".bmp");
            if( recognizer.start() == "Circle")
                throw new IllegalAccessError("Error");
            else System.out.println("OK");

            System.out.println("-----");
        }
    }
}