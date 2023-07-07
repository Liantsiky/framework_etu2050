/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package etu2050.framework.servlet;

import etu2050.framework.myutils.MyUtils;
import etu2050.framework.Mapping;
import etu2050.framework.Modelview;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import com.google.gson.*;

import etu2050.framework.annotations.Url;

/**
 *
 * @author liantsiky
 */
public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls = new HashMap<String,Mapping>();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
//        String packageName = config.getInitParameter("root");
        try{
            ArrayList <Class<?>> test= MyUtils.getClasses("models", this.getUrlMapping());
        }catch(Exception ex){
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.net.URISyntaxException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, URISyntaxException {

        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        // for ( String key : getUrlMapping().keySet()) {
        //     out.println(getUrlMapping().get(key).getmethod());
        // }
        try {
            String uri= request.getRequestURI().toString();
            String url = MyUtils.getURL(uri);
            if( this.getUrlMapping().containsKey(url)) {
                Mapping check= (Mapping) getUrlMapping().get(url);

                //instance la classe
                Object tosave = Class.forName(check.getclassName()).getConstructor().newInstance();
                Object checkreturn = new Object();

                if (MyUtils.ifArgsExist(check) == true){
                    checkreturn = MyUtils.getMethodResult(check, MyUtils.getParameterValues(request, response, out), out);
                } else {
                    MyUtils.setObject(request, tosave, out);
                    checkreturn= Class.forName(check.getclassName()).getMethod(check.getmethod()).invoke(tosave);
                }
              
                if (checkreturn instanceof Modelview){
                    Modelview page = (Modelview) checkreturn;
                    if(page.getIsJson() == false){
                        for(String key : page.getData().keySet()) {
                            request.setAttribute(key,page.getData().get(key));
                        }
                    } else if (page.getIsJson() == true){
                        Gson gsonObj = new Gson();
                        String jsonStr = gsonObj.toJson(page.getData());
                        request.setAttribute("DisplayJson",jsonStr);

                    }
                    request.getRequestDispatcher(page.getPageJsp()).forward(request,response);
                }
            }
        } catch (Exception ex) {
            out.println(ex);
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);

        }
        

        
    }

     // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
//     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
           Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        try {
            processRequest(request, response);
            
        } catch (Exception ex) {
            // TODO: handle exception
           Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    // getters and setters    
         public HashMap<String, Mapping> getUrlMapping() {
            return MappingUrls;
        }
    
        public void setUrlMapping(HashMap<String, Mapping> urlMapping) {
            this.MappingUrls = urlMapping;
        }
}