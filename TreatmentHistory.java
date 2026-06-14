/*
 * TreatmentHistory.java
 * 
 * Created on Aug 25, 2007, 10:15:00 PM
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
public class TreatmentHistory extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Treatment History","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("treatmenthistory");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }      
        model.Patient patient=new model.Patient(); 
        String id=request.getParameter("id");
        if ((id!=null)) {
            patient.fetch(id, login.getAccountId());
        } else {      
            page.text("Guest History", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
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
        
// ********************************
// ** TITLE **            
// ********************************            
        page.text("Guest History", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");

        page.startTable("stdgrid","200px","175px","468px","30px","4");
            page.startRow();
                //page.startCell("", "1", "1", "");
                //    page.text("Medical & Dental History", "extralinktext", "", "", "medicalhistory", "20px", "20px");
                //page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Guest Profile", "extralinktext", "", "", "guestprofile?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
                //page.startCell("", "1", "1", "");
                //    page.text("Voice Recorder", "extralinktext", "mp3.jpg", "", "voicerecorder", "20px", "20px");
                //page.endCell();
                //page.startCell("", "1", "1", "");
                //    page.text("Dental Images", "extralinktext", "", "", "dentalimages", "20px", "20px");
                //page.endCell();
                //page.startCell("", "1", "1", "");
                //    page.text("Treatment Plans", "extralinktext", "", "", "treatmentplans", "20px", "20px");
                //page.endCell();
            page.endRow();
        page.endTable(true);             

        page.startTable("stdgrid","200px","230px","468px","3");
// ********************************
// ** TRANSACTION HEADER **            
// ********************************            
       page.startTable("stdgrid", "468px");
       page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Guest:", "attribtext");
            page.endCell();
            page.startCell("", "1", "4", "");
                page.text(patient.getFirstName()+" "+patient.getLastName(), "valuetext");
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
                /*
                page.startCell("", "1", "1", "");
                    page.text("Tooth#", "attribtext");
                page.endCell();
                 */
                page.startCell("", "1", "1", "");
                    page.text("Item", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Charge", "attribtext");
                page.endCell();
                /*
                page.startCell("", "1", "1", "");
                    page.text("Paid", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Balance", "attribtext");
                page.endCell();
                 */
            page.endRow();
            
            String pg=request.getParameter("pg");
            if (pg==null) {
                pg="1";
            }
            int bar=20;
            int size=20;
            int iPg=Integer.valueOf(pg).intValue();
            model.TreatmentDetail detail=null;
            model.TreatmentDetails details=new model.TreatmentDetails();
            int count=details.fetchHistory(patient.getPartyId(),iPg, size);
            for (int i=0; i<details.size(); i++) {
                detail=(model.TreatmentDetail)details.get(i);
                    
                page.startRow();
                    page.startCell("", "1", "1", "");
                        if (login.getAccountNumber()==1) {
                            page.text(details.lookupControlNumber(detail.getTreatmentId()), "","","","treatment?id="+detail.getTreatmentId(),"","");
                        } else {
                            page.text(details.lookupControlNumber(detail.getTreatmentId()), "","","","treatment?id="+detail.getTreatmentId()+"&RO=true","","");
                        }
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(ctr.convertDateToString(detail.getDateVisit()), "");
                    page.endCell();
                    /*
                    page.startCell("", "1", "1", "");
                        page.text(detail.getTooth(), "");
                    page.endCell();
                     */
                    page.startCell("", "1", "1", "");
                        page.text(detail.getProcedure(), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getCharge(), "");
                    page.endCell();
                    /*
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getPaid(), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getBalance(), "");
                    page.endCell();
                     */
                page.endRow(); 
            }
            
            page.startRow();
                page.startCell("", "1", "3", "");
                    if (details.getSets()>1) {
                        for (int j=1; j<=details.getSets(); j++) {
                            if (iPg==j) {
                                page.text(""+j,"pagingtext");
                            } else {
                                page.text("<a href=\"treatmenthistory?id="+patient.getPartyId()+"&pg="+j+"\">"+j+"</a>&nbsp;","extralinktext","","","","","");
                            }
                            if ((j % bar)==0) {
                                page.println("<br/>");
                            }
                        }
                    }
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
