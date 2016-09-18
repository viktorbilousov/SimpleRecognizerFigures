package com.company;

import com.company.Figures.Circle;
import com.company.Figures.Rect;
import com.sun.xml.internal.bind.CycleRecoverable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.*;
import java.util.List;

/**
 * Created by Belousov on 17.09.2016.
 */
public class TestInputDrawer {
    private BufferedImage _image;
    private Graphics _graphic;
    private Color _backColor = Color.GRAY;
    private Color _figureColor = Color.white;
    private String _fileName = "TestInput.bmp";

    private int _widthImage;
    private int _heightImage;


    private final int MaxRadius = 150;
    private final int MinRadius = 10;

    private final int MaxRecLeigth = 150;
    private final int MinRecLeigth = 20;


    public TestInputDrawer(int width, int height)
    {
        _widthImage = width;
        _heightImage = height;

        _image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        _graphic = _image.getGraphics();
        clearImage();
    }

    private void clearImage()
    {
        _graphic.setColor(_backColor);
        _graphic.fillRect(0, 0, _widthImage - 1, _heightImage - 1);
     //   _graphic.dispose();
    }

    ///рисует случайные квадраты и круги, которые не пересикаются между сосбой
    public void drawRandFigures(int cntCyrcles, int cntRectangles)
    {
        clearImage();
        ArrayList<Circle> listCircles = new ArrayList<>();
        ArrayList<Rect> listRect = new ArrayList<>();

        drawRandCyrcles(cntCyrcles, listCircles, listRect);
        drawRandRect(cntRectangles, listCircles, listRect);
    }

    private void drawRandCyrcles(int cnt, ArrayList<Circle> listCircles, ArrayList<Rect> listRect)
    {
        if(cnt <= 0 )
            throw new IllegalArgumentException("cnt <= 0 !!!");
        Random rand = new Random();
        int x = -1;
        int y = -1;
        int radius = -1;
        boolean isAdd = true;
        Circle candidatCircle = null;

        for (int i = 0; i < cnt; i++) {
            x = rand.nextInt(_widthImage);
            y = rand.nextInt(_heightImage);
            radius = rand.nextInt(MaxRadius - MinRadius) + MinRadius ;

            if( _widthImage - x < radius + 1 )
                x = _widthImage - radius - 10;
            if( _heightImage - y < radius +1 )
                y = _heightImage -radius - 10;
            if(x < radius + 1)
                x = radius + 10;
            if (y < radius +1)
                y = radius + 10;

            candidatCircle = new Circle(x,y,radius);
            for(Circle C : listCircles) { // пересечение с кругами
               if(C.isCrossCircle(candidatCircle))
               {
                //   System.out.println("circle ups...");
                   isAdd = false;
                   break;
               }
            }

            for(Rect R : listRect)
            {
                if(candidatCircle.isCrossRect(R))
                {
                  //  System.out.println("circle ups...");
                    isAdd = false;
                    break;
                }
            }

            if(!isAdd)
            {
                i--;
                isAdd = true;
                continue;
            }
            listCircles.add(candidatCircle);
        }

        _graphic.setColor(_figureColor);
        int num=0;
        for(Circle c : listCircles) {
            _graphic.fillOval(c.boundingBox.getX(), c.boundingBox.getY(), c.getRadius() * 2, c.getRadius() * 2);
            System.out.println(++num + c.toString());
        }
    }

    private void drawRandRect(int cnt, ArrayList<Circle> listCircles, ArrayList<Rect> listRect)
    {
        if(cnt <= 0 )
            throw new IllegalArgumentException("cnt <= 0 !!!");

        Random rand = new Random();

        int x = -1;
        int y = -1;
        int wight = -1;
        int height = -1;
        boolean isAdd = true;
        Rect candidateRect;
        for (int i = 0; i < cnt; i++) {
            x = rand.nextInt(_widthImage);
            y = rand.nextInt(_heightImage);
            wight = rand.nextInt(MaxRecLeigth - MinRecLeigth) + MinRecLeigth;
            height = rand.nextInt(MaxRecLeigth - MinRecLeigth) + MinRecLeigth;

            if(_widthImage - x < wight + 1 )
                x = _widthImage - wight - 10;
            if(_heightImage - y < height +1 )
                y = _heightImage - height - 10;

            candidateRect = new Rect(x,y,wight,height);

            for (Circle C : listCircles) {
                if(C.isCrossRect(candidateRect)) {
                //    System.out.println("rect ups...");
                    isAdd = false;
                    break;
                }
            }
            if(!isAdd) {
                i--;
                isAdd = true;
                continue;
            }

            for (Rect R : listRect) {
                if(candidateRect.isCrossRect(R)) {
                //    System.out.println("rect ups...");
                    isAdd = false;
                    break;
                }
            }
            if(!isAdd){
                i--;
                isAdd = true;
                continue;
            }
            listRect.add(candidateRect);
        }

        Integer num =0;
        for(Rect R : listRect) {
            _graphic.setColor(_figureColor);
            _graphic.fillRect(R.getX(), R.getY(), R.getWeight(), R.getHeight());
            _graphic.setColor(Color.black);
            _graphic.drawString((++num).toString(),R.getX(), R.getY());
            System.out.println(num + " " + R.toString());
        }

    }




    private boolean isCrossAndereCyrcles(Point center, int radius)
    {
        return true;
    }
    public void writeFile(String path)
    {
        try {
            ImageIO.write(_image, "bmp", new File(path + _fileName));
            System.out.println("Success!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
