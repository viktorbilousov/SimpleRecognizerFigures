package com.company;
import java.awt.*;


public class Main {

    public static void main(String[] args) {
        Recognizer R = new Recognizer(Color.white, Color.GRAY);
        R.setWriteSteps(false);

        TestInputDrawer T = new TestInputDrawer(1000, 1000);
        T.setSoutON(false);

        T.drawRandFigures(43, 65);
       // T.writeFile("Input\\Test.bmp");
       // R.loadImage("Input\\Test.bmp");
        R.loadImage(T.getImage());
        System.out.println(R.start());
    }
}