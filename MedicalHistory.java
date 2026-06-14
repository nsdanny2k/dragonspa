/*
 * MedicalHistory.java
 * 
 * Created on Aug 25, 2007, 8:53:54 PM
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
public class MedicalHistory extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Medical & Dental History","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("medicalhistory");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }        

        model.Patient patient=new model.Patient(); 
        String save=request.getParameter("save");
        String id=request.getParameter("id");
        if ((id!=null)) {
            patient.fetch(id, login.getAccountId());
        } else {      
            page.text("Medical History", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
            page.startTable("stdgrid","200px","210px","468px","30px","4");
                page.startRow();
                    page.startCell("", "1", "1", "");
                        page.text("Id is required.","attribtext");
                    page.endCell();
                page.endRow();
            page.endTable(true);
            page.flush();
            view.finalize();            
            return;
        }  
        if ((save!=null)) {
            String alert[] = request.getParameterValues("medalert");
            model.MedicalHistory mh=new model.MedicalHistory();
            mh.reset(patient.getPartyId(), patient.getAccountId());
            if (alert!=null) {
                for (int i=0; i < alert.length; i++)
                {   
                    mh.create(ctr.convertToInt(alert[i]), patient.getPartyId(), patient.getAccountId());        
                }
            }
            page.endTable(true);
            page.flush();
            view.finalize();            
            return;

            
        }
            
// ********************************
// ** TITLE **            
// ********************************            
        page.text("Medical & Dental History", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");

        page.startTable("stdgrid","200px","175px","468px","30px","4");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Patient Profile", "extralinktext", "", "", "patientprofile?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
                //page.startCell("", "1", "1", "");
                //    page.text("Voice Recorder", "extralinktext", "mp3.jpg", "", "voicerecorder?id="+patient.getPartyId(), "20px", "20px");
                //page.endCell();
                //page.startCell("", "1", "1", "");
                //    page.text("Dental Images", "extralinktext", "", "", "dentalimages?id="+patient.getPartyId(), "20px", "20px");
                //page.endCell();
                //page.startCell("", "1", "1", "");
                //    page.text("Treatment Plans", "extralinktext", "", "", "treatmentplans?id="+patient.getPartyId(), "20px", "20px");
                //page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Treatment History", "extralinktext", "", "", "treatmenthistory?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
            page.endRow();
        page.endTable(true);             

        page.startForm("newentry", "medicalhistory", "post");
        page.startTable("stdgrid","200px","240px","468px","30px","4");
        //page.startTable("stdgrid","200px","210px","468px","30px","4");
           // page.startRow();
           //    page.startCell("extralinkcell", "1", "3", "");
           //         page.text("Ok", "attribtext", "", "", "", "30px", "30px");
           //         page.inputImage("ok", "check.jpg", "");
           //         page.text("Cancel", "attribtext", "", "", "", "30px", "30px");
           //         page.inputImage("cancel", "cross.jpg", "");
           //     page.endCell();
           // page.endRow();

            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Patient:", "attribtext");
                page.endCell();
                page.startCell("", "1", "4", "");
                    page.text(patient.getFirstName()+" "+patient.getLastName(), "valuetext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
            page.endRow();
            model.MedicalAlert alert;
            model.MedicalAlerts alerts=new model.MedicalAlerts();
            alerts.fetch("system");
            for (int i=0; i<alerts.size(); i++) {
                alert=(model.MedicalAlert)alerts.get(i);
                if ((i % 2)==0) {
                    page.startRow();
                }
                    page.startCell("", "1", "1", "50%");
                        model.MedicalHistory mh=new model.MedicalHistory();
                        boolean checked=mh.lookup(alert.getId(), patient.getPartyId(), patient.getAccountId());
                        page.inputCheckbox("medalert", ""+alert.getId(), alert.getDescription(), checked);
                    page.endCell();
                if ((i % 2)==1) {    
                    page.endRow();
                }
            }
            if (alerts.size()>0 && (alerts.size() % 2)==1 ) {
                page.endRow();
            }
            page.startRow();
                page.startCell("extralinkcell", "1", "6", "");
                    page.inputHidden("id", patient.getPartyId());
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
