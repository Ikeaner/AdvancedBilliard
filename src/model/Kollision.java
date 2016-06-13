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
    public boolean[] alreadyCol=new boolean[30];  
    public static int alreadyDone = 0;
    FXML_GUIController asd = new FXML_GUIController();
    
    
    
    public double[] checkKollision(double wPosX,double wPosY,double wRad, double rPosX,double rPosY,double rRad,double xx,double yy,double xx2,double yy2,int b1,int b2){
    double radCol = wRad + rRad;
    double radColAgain = wRad + rRad +15;
    double xColCirc = (wPosX - rPosX);
    double yColCirc = (wPosY - rPosY);
    double rectX = 450;
    double rectY = 110;
    double rectW = 50;
    double rectH = 200;   
    
    /* if (alreadyDone == 0){
    initBools();
    }
    int index = setBools(b1,b2);*/
    
  
    if(wPosX > rectX-wRad&& wPosX < rectX+rectW+wRad){
       if (wPosY > rectY-wRad&& wPosY < rectY+rectH+wRad){
          // System.out.println("Kollision mit Rechteck");
       } 
    }
    /* if ((radColAgain*radColAgain) < (xColCirc*xColCirc) + (yColCirc*yColCirc)){
    alreadyCol[index] = false;
    
    }*/
    boolean collisionPossible = calcMove(wPosX,wPosY,wRad,rPosX,rPosY,rRad,xx,yy,xx2,yy2);
    
    if ((radCol*radCol) > (xColCirc*xColCirc) + (yColCirc*yColCirc)&&collisionPossible==true){
                  
   
        //alreadyCol[index]=true;
        //System.out.println(alreadyCol[index]);
        //System.out.println(alreadyCol[index]+"   "+index);
        //System.out.println(b1+"   "+b2);
        double ax = wPosX-rPosX;
        double ay = wPosY-rPosY;
        double colAngle = Math.atan2(ay, ax);
        //System.out.println(Math.toDegrees(colAngle)+"      "+colAngle);
        double speed = Math.sqrt(xx*xx+yy*yy);
        double rVektor  = Math.atan2(yy ,xx );
        double rVektor2 = Math.atan2(yy2,xx2);
        double ablenkungX  = speed*Math.cos(rVektor-colAngle);
        double ablenkungY  = speed*Math.sin(rVektor-colAngle);        
        double ablenkungX2 = speed*Math.cos(rVektor2-colAngle);
        double ablenkungY2 = speed*Math.sin(rVektor2-colAngle);
        return new double[]{ablenkungX,ablenkungY,ablenkungX2,ablenkungY2}; 
        }   
    
        return new double[]{0,0,0,0};   
    }
    
    /*    public void initBools(){
    for (int i = 0;i< alreadyCol.length;i++)
    {
    alreadyCol [i] = false;
    }
    
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
    }*/
    
    public boolean calcMove(double xPos1,double yPos1, double rad1,double xPos2,double yPos2,double radi2,double xx1,double yy1, double xx2, double yy2){
        
        double xColCirc = (xPos1 - xPos2);
        double yColCirc = (yPos1 - yPos2);
        double xColCirc2 = (xPos1 - xPos2 +xx1);
        double yColCirc2 = (yPos1 - yPos2 +yy1);
        double posNow = (xColCirc*xColCirc)+(yColCirc*yColCirc);
        double posNextFrame = (xColCirc2*xColCirc2)+(yColCirc2*yColCirc2);

        if (posNextFrame < posNow){
            return true;
        }   
        return false;
    }
}
