/*
 * VoiceRecorder.java
 * 
 * Created on Aug 25, 2007, 8:49:21 PM
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
public class VoiceRecorder extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Voice Recorder","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("voicerecorder");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Voice Recorder", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");

        page.startTable("stdgrid","200px","175px","468px","30px","4");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Dental Images", "extralinktext", "", "", "dentalimages", "20px", "20px");
                page.endCell();
                //page.startCell("", "1", "1", "");
                //    page.text("Treatment Plans", "extralinktext", "", "", "treatmentplans", "20px", "20px");
                //page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Treatment History", "extralinktext", "", "", "treatmenthistory", "20px", "20px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Medical & Dental History", "extralinktext", "", "", "medicalhistory", "20px", "20px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Patient Profile", "extralinktext", "", "", "patientprofile", "20px", "20px");
                page.endCell();
            page.endRow();
        page.endTable(true);             

        page.startForm("newentry", "voicerecorder", "post");
        page.startTable("stdgrid","200px","220px","468px","30px","4");
                page.startRow();
                    page.startCell("extralinkcell", "1", "4", "");
                        //page.text("Save Record", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("save", "saveit.jpg", "");
                        page.inputSubmit("save", "Save Record");
                    page.endCell();
                page.endRow();  
        page.endTable(true);
        page.endForm();
        
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
