/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2050.framework.models;

import etu2050.framework.annotations.Url;
/**
 *
 * @author liantsiky
 */
public class Etudiant {
    
    @Url(lien="getEtudiant")
    public String getEtudiant(){
        return "Liantsiky";
    }
}
