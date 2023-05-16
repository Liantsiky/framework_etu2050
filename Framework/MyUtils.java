/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2050.framework.myutils;

import etu2050.framework.annotations.Url;
import etu2050.framework.Mapping;
import etu2050.framework.Modelview;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.function.Function;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author liantsiky
 */
public class MyUtils {
    
    //attribut Hashmap contenant les classes et les functions qui les parse
    public static final HashMap <Class <?>, Function<String,?>> PARSING = new HashMap<>();
    static{
        PARSING.put(int.class, Integer::parseInt);
        PARSING.put(double.class, Double :: parseDouble);
        PARSING.put(String.class, Function.identity());
    }
    

    /**
     * function that set the attribut of the object 
     * 
     *  @param HttpServletRequest 
     *  @param Object the object we are going to set
     *  @param PrintWriter for test
     *  @throws Exception
     */
    public static void setObject(HttpServletRequest request, Object toset, PrintWriter out) throws Exception {
        Field[] attributs = toset.getClass().getDeclaredFields(); 
        for(Field attribut : attributs){
            Class <?> type = attribut.getType();
            Function <String,?> parser = PARSING.get(type);
            if (parser == null){
                throw new Exception("Fieldtype not in PARSING: "+ type); 
            }
            out.println(attribut.getName());
            Object tosave = parser.apply(request.getParameter(attribut.getName()));
            String method = "set"+attribut.getName();
            toset.getClass().getMethod(method, attribut.getType()).invoke(toset, tosave);
        }
        // Object checkreturn= Class.forName(check.getclassName()).getMethod(check.getmethod()).invoke(Class.forName(check.getclassName()).getConstructor().newInstance());
    }

    /**
     * function return the jsp name of the invoked method 
     * 
     *@param String key of the object
     * @param urlMapping is the HashMap to fill with the url and Mapping (sprint2)
     * @return Modelview class
     * @throws java.lang.ClassNotFoundException in case there is non e such class
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public static String getTheJSP(String key, HashMap <String,Mapping> urlMapping) throws Exception{
        Mapping check= (Mapping) urlMapping.get(key);
        Object objet = Class.forName("models."+check.getclassName()).getConstructor().newInstance();
        Method checkreturn= Class.forName("models."+check.getclassName()).getMethod(check.getmethod());
        String result = String.valueOf(checkreturn.invoke(objet));
        return result;
    }

    /**
     * function that check if the method of the url return a Modelview class
     * 
     *@param String key of the object
     * @param urlMapping is the HashMap to fill with the url and Mapping (sprint2)
     * @return Modelview class
     * @throws java.lang.ClassNotFoundException in case there is non e such class
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public static boolean checkReturn(String key, HashMap <String,Mapping> urlMapping,PrintWriter out) throws Exception{
        boolean result= false;
        Mapping check= (Mapping) urlMapping.get(key);
        // out.println(check.getmethod());
        Object checkreturn= (Modelview) Class.forName(check.getclassName()).getMethod(check.getmethod()).invoke(Class.forName(check.getclassName()).getConstructor().newInstance());
        Method test= Class.forName(check.getclassName()).getMethod(check.getmethod());
        Class <?> classe = test.getReturnType();
        // Object checkreturn = null;
        if ( checkreturn instanceof Modelview){
            result = true;
        }
        return result;
    }

    /**
     * function that split the request uri to get the url
     * 
     *@param String key of the object
     * @param urlMapping is the HashMap to fill with the url and Mapping (sprint2)
     * @return Modelview class
     * @throws java.lang.ClassNotFoundException in case there is non e such class
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public static String getURL(String URI)  {
        String [] split = URI.split("/");
        int size = split.length;
        return split[size-1];
    }
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
                for (File child : file.listFiles()) {
                    if(child.isFile()){
                        String className = packageName + "." + child.getName().split("\\.")[0];
                        classes.add(Class.forName(className));
                        getMethods(Class.forName(className), urlMapping);
                    }
                    classes.addAll(getClasses(packageName + "." + child.getName().split("\\.")[0], urlMapping));

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
                // out.println(method.getName());
                urlMapping.put(url, map);
            }
        }        
    }
}
