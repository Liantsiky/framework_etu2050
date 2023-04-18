/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import etu2050.framework.annotations.Url;
/**
 *
 * @author liantsiky
 */
public class Table {
    
    @Url(lien="getTable")
    public String getTable(){
        return "table";
    }
    
    @Url(lien="getNombre")
    public int getNombre(){
        return 11;
    }
}
