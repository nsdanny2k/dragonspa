/*
 * MedicalAlerts.java
 * 
 * Created on Sep 13, 2007, 3:44:00 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dentalworx.controllers;

import com.dentalworx.view.PageView;
import com.ownx.util.WebController;
import com.ownx.webpages.Page;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class MedicalAlerts extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("medicalalerts");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }   
        
        Page page;
        if (login.getAccountId().equals("system")) {
            page=view.initialize("Medical Alerts","global.css","System");    
        } else {
            page=view.initialize("Medical Alerts","global.css");
        }        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Medical Alerts", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
// ********************************
// ** MAIN SUB MENU **            
// ********************************            
            page.startTable("stdgrid","200px","175px","468px","30px","4");
                page.startRow();
                    page.startCell("extralinkcell","1","1","");
                        //page.text("Settings", "extralinktext", "", "", "settings", "", "");
                        //page.space();
                        //page.space();
                        page.text("New Alert", "extralinktext", "insert.jpg", "", "medicalalert?command=2", "35px", "35px");
                    page.endCell();
                page.endRow();
            page.endTable(true);
            
            page.startTable("boxgrid","200px","220px","468px","3");
                page.startRow();
                    page.startCell("", "1", "1", "");
                        page.text("Description", "attribtext", "", "", "", "", "");
                    page.endCell();
                page.endRow();
                
                String pg=request.getParameter("pg");
                if (pg==null) {
                    pg="1";
                }
                int bar=20;
                int size=20;
                int iPg=Integer.valueOf(pg).intValue();
                model.MedicalAlert code=null;
                model.MedicalAlerts codes=new model.MedicalAlerts();
                int count=codes.fetch(login.getAccountId(),iPg, size);
                for (int i=0; i<codes.size(); i++) {
                    code=(model.MedicalAlert)codes.get(i);
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text(code.getDescription(), "extralinktext", "", "", "medicalalert?id="+code.getId()+"&pg="+pg, "", "");
                        page.endCell();
                    page.endRow();                
                }                
                
                page.startRow();
                    page.startCell("", "1", "2", "");
                        if (codes.getSets()>1) {
                            for (int j=1; j<=codes.getSets(); j++) {
                                if (iPg==j) {
                                    page.text(""+j,"pagingtext");
                                } else {
                                    page.text("<a href=\"medicalalerts?pg="+j+"\">"+j+"</a>&nbsp;","extralinktext","","","","","");
                                }
                                if ((j % bar)==0) {
                                    page.println("<br/>");
                                }
                            }
                        }
                    page.endCell();
                page.endRow();
                page.endTable(false);
            
        page.flush();
        view.finalize();
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
