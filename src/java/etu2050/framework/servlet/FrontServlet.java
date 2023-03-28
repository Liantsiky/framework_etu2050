/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package etu2050.framework.servlet;

import etu2050.framework.myutils.MyUtils;
import etu2050.framework.Mapping;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

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
//           ArrayList <Class<?>> test= MyUtils.getClasses("etu2050.framework.models", this.getUrlMapping());
//           for(int i=0; i< getUrlMapping().size(); i++){
//               System.out.println(MappingUrls);
//           }
//            Mapping map =  getUrlMapping().get(url);
//            out.println(map.getMethod());
        }catch(IOException | ClassNotFoundException | URISyntaxException e){
//            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, e);
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
            
//        MyUtils.getClasses("etu2050", this.getUrlMapping());
        
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
//            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
// getters and setters    
     public HashMap<String, Mapping> getUrlMapping() {
        return MappingUrls;
    }

    public void setUrlMapping(HashMap<String, Mapping> urlMapping) {
        this.MappingUrls = urlMapping;
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
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
