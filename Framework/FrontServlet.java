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
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;

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
    String SessionConnect;

    @Override
    public void init(ServletConfig config) throws ServletException {
       String packageName = config.getInitParameter("root");
       String connexName = config.getInitParameter("sessionLogin");
       this.setSessionConnect(connexName);
        
        try{
            ArrayList <Class<?>> test= MyUtils.getClasses(packageName, this.getUrlMapping());
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
        HttpSession session = request.getSession();
        // for ( String key : getUrlMapping().keySet()) {
        //     out.println(getUrlMapping().get(key).getmethod());
        // }
        try {
            String uri= request.getRequestURI().toString();
            String url = MyUtils.getURL(uri);

            //prendre le paramater "profile" et verifier s'il existe
           
            if(request.getParameter("profile") != null) {
                session.setAttribute(getSessionConnect(),request.getParameter("profile"));
                // out.println(request.getSession().getAttribute(getSessionConnect()));
            }

            if( this.getUrlMapping().containsKey(url)) {

                Mapping check= (Mapping) getUrlMapping().get(url);
                //instance la classe
                Object tosave = Class.forName(check.getclassName()).getConstructor().newInstance();
                Object checkreturn = new Object();

                if(MyUtils.isAuth(check) == true) {
                    if(request.getSession().getAttribute(getSessionConnect()) == null) {
                        request.getRequestDispatcher("NonAcces.jsp").forward(request,response);
                        // out.println("Acces refuse");
                    } 
                }
                if(MyUtils.isAuthProfile(check) == true) {
                    if(request.getSession().getAttribute(getSessionConnect()).equals(MyUtils.getAuthProfile(check)) == false) {
                        request.getRequestDispatcher("NonAcces.jsp").forward(request,response);
                    //    out.println(request.getSession().getAttribute(getSessionConnect()));
                    }
                }

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
                //raha toa ka hafa mihintsy ilay instance 
                 } else if (MyUtils.isRestAPI(check) == true) {
                    Object [] obj = (Object []) checkreturn;
                    Gson gsonObj = new Gson();
                    String jsonStr = gsonObj.toJson(obj);
                    out.println(jsonStr);
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
        public HashMap<String, Mapping> getUrlMapping() { return MappingUrls;}
        public String getSessionConnect(){return this.SessionConnect;}

        public void setUrlMapping(HashMap<String, Mapping> urlMapping) {
            this.MappingUrls = urlMapping;
        }
        public void setSessionConnect(String session) { this.SessionConnect = session;}

}