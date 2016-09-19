package com.company;

import com.company.Figures.Circle;
import com.company.Figures.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int cnt = 500;

        Recognizer R = new Recognizer(Color.white, Color.GRAY);
        R.setWriteSteps(false);


        TestInputDrawer T = new TestInputDrawer(1000,1000);
        T.setSoutON(false);

            T.drawRandFigures(34, 87);
            T.writeFile("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Test.bmp");
            R.loadImage("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Test.bmp");
            System.out.println(R.start());




     /*   ArrayList<Circle> testC = genList(cnt);
        ArrayList<Circle> output  = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {

            R.loadImage("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Circles\\TestInput" + (i + 1) + ".bmp");
            R.start();
            output.add(R.getCList().get(0));
        }

        for(int i=0; i<output.size(); i++)
        {
            if(!output.get(i).equals(testC.get(i)))
            {
                System.out.println("Error num: " + i);
                System.out.println("test : " + (testC.get(i)).toString() + " output : " + output.get(i).toString());
            }
        }
*/
//        generate(cnt);
//        test(cnt);


    }


    static ArrayList<Circle> genList(int cnt)
    {
        TestInputDrawer drawer = new TestInputDrawer(400,400);
        drawer.setSoutON(false);
        ArrayList<Circle> res = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            drawer.drawRandFigures(1, 0);
            drawer.writeFile("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Circles\\TestInput" + (i + 1) + ".bmp");
            res.add(drawer.getListC().get(0));
        }
        return res;
    }
    static void generate(int cnt)
    {
        TestInputDrawer drawer = new TestInputDrawer(400,400);
        for (int i = 0; i < cnt; i++) {
            drawer.drawRandFigures(0,1);
            drawer.writeFile("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Rects\\TestInput" + (i+1) + ".bmp");
        }
        for (int i = 0; i < cnt; i++) {
            drawer.drawRandFigures(1,0);
            drawer.writeFile("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Circles\\TestInput" + (i+1) + ".bmp");
        }
    }
    static void test(int cnt)
    {
        Recognizer recognizer = new Recognizer(Color.white, Color.GRAY);
        for(int i=0; i<cnt ; i++) {
            System.out.println(i + 1);
            recognizer.loadImage("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Rects\\TestInput"+ (i+1) +".bmp");
            if( recognizer.start() == "Rect")
                throw new IllegalAccessError("Error");
            else System.out.println("OK");

            System.out.println("-----");
        }

        for(int i=0; i<cnt; i++) {
            System.out.println(i + 1);
            recognizer.loadImage("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Circles\\TestInput"+ (i+1) +".bmp");
            if( recognizer.start() == "Circle")
                throw new IllegalAccessError("Error");
            else System.out.println("OK");

            System.out.println("-----");
        }
    }
}