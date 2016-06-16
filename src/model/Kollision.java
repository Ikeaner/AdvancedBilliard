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
    //setzt die hier verwendeten Variablen gleich den Werten die an diese Methode weitergegeben wurden
    //rechnet auch paar Werte schon zusammen wie wPosX-rPosX die später bei der Kollisionsabfrage benötigt werden
    double radCol = wRad + rRad;
    double xColCirc = (wPosX - rPosX);
    double yColCirc = (wPosY - rPosY);

    //hier wird geguckt ob die Kugel sich in Richtung einer anderen Kugel bewegt. Ist dies der Fall ist eine Kollision "möglich"
    boolean collisionPossible = calcMove(wPosX,wPosY,wRad,rPosX,rPosY,rRad,xx,yy,xx2,yy2);
    //ist die Summe der Radien zum quadrat größer als die differenz der differenzierten x und y positionen zum quadrat, so besteht eine überschneidung der Kugeln und somit eine Kollision  
    if ((radCol*radCol) > (xColCirc*xColCirc) + (yColCirc*yColCirc)&&collisionPossible==true){
                  
   
        //alreadyCol[index]=true;
        //System.out.println(alreadyCol[index]);
        //System.out.println(alreadyCol[index]+"   "+index);
        //System.out.println(b1+"   "+b2);
        double ax = wPosX-rPosX;
        double ay = wPosY-rPosY;
        //die differenzierten x und y Positionen der beiden Kugeln werden mit dem atan2 gerechnet um somit den Kollisionswinkel zu bekommen
        double colAngle = Math.atan2(ay, ax);
        //System.out.println(Math.toDegrees(colAngle)+"      "+colAngle);
        //berechnet die Geschwindigkeiten der Kugeln mit der sie unterwegs waren um diesen Wert dann mit den x und y geschwindigkeiten zu multiplizieren
        //da diese nicht mehr die richtige geschwindigkeit nach der Rechnung mit dem winkel drauf haben
        double speed1 = Math.sqrt(xx*xx+yy*yy);
        double speed2 = Math.sqrt(xx2*xx2+yy2*yy2);
        //ist die geschwindigkeit der zweiten Kugel null wird winkelberechnung der abprall nicht funktionieren
        //also wird dem ein extrem kleiner wert als geschwindigkeit angegeben das zwar den Winkel verfälscht aber die verfälschung so minimal klein ist dass man sie nicht beachten kann
        //die verfälschung durch überschneidung bei Kollision ist hier wesentlich größer da wir mit frames rechnen
        if (speed2 == 0){
            speed2 = 0.0001;
        }
        //erst werden die richtungsvektoren der beiden Kugeln berechnet und anschließend der Kollisionswinkel von ihnen abgezogen um so den abprallwinkel zu erhalten
        double rVektor  = Math.atan2(yy ,xx );
        double rVektor2  = Math.atan2(yy2 ,xx2 );
        double ablenkungX  = speed1*Math.cos(rVektor-colAngle);
        double ablenkungY  = speed1*Math.sin(rVektor-colAngle);        
        double ablenkungX2 = speed2*Math.cos(rVektor2-colAngle);
        double ablenkungY2 = speed2*Math.sin(rVektor2-colAngle);
        //System.out.println(masse1+"   "+masse2);
        //dann gibt es eine Rechnung der Stärke des abpralls die die Masse der Kugeln in Betracht zieht.
        //eine schwere Kugel wird kaum von einer leichten abgestoßen und anschließend werden diese Werte
        //an die Kugel klasse returned
        //für diese Masseformel habe ich mich an die Anleitung von Darren Jamieson gehalten: http://gamedevelopment.tutsplus.com/tutorials/when-worlds-collide-simulating-circle-circle-collisions--gamedev-769
        //
        double speedX = (ablenkungX *(masse1-masse2)+ (2* masse2*ablenkungX2)) / (masse1 + masse2);
        double speedY = (ablenkungY *(masse1-masse2)+ (2* masse2*ablenkungY2)) / (masse1 + masse2);
        double speedX2 = (ablenkungX2 *(masse2-masse1)+ (2* masse1*ablenkungX)) / (masse1 + masse2);
        double speedY2 = (ablenkungY2 *(masse2-masse1)+ (2* masse1*ablenkungY)) / (masse1 + masse2);   
        
        
        return new double[]{speedX,speedY,speedX2,speedY2}; 
        }   
        //haben wir keine Kollision wird null zurückgegeben die in der Kugel Klasse als "keine Ablenkung" als bedingung steht. 
        //also return 0 = keine ablenkung
        return new double[]{0,0,0,0};   
    }
    
    public int rectCol(double wPosX,double wPosY,double wRad,double rectX,double rectY,double rectW, double rectH){
 
    if(wPosX > rectX-wRad&& wPosX < rectX+rectW+wRad){
       
        if (wPosY > rectY-(wRad*0.99)){
            if (wPosY < rectY+wRad*0.01){
                return 1;
            }
        }
        if (wPosY < rectY+rectH+(wRad*0.99)){
            if (wPosY > rectY+rectH+wRad*0.01){
                return 1;
            }
        }
              
    }
    if (wPosY > rectY-wRad&& wPosY < rectY+rectH+wRad){
       
        if (wPosX > rectX-(wRad*0.99)){
            if (wPosX < rectX+wRad*0.01){
                return 2;
            }
        }
        if (wPosX < rectX+rectW+(wRad*0.99)){
            if (wPosX > rectX+rectW+wRad*0.01){
                return 2;
            }
        }
           
    } 
    return 0;
    }
    
    public boolean calcMove(double xPos1,double yPos1, double rad1,double xPos2,double yPos2,double radi2,double xx1,double yy1, double xx2, double yy2){
        //hier wird ähnlich wie bei der Kollisionsabfrage die position beider Kugeln ermittelt und dann die x und y richtung der ersten Kugel drauf addiert
        // ist also die aktuelle position kleiner als die position mit drauf addierten richtungen dann bewegt sich die Kugel auf die zweite Kugel zu und eine Kollision ist möglich
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
