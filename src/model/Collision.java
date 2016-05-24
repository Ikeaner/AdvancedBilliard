/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.FXML_GUIController;
import javafx.scene.paint.Color;

/**
 *
 * @author Graynh
 */

public class Collision {

    FXML_GUIController asd = new FXML_GUIController();
    public void checkCollision(double wPosX,double wPosY,double rPosX,double rPosY){
    double weissPosX = wPosX;
    double weissPosY = wPosY;
    double weissRad = 20;
    double rotPosX = rPosX;
    double rotPosY = rPosY;
    double rotRad = 30;
    double radCol = weissRad + rotRad;
    double xCol = (weissPosX - rotPosX);
    double yCol = (weissPosY - rotPosY);
    
    
    if ((radCol*radCol) > (xCol*xCol) + (yCol*yCol)){
        System.out.println("Kollision");
        }
    }
    
}
