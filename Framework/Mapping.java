/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2050.framework;

/**
 *
 * @author liantsiky
 */
public class Mapping {
    String className;
    String method;
    
    public Mapping(String ClassName,String meThod){
        setclassName(ClassName);
        setmethod(meThod);
    }
    
    public String getclassName(){return this.className;}
    public String getmethod(){return this.method;}
    
    public void setclassName(String classe) {this.className=classe;}
    public void setmethod(String fonction) {this.method=fonction;}

}
