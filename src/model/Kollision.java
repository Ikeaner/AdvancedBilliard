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
    int alreadyCol=0;
    FXML_GUIController asd = new FXML_GUIController();
    
    
    public double[] checkKollision(double wPosX,double wPosY,double wRad, double rPosX,double rPosY,double rRad,double xx,double yy){
    double weissRad = wRad;
    double radCol = weissRad + rRad;
    double xColCirc = (wPosX - rPosX);
    double yColCirc = (wPosY - rPosY);
    double rectX = 450;
    double rectY = 110;
    double rectW = 50;
    double rectH = 200;   
    double ablenkungY=0;
    double ablenkungX=0;
  
    if(wPosX > rectX-weissRad&& wPosX < rectX+rectW+weissRad){
       if (wPosY > rectY-weissRad&& wPosY < rectY+rectH+weissRad){
          // System.out.println("Kollision mit Rechteck");
       } 
    }
    if ((radCol*radCol)*2 < (xColCirc*xColCirc) + (yColCirc*yColCirc)){
        alreadyCol = 2;
    
    }
    
    if ((radCol*radCol) > (xColCirc*xColCirc) + (yColCirc*yColCirc)&&alreadyCol==2){
     
        alreadyCol=1;
        double ax = wPosX-rPosX;
        double ay = wPosY-rPosY;
        double colAngle = Math.atan2(ay, ax);
        System.out.println(Math.toDegrees(colAngle)+"      "+colAngle);
        double speed = Math.sqrt(xx*xx+yy*yy);
        double rVektor = Math.atan2(yy, xx);
        ablenkungX = speed*Math.cos(rVektor-colAngle);
        ablenkungY = speed*Math.sin(rVektor-colAngle);
        System.out.println(speed);
        return new double[]{ablenkungX,ablenkungY,0.1,0.1}; 
        }
    else{
        return new double[]{0,0,0,0};
    }
    }
    
}
