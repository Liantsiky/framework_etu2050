package etu2050.framework;

import java.util.HashMap;

public class Modelview {

    String pageJsp;
    HashMap <String,Object>  data;

    //getters &setters
    public String getPageJsp(){return pageJsp;}
    public HashMap <String,Object> getData(){return this.data;}

    public void setPageJsp(String page){this.pageJsp=page;}
    public void setData(HashMap <String,Object> donnees){this.data=donnees;}

    //add item
    public void addItem(String key, Object value){
        this.getData().put(key, value);
    }
    public Modelview(HashMap <String,Object> o){
        this.setData(o);
    }
}
