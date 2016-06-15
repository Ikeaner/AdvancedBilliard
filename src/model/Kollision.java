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
    
    
    
    public double[] checkKollision(double wPosX,double wPosY,double wRad, double rPosX,double rPosY,double rRad,double xx,double yy,double xx2,double yy2,int b1,int b2,double masse1,double masse2){
    double radCol = wRad + rRad;
    double radColAgain = wRad + rRad +15;
    double xColCirc = (wPosX - rPosX);
    double yColCirc = (wPosY - rPosY);
    double rectX = 450;
    double rectY = 110;
    double rectW = 50;
    double rectH = 200;   
    
     
    if(wPosX > rectX-wRad&& wPosX < rectX+rectW+wRad){
       if (wPosY > rectY-wRad&& wPosY < rectY+rectH+wRad){
          // System.out.println("Kollision mit Rechteck");
       } 
    }

    boolean collisionPossible = calcMove(wPosX,wPosY,wRad,rPosX,rPosY,rRad,xx,yy,xx2,yy2);
    
    if ((radCol*radCol) > (xColCirc*xColCirc) + (yColCirc*yColCirc)&&collisionPossible==true){
                  
   
        //alreadyCol[index]=true;
        //System.out.println(alreadyCol[index]);
        //System.out.println(alreadyCol[index]+"   "+index);
        //System.out.println(b1+"   "+b2);
        double ax = wPosX-rPosX;
        double ay = wPosY-rPosY;
        double colAngle = Math.atan2(ay, ax);
        System.out.println(Math.toDegrees(colAngle)+"      "+colAngle);
        double speed1 = Math.sqrt(xx*xx+yy*yy);
        double speed2 = Math.sqrt(xx2*xx2+yy2*yy2);
        if (speed2 == 0){
            speed2 = 0.0001;
        }
        double rVektor  = Math.atan2(yy ,xx );
        double ablenkungX  = speed1*Math.cos(rVektor-colAngle);
        double ablenkungY  = speed1*Math.sin(rVektor-colAngle);        
        double ablenkungX2 = speed2*Math.cos(rVektor-colAngle);
        double ablenkungY2 = speed2*Math.sin(rVektor-colAngle);
        System.out.println(masse1+"   "+masse2);
        double speedX = (ablenkungX *(masse1-masse2)+ (2* masse2*ablenkungX2)) / (masse1 + masse2);
        double speedY = (ablenkungY *(masse1-masse2)+ (2* masse2*ablenkungY2)) / (masse1 + masse2);
        double speedX2 = (ablenkungX2 *(masse2-masse1)+ (2* masse1*ablenkungX)) / (masse1 + masse2);
        double speedY2 = (ablenkungY2 *(masse2-masse1)+ (2* masse1*ablenkungY)) / (masse1 + masse2);   
        
        
        return new double[]{speedX,speedY,speedX2,speedY2}; 
        }   
    
        return new double[]{0,0,0,0};   
    }

    
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
