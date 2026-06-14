/*
 * Attendants.java
 * 
 * Created on Feb 4, 2008, 3:29:42 PM
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
public class Attendants extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       PageView view=new PageView(request,response);
        Page page=view.initialize("Attendants","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("attendants");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Therapist", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
// ********************************
// ** MAIN SUB MENU **            
// ********************************            
            page.startTable("stdgrid","200px","175px","468px","30px","4");
                page.startRow();
                    page.startCell("extralinkcell","1","1","");
                        //page.text("Settings", "extralinktext", "", "", "settings", "", "");
                        //page.space();
                        //page.space();
                        page.text("New Therapist", "extralinktext", "insert.jpg", "", "attendant?command=2", "35px", "35px");
                    page.endCell();
                page.endRow();
            page.endTable(true);

            page.startTable("boxgrid","200px","220px","468px","3");
                page.startRow();
                    page.startCell("", "1", "1", "");
                        page.text("Name", "attribtext", "", "", "", "", "");
                    page.endCell();
                page.endRow();
                
                model.Doctor doctor;
                model.Doctors doctors=new model.Doctors();
                int count=doctors.fetch(login.getAccountId());
                for (int i=0; i<doctors.size(); i++) {
                    doctor=(model.Doctor)doctors.get(i);
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text(doctor.getFirstName()+" "+doctor.getLastName()+" "+doctor.getMiddleName(), "extralinktext", "", "", "attendant?id="+doctor.getPartyId(), "", "");
                        page.endCell();
                    page.endRow();                
                }                
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
