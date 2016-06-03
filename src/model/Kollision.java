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
import javafx.geometry.Point2D;

/**
 *
 * @author Graynh
 */

public class Kollision {
     int alreadyCol=0;
    FXML_GUIController asd = new FXML_GUIController();
    public Point2D checkKollision(double wPosX,double wPosY,double wRad, double rPosX,double rPosY,double xx,double yy){
    double weissPosX = wPosX;
    double weissPosY = wPosY;
    double weissRad = wRad;
    double rotPosX = rPosX;
    double rotPosY = rPosY;
    double rotRad = 30;
    double radCol = weissRad + rotRad;
    double xColCirc = (weissPosX - rotPosX);
    double yColCirc = (weissPosY - rotPosY);
    double rectX = 450;
    double rectY = 110;
    double rectW = 50;
    double rectH = 200;   
    double ablenkungY=0;
    double ablenkungX=0;
  
    if(weissPosX > rectX-weissRad&& weissPosX < rectX+rectW+weissRad){
       if (weissPosY > rectY-weissRad&& weissPosY < rectY+rectH+weissRad){
          // System.out.println("Kollision mit Rechteck");
       } 
    }
    if ((radCol*radCol)*10 < (xColCirc*xColCirc) + (yColCirc*yColCirc)){
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
        ablenkungY = speed*Math.sin(rVektor-colAngle);
        ablenkungX = speed*Math.cos(rVektor-colAngle);      
        return new Point2D(ablenkungX,ablenkungY);
        //System.out.println("Kollision mit rotem Kreis");
        
        }
    else{
        return new Point2D(0,0);
    }
    }
    
}
