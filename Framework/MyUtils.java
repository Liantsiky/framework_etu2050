/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2050.framework.myutils;

import etu2050.framework.annotations.*;
import etu2050.framework.Mapping;
import etu2050.framework.Modelview;
import etu2050.framework.MyFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


/**
 *
 * @author liantsiky
 */
public class MyUtils {
    
    
    /**
     * attribut Hashmap contenant les classes et les functions qui les parse
     */
    public static final HashMap <Class <?>, Function<String,?>> PARSING = new HashMap<>();
    static{
        PARSING.put(int.class, Integer::parseInt);
        PARSING.put(double.class, Double :: parseDouble);
        PARSING.put(String.class, Function.identity());
    }

    /**
     * Get the name profile that the function require
     * @param mapping
     * @return
     * @throws Exception
     */
    public static String getAuthProfile(Mapping mapping) throws Exception {
        String result = "";
        Method method = searchMethod(mapping);
        result = method.getAnnotation(Auth.class).profile();
        return result;
    }
    /**
     * Check if the method have the annotation Auth and if have a profile
     * @param mapping
     * @return
     * @throws Exception
     */
    public static boolean isAuthProfile(Mapping mapping) throws Exception {
        boolean result = false;
        Method method = searchMethod(mapping);
        if(method.isAnnotationPresent(Auth.class) && method.getAnnotation(Auth.class).profile() != null){
            result = true;
        }
        return result;
    }

    /**
     * Check if the method have the annotation Auth
     * @param mapping
     * @return
     * @throws Exception
     */
    public static boolean isAuth(Mapping mapping) throws Exception {
        boolean result = false;
        Method method = searchMethod(mapping);
        if(method.isAnnotationPresent(Auth.class)){
            result = true;
        }
        return result;
    }
    /**
     * Check if the method has the annotation RestAPI
     * @param mapping
     * @return
     * @throws Exception
     */
    public static boolean isRestAPI(Mapping mapping) throws Exception {
        boolean result = false;
        Method method = searchMethod(mapping);
        if(method.isAnnotationPresent(RestAPI.class)){
            result = true;
        }
        return result;
    }
    /**
     * Get the values of the parameters in the page
     * @param HttpServletRequest
     * @param HttpServletResponse
     * @param PrintWriter
     * @return a table of String, values of the parameter
     */
    public static Object [] getParameterValues(HttpServletRequest request, HttpServletResponse response ,PrintWriter out) throws Exception {
        List <String> parametersName = Collections.list(request.getParameterNames());
        Object [] result = new String[parametersName.size()];
        
       for(int i = 0; i< parametersName.size(); i++) {
            // out.println(parametersName.get(i));
            // if(MyUtils.isFile(request,parametersName.get(i),out) == true){
            //     result[i] = request.getPart(parametersName.get(i));
            // } else {
                result[i] = request.getParameter(parametersName.get(i));
            // }
        }
        return result;
    }

    /**
     * Check if the parameter is a file or not
     * @param request
     * @param response
     * @param out
     * @return true if yes, else false
     * @throws Exception
     */
    public static boolean isFile(HttpServletRequest request, String paramterName,PrintWriter out) throws Exception {
        boolean result = false;
        Part filePart = request.getPart(paramterName);
        String disposition = filePart.getHeader("Content-Disposition");
        if(disposition != null && disposition.startsWith("form-data") && filePart.getSubmittedFileName() != null){
            result = true;
        }
        return result;
    }
    
    /**
     * Call the method who needs arguments and return the object it returns
            result[i] = request.getParameter(parametersName.get(i));
     * @param maps the mapping which contains the name of the class and the method
     * @param args the arguments that the method needs
     * @return the object that the method return
     */
    public static Object getMethodResult(Mapping maps , Object [] args,PrintWriter out) throws Exception {
        Object tosave = Class.forName(maps.getclassName()).getConstructor().newInstance();
       
        Method method = searchMethod(maps);
        //get the arguements type of the method
        Type [] parameterTypes = method.getGenericParameterTypes();


        //parsing the table of String to the type of the arguments 
        Object [] argsParse = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            if (args[i] instanceof String){
                String arg = (String) args[i];
                Function <String, ?> parser = PARSING.get(parameterTypes[i]);
                argsParse[i] = parser.apply(arg);
            } 
            // else {
            //     argsParse[i] = new MyFile((Part) args[i]);
            // }
        }
        //invoke the method with the args and get the result
        Object result = method.invoke(tosave,argsParse);

        return result;
    }
    /**
     * Search the mapping method
     * We have to do a loop because we don't now if the function have args or not
     * @param maps
     * @return
     * @throws Exception
     */
    public static Method searchMethod(Mapping maps) throws Exception {
        Method [] methods = Class.forName(maps.getclassName()).getDeclaredMethods();
        int indice_method = 0;
        for (int i = 0; i<methods.length; i++) {
            if(methods[i].getName() == maps.getmethod()) {
                indice_method = i;
            }
        }
        return methods[indice_method];
    }
    /**
     * check if the method has arguments or not
     * @param maps the mapping that contains the class and method name 
     * @return  true if contains else false
     * @throws Exception
     */
    public static boolean ifArgsExist(Mapping maps) throws Exception {
        boolean result = false;
       
        Method method = searchMethod(maps);
        if(method.getAnnotation(Url.class).args() == true){

            result = true;
        }
        return result;
    }
    

    /** 
     * function that set the attribute of the object 
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
