/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import etu2050.framework.annotations.*;
/**
 *
 * @author liantsiky
 */
public class Table {
    String Nom; 
    int Age;

    @Url(lien="isaTable",args = true)
    @RestAPI
    public Table[] isaTable(String nom, int age){
        Table [] result = new Table[3];
        result [0] = new Table("Table1",1);
        result [1] = new Table("Table2",2);
        result [2] = new Table("Table3",3);
        
        return result;
    }
    @Url(lien="getTable")
    public String getTable(){
        return "table";
    }
    
    @Url(lien="getNombre")
    public int getNombre(){
        return 11;
    }

    public String getNom(){return this.Nom;}
    public int getAge(){return this.Age;}

    public void setNom(String name){this.Nom=name;}
    public void setAge(int ages ){this.Age=ages;}


    //constructor
     public Table (String name, int ages){
        this.setNom(name);
        this.setAge(ages);
    }
    public Table(){}
    
}
