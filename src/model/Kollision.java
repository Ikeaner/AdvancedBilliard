/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static com.sun.javafx.util.Utils.clamp;
import control.FXML_GUIController;
import java.awt.Rectangle;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.lang.reflect.Array;
import javafx.geometry.Point2D;

/**
 *
 * @author Graynh
 */

public class Kollision {
    private boolean[] alreadyCol=new boolean[30];  
    public static int alreadyDone = 0;
    FXML_GUIController asd = new FXML_GUIController();
    
    
    public double[] checkKollision(double wPosX,double wPosY,double wRad, double rPosX,double rPosY,double rRad,double xx,double yy,double xx2,double yy2,int b1,int b2){
    double radCol = wRad + rRad;
    double radColAgain = wRad + rRad +150;
    double xColCirc = (wPosX - rPosX);
    double yColCirc = (wPosY - rPosY);
    double rectX = 450;
    double rectY = 110;
    double rectW = 50;
    double rectH = 200;   
    double ablenkungY=0;
    double ablenkungX=0;
    double ablenkungY2=0;
    double ablenkungX2=0;
    
    if (alreadyDone == 0){
        initBools();
    }
    int index = setBools(b1,b2);
    
  
    if(wPosX > rectX-wRad&& wPosX < rectX+rectW+wRad){
       if (wPosY > rectY-wRad&& wPosY < rectY+rectH+wRad){
          // System.out.println("Kollision mit Rechteck");
       } 
    }
    if ((radColAgain*radColAgain) < (xColCirc*xColCirc) + (yColCirc*yColCirc)&&alreadyCol[index] ==true){
        alreadyCol[index] = false;
    
    }
    if ((radCol*radCol) > (xColCirc*xColCirc) + (yColCirc*yColCirc)&&alreadyCol[index]==false){
     
        alreadyCol[index]=true;
        //System.out.println(alreadyCol[index]+"   "+index);
        double ax = wPosX-rPosX;
        double ay = wPosY-rPosY;
        double colAngle = Math.atan2(ay, ax);
        System.out.println(Math.toDegrees(colAngle)+"      "+colAngle);
        double speed = Math.sqrt(xx*xx+yy*yy);
        double rVektor = Math.atan2(yy, xx);
        double rVektor2 = Math.atan2(yy2,xx2);
        ablenkungX = speed*Math.cos(rVektor-colAngle);
        ablenkungY = speed*Math.sin(rVektor-colAngle);
        
        ablenkungX2 = speed*Math.cos(rVektor2-colAngle);
        ablenkungY2 = speed*Math.sin(rVektor2-colAngle);
        return new double[]{ablenkungX,ablenkungY,ablenkungX2,ablenkungY2}; 
        }
    else{
        return new double[]{0,0,0,0};
    }
    }
    public void initBools(){
    alreadyCol [0] = false;
    alreadyCol [1] = false;
    alreadyCol [2] = false;
    alreadyCol [3] = false;
    alreadyCol [4] = false;
    alreadyCol [5] = false;
    alreadyCol [6] = false;
    alreadyCol [7] = false;
    alreadyCol [8] = false;
    alreadyCol [9] = false;
    alreadyCol [10] = false;
    alreadyCol [11] = false;
    alreadyCol [12] = false;
    alreadyCol [13] = false;
    alreadyCol [14] = false;
    alreadyCol [15] = false;
    alreadyCol [16] = false;
    alreadyCol [17] = false;
    alreadyCol [18] = false;
    alreadyCol [19] = false;
    alreadyCol [20] = false;
    alreadyCol [21] = false;
    alreadyCol [22] = false;
    alreadyCol [23] = false;
    alreadyCol [24] = false;
    alreadyCol [25] = false;
    alreadyCol [26] = false;
    alreadyCol [27] = false;
    alreadyCol [28] = false;
    alreadyCol [29] = false;
    
    alreadyDone = 1;  
    }
    
    public int setBools(int ballOne, int ballTwo){
        if (ballOne == 0 && ballTwo == 1)
        {
            return 0;
        }
        if (ballOne == 0 && ballTwo == 2)
        {
            return 1;
        }
        if (ballOne == 0 && ballTwo == 3)
        {
            return 2;
        }
        if (ballOne == 0 && ballTwo == 4)
        {
            return 3;
        }
        if (ballOne == 0 && ballTwo == 5)
        {
            return 4;
        }
        //----------------------------------------------------------------------------------------------------
        if (ballOne == 1 && ballTwo == 0)
        {
            return 0;
        }
        if (ballOne == 1 && ballTwo == 2)
        {
            return 6;
        }
        if (ballOne == 1 && ballTwo == 3)
        {
            return 7;
        }
        if (ballOne == 1 && ballTwo == 4)
        {
            return 8;
        }
        if (ballOne == 1 && ballTwo == 5)
        {
            return 9;
        }
        //----------------------------------------------------------------------------------------------------
        if (ballOne == 2 && ballTwo == 0)
        {
            return 1;
        }
        if (ballOne == 2 && ballTwo == 1)
        {
            return 6;
        }
        if (ballOne == 2 && ballTwo == 3)
        {
            return 12;
        }
        if (ballOne == 2 && ballTwo == 4)
        {
            return 13;
        }
        if (ballOne == 2 && ballTwo == 5)
        {
            return 14;
        }
        //----------------------------------------------------------------------------------------------------
        if (ballOne == 3 && ballTwo == 0)
        {
            return 2;
        }
        if (ballOne == 3 && ballTwo == 1)
        {
            return 7;
        }
        if (ballOne == 3 && ballTwo == 2)
        {
            return 12;
        }
        if (ballOne == 3 && ballTwo == 4)
        {
            return 18;
        }
        if (ballOne == 3 && ballTwo == 5)
        {
            return 19;
        }
        //----------------------------------------------------------------------------------------------------
        if (ballOne == 4 && ballTwo == 0)
        {
            return 3;
        }
        if (ballOne == 4 && ballTwo == 1)
        {
            return 8;
        }
        if (ballOne == 4 && ballTwo == 2)
        {
            return 13;
        }
        if (ballOne == 4 && ballTwo == 3)
        {
            return 18;
        }
        if (ballOne == 4 && ballTwo == 5)
        {
            return 24;
        }
        //----------------------------------------------------------------------------------------------------
        if (ballOne == 5 && ballTwo == 0)
        {
            return 4;
        }
        if (ballOne == 5 && ballTwo == 1)
        {
            return 9;
        }
        if (ballOne == 5 && ballTwo == 2)
        {
            return 14;
        }
        if (ballOne == 5 && ballTwo == 3)
        {
            return 19;
        }
        if (ballOne == 5 && ballTwo == 4)
        {
            return 24;
        }
        //---------------------------------------------------------------------------------------------------- 
        return 55555;      
    }
}
