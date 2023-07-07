/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import etu2050.framework.Modelview;
import etu2050.framework.annotations.Url;
import java.util.HashMap;


/**
 *
 * @author liantsiky
 */
public class Maison {
    
    @Url(lien="isaTrano",args = true)
    public Modelview isaTrano(String nom, int age){

        HashMap <String,Object> o= new HashMap<>();
        Modelview result = new Modelview(o);
        
        result.addItem("Nom", nom);
        result.addItem("Age", age);
        result.setPageJsp("Affichage.jsp");
        
        return result;
    }

    @Url(lien="isaTranoNdray",args = true)
    public Modelview isaTranoNdray(String nom, int age){

        HashMap <String,Object> o= new HashMap<>();
        Modelview result = new Modelview(o);
        result.setIsJson(true);
        result.addItem("Nom", nom);
        result.addItem("Age", age+2);
        result.setPageJsp("Affichage2.jsp");
        
        return result;
    }
}
