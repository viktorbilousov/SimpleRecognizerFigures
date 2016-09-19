package com.company;

import com.company.Figures.Circle;
import com.company.Figures.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Belousov on 18.09.2016.
 */
public class Recognizer {
    private BufferedImage _image;
    private Graphics _graphic;
    private Color figureColor;
    private Color backColor;
    private final int STEP = 10;
    private ArrayList<Circle> listCircles;
    private ArrayList<Rect> listRect;

    private boolean writeSteps = false;

    public void setWriteSteps(boolean writeSteps) {
        this.writeSteps = writeSteps;
    }

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

    public ArrayList<Circle> getCList()
    {
        return listCircles;
    }


     public String start()
     {
         if(_image == null)
             throw new IllegalArgumentException("Dont load image");

         listRect.clear();
         listCircles.clear();
        int cnt = 0;
         for(int y=0; y<_image.getHeight(); y += STEP)
         {
             for (int x = 0; x < _image.getWidth(); x += STEP) {
                 Color pixel = new Color(_image.getRGB(x,y));
                 if(pixel.equals(figureColor)) {
                   //  System.out.println("x = " + x + " y = " + y);
                     Point leftUp = findLeftUpPointFigure(x,y);
                     String figure = recognitionFigure(leftUp.x, leftUp.y);

                     if(figure.equals(Circle.class.getSimpleName())) {
                         Circle findC = null;
                         try {
                              findC = bildCircle(leftUp.x, leftUp.y);
                         }
                         catch (IllegalArgumentException E)
                         {
                             System.out.println(E.getMessage() + " leftUp : "+leftUp.toString() );
                         }
                         listCircles.add(findC);
                        _graphic.setColor(backColor);
                         _graphic.fillOval(findC.boundingBox.getX(), findC.boundingBox.getY(),
                                 findC.getRadius() * 2, findC.getRadius() * 2);
                         if(writeSteps)
                             writeFile("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Steps\\Step" + ++cnt + ".bmp");
                     }
                     if(figure.equals(Rect.class.getSimpleName())){
                         Rect findR = bildRec(leftUp.x, leftUp.y);
                         listRect.add(findR);
                         _graphic.setColor(backColor);
                         _graphic.fillRect(findR.getX(), findR.getY(), findR.getWight(), findR.getHeight());
                         if(writeSteps)
                            writeFile("C:\\Users\\Belousov\\Documents\\Java projects\\SimpleRecognizerFigures\\Input\\Steps\\Step" + ++cnt + ".bmp");
                     }
                 }
             }
         }
         return  "Circles : " + listCircles.size() +" Rectangles : " + listRect.size();
     }

    public int getRecCnt ()
    {
        return listRect.size();
    }
    public int getCirclCnt ()
    {
        return listCircles.size();
    }


    private Point findLeftUpPointFigure(int x, int y)
    {
        Color nextStep = null;
        boolean isFind = false;
        while (!isFind) {
            for (; y >= 0; y--) {
                nextStep = getColor(x, y);
                if (!nextStep.equals(figureColor))
                    break;
            }
            y++;

            for (; x >= 0; x--) {
                nextStep = getColor(x, y);
                if (!nextStep.equals(figureColor))
                    break;
            }
            x++;

           // проверка ( иногда на круге не срабоатывает)
            if(y == 0)
                break;

            isFind = true;
            for (int i = x; i < _image.getWidth() ; i++) {
                if(getColor(i,y-1).equals(figureColor)) {
                    isFind = false;
                    x = i;
                    y--;
                }
            }
        }
        return new Point(x,y);
    }

    private String recognitionFigure(int leftUpX, int leftUpY)
    {
        Color tmp = getColor(leftUpX, leftUpY);
        if(!tmp.equals(figureColor))
            throw new IllegalArgumentException("Imput cord pixes is not a Figure color");

        int resStep = 1;
        // tmp крайний верхний в фигуре если это квадрат
        if(leftUpX - resStep < 0) // если уперлись в верхний левый угол изображения
            return Rect.class.getSimpleName();

        if(getColor(leftUpX-resStep, leftUpY+resStep).equals(figureColor))
            return Circle.class.getSimpleName();

        return Rect.class.getSimpleName();

    }

    private Rect bildRec(int leftUpX, int leftUpY)
    {
        int wigthRec = 0;
        int heightRec = 0;

        for (int i = leftUpX; i < _image.getWidth(); i++) {
            if(getColor(i, leftUpY).equals(figureColor))
                wigthRec++;
            else
                break;

        }
        for (int i = leftUpY; i < _image.getHeight(); i++) {
            if(getColor(leftUpX,i).equals(figureColor))
                heightRec ++;
            else
                break;
        }

        return new Rect(leftUpX,leftUpY,wigthRec,heightRec);
    }

    private Circle bildCircle(int leftUpX, int leftUpY)
    {
        int diameter = 1; // magic
        int centerX = 0;
        int centerY = 0;
        int radius = -1;

        while(getColor(leftUpX + 1, leftUpY).equals(figureColor))
            leftUpX ++ ;

        for (int i = leftUpY; i < _image.getHeight() ; i++) {
            if(getColor(leftUpX,i).equals(figureColor))
                diameter++ ;
            else
                break;
        }

        radius = diameter /2;
        if(diameter %radius == 1 && false)
            throw new IllegalArgumentException("diam % rad == 1 !!!!");

        int rightVertical = 0;
        int leftVertical = 0;
        for (int i = leftUpX, y = leftUpY + radius; i < _image.getWidth(); i++) {
            if(getColor(i,y).equals(figureColor))
                rightVertical ++ ;
            else
                break;
        }
        for (int i = leftUpX, y = leftUpY + radius; i >=0 ; i--) {
            if(getColor(i,y).equals(figureColor))
                leftVertical ++ ;
            else
                break;
        }
        int delta = rightVertical -  leftVertical ;
        centerX = leftUpX + delta/2;
        centerY = leftUpY + radius-1;

        return new Circle(centerX,centerY, radius);
    }

    public Recognizer(Color figureColor, Color backColor) {

        _image = null;
        _graphic = null;
        this.figureColor = figureColor;
        this.backColor = backColor;
        listCircles = new ArrayList<>();
        listRect = new ArrayList<>();
    }

    public void loadImage(BufferedImage image)
    {
        _image = image;
        _graphic = _image.getGraphics();
    }

    public void loadImage(String pathToIm)
    {
        readImage(pathToIm);
        _graphic = _image.getGraphics();

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

    public void writeFile(String path)
    {
        try {
            ImageIO.write(_image, "bmp", new File(path));
          //  System.out.println("Success!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
