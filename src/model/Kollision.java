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

/**
 *
 * @author Graynh
 */

public class Kollision {

    FXML_GUIController asd = new FXML_GUIController();
    public void checkKollision(double wPosX,double wPosY,double wRad, double rPosX,double rPosY){
    double weissPosX = wPosX;
    double weissPosY = wPosY;
    double weissRad = wRad;
    double rotPosX = rPosX;
    double rotPosY = rPosY;
    double rotRad = 30;
    double radCol = weissRad + rotRad;
    double xColCirc = (weissPosX - rotPosX);
    double yColCirc = (weissPosY - rotPosY);
    double rectX = 350;
    double rectY = 110;
    double rectW = 50;
    double rectH = 200;   

    if(weissPosX > rectX-weissRad&& weissPosX < rectX+rectW+weissRad){
       if (weissPosY > rectY-weissRad&& weissPosY < rectY+rectH+weissRad){
           System.out.println("Kollision mit Rechteck");
       } 
    }
  
    
    if ((radCol*radCol) > (xColCirc*xColCirc) + (yColCirc*yColCirc)){
        System.out.println("Kollision mit rotem Kreis");
        }
    }
    
}
