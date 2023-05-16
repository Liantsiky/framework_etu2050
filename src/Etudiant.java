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
    String Nom;
    int Age;

    public String getNom(){return this.Nom;}
    public int getAge(){return this.Age;}

    public void setNom(String name){this.Nom=name;}
    public void setAge(int ages ){this.Age=ages;}

    
    @Url(lien="getEtudiant")
    public Modelview getEtudiant(){
        HashMap <String,Object> o= new HashMap<>();
        Modelview result = new Modelview(o);
        
        result.addItem("test", "bonjour");
        result.addItem("test2", 123);
        result.setPageJsp("Etudiant.jsp");
        
        return result;
    }

    @Url(lien = "saveEtudiant")
    public Modelview saveEtudiant(){
        HashMap <String,Object> o= new HashMap<>();
        Modelview result = new Modelview(o);
        int test= this.getAge()+1;
        result.addItem("agemiampy", test);
        result.setPageJsp("Affichage.jsp");
        return result;
    }

    //constructor
    // public Etudiant(String name, int ages){
    //     this.setNom(name);
    //     this.setAge(ages);
    // }
    public Etudiant(){}
}
