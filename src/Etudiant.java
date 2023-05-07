/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import etu2050.framework.annotations.Url;

import java.util.HashMap;

import etu2050.framework.Modelview;


/**
 *
 * @author liantsiky
 */
public class Etudiant {
    
    @Url(lien="getEtudiant")
    public Modelview getEtudiant(){
        HashMap <String,Object> o= new HashMap<>();
        Modelview result = new Modelview(o);

        result.addItem("test", "bonjour");
        result.addItem("test2", 123);
        result.setPageJsp("Etudiant.jsp");
        
        return result;
    }
}
