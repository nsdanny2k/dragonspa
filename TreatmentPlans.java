/*
 * TreatmentPlans.java
 * 
 * Created on Aug 25, 2007, 8:52:14 PM
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
public class TreatmentPlans extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Treatment Plans","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("treatmentplans");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Treatment Plans", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");

        page.startTable("stdgrid","200px","175px","468px","30px","4");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Treatment History", "extralinktext", "", "", "treatmenthistory", "20px", "20px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Medical & Dental History", "extralinktext", "", "", "medicalhistory", "20px", "20px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Patient Profile", "extralinktext", "", "", "patientprofile", "20px", "20px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Voice Recorder", "extralinktext", "mp3.jpg", "", "voicerecorder", "20px", "20px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Dental Images", "extralinktext", "", "", "dentalimages", "20px", "20px");
                page.endCell();
            page.endRow();
        page.endTable(true);             
            
        page.startTable("stdgrid","200px","230px","468px","3");
// ********************************
// ** TRANSACTION HEADER **            
// ********************************            
       page.startTable("stdgrid", "468px");
       page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Patient:", "attribtext");
            page.endCell();
            page.startCell("", "1", "4", "");
                page.text("Michael Douglas", "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.space();
            page.endCell();
       page.endRow();
       page.endTable(false);
// ********************************
// ** TRANSACTION DETAIL **            
// ********************************            
        page.startTable("boxgrid", "468px");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Ctrl#", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Date#", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Tooth#", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Procedure", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Charge", "attribtext");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("1251", "");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("6-25-2007", "");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("1", "");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("crown filling", "");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("200.00", "");
                page.endCell();
            page.endRow(); 
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("1251", "");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("6-25-2007", "");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("2", "");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("root canal", "");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("300.00", "");
                page.endCell();
            page.endRow(); 
            page.startRow();
                page.startCell("", "1", "7", "");
                    page.text("<a href=\"treatmentplans\">1</a>&nbsp;<a href=\"treatmentplans\">2</a>&nbsp;<a href=\"treatmentplans\">3</a>&nbsp;<a href=\"treatmentplans\">4</a>&nbsp;<a href=\"treatmentplans\">5</a>&nbsp;","extralinktext","","","","","");
                page.endCell();
            page.endRow();                
        page.endTable(false);
        
        page.endTable(true);         
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
