package com.company;

import com.company.Figures.Circle;
import com.company.Figures.Rect;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Belousov on 18.09.2016.
 */
public class Recognizer {
    private BufferedImage _image;
    private Graphics _graphic;
    private Color figureColor;
    private final int STEP = 10;
    private ArrayList<Circle> listCircles;
    private ArrayList<Rect> listRect;

    public Recognizer(BufferedImage image, Color figureColor) {
        this.figureColor = figureColor;
        _graphic = image.getGraphics();
        this._image = image;

        listCircles = new ArrayList<>();
        listRect = new ArrayList<>();
    }

    private Color getColor(int x,int y)
    {
        return new Color(_image.getRGB(x,y));
    }

     public String start()
     {
         listRect.clear();
         listCircles.clear();

         for(int y=0; y<_image.getHeight(); y += STEP)
         {
             for (int x = 0; x < _image.getWidth(); x += STEP) {
                 Color pixel = new Color(_image.getRGB(x,y));
                 if(pixel.equals(figureColor)) {
                     System.out.println("x = " + x + " y = " + y);
                     return recognitionFigure(x, y);
                 }
             }

         }
         return "ERROR";
     }

    public String recognitionFigure(int x, int y)
    {
        Color tmp = getColor(x,y);
        if(!tmp.equals(figureColor))
            throw new IllegalArgumentException("Imput cord pixes is not a Figure color");

        int resStep = STEP/2;

        if(resStep == 0) // Step == 1
            resStep = 1;

        Color nextStep = getColor(x,y);

        for( ; y >= 0; y--) {
            nextStep = getColor(x,y);
            if(!nextStep.equals(figureColor))
                break;

        }
        y ++;

        for(; x >= 0; x--){
            nextStep = getColor(x,y);
            if(!nextStep.equals(figureColor))
                 break;

        }
        x ++;

        tmp = getColor(x,y);
        // tmp крайний верхний в фигуре если это квадрат

        if(x - resStep < 0) // если уперлись в верхний левый угол изображения
            return Rect.class.getSimpleName();

        if(getColor(x-resStep, y+resStep).equals(figureColor) )
            return Circle.class.getSimpleName();

        return Rect.class.getSimpleName();

    }

    public Recognizer(Color figureColor, String pathToImage) {

        readImage(pathToImage);

        _graphic = _image.getGraphics();
        this.figureColor = figureColor;
        listCircles = new ArrayList<>();
        listRect = new ArrayList<>();
    }

    private void readImage(String path)
    {
        try {
            _image = ImageIO.read(new File(path));
        }
        catch (Exception E) {
            System.out.println(E.getMessage());
        };

    }
    @Override
    public String toString() {
        return super.toString();
    }
}
