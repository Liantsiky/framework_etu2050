/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import etu2050.framework.annotations.Url;
import etu2050.framework.Modelview;

/**
 *
 * @author liantsiky
 */
public class Etudiant {
    
    @Url(lien="getEtudiant")
    public Modelview getEtudiant(){
        Modelview result = new Modelview();
        result.setPageJsp("Etudiant.jsp");
        return result;
    }
}
