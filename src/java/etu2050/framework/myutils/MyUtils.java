/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2050.framework.myutils;

import etu2050.framework.annotations.Url;
import etu2050.framework.Mapping;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
/**
 *
 * @author liantsiky
 */
public class MyUtils {
    /**
     * function mi-scan ny classe rehetra ao anatin'ilay package
     * miantso fonction getMethod ary fenoiny ilay HashMap
     *@param packageName is the package to scan
     * @param urlMapping is the HashMap to fill with the url and Mapping (sprint2)
     * @return ArrayList of all the classes found
     * @throws java.lang.ClassNotFoundException in case there is non e such class
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public static ArrayList<Class<?>> getClasses(String packageName, HashMap<String, Mapping> urlMapping) throws ClassNotFoundException, IOException, URISyntaxException {
        ArrayList<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        path = path.replace("%20", " ");
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File file = new File(resource.toURI());
//            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    if(child.isFile()){
                        String className = packageName + "." + child.getName().split("\\.")[0];
                        classes.add(Class.forName(className));
                        getMethods(Class.forName(className), urlMapping);
                    }
                    classes.addAll(getClasses(packageName + "." + child.getName().split("\\.")[0], urlMapping));
//                }
            } 
        }
        return classes;
    }
    
    public static void getMethods(Class<?> classe, HashMap<String, Mapping> urlMapping){
        for(Method method : classe.getDeclaredMethods()){
            if(method.isAnnotationPresent(Url.class)){
                Url annotation = method.getAnnotation(Url.class);
                String url  = annotation.lien();
                Mapping map = new Mapping(classe.getName(), method.getName());
                urlMapping.put(url, map);
            }
        }        
    }
}
