/*
 * Sales.java
 * 
 * Created on Sep 15, 2007, 1:30:17 PM
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
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class CollectionReport extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Collection Reports","global.css","");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("collectionreports");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        } else if (login.getAccountNumber()==1) {
            // ok
        } else {
            ctr.forward("login", request, response);
            return;
        }  
        Date transDateFrom;
        String selectDateFrom=request.getParameter("selectDateFrom");
        if (selectDateFrom==null) {
            transDateFrom=ctr.TODAY();
        } else {
            transDateFrom=ctr.convertToDate(selectDateFrom);
        }        
        Date transDateTo;
        String selectDateTo=request.getParameter("selectDateTo");
        if (selectDateTo==null) {
            transDateTo=ctr.TODAY();
        } else {
            transDateTo=ctr.convertToDate(selectDateTo);
        }        

        page.startTable("stdgrid", "100%");
            page.startRow();
                page.startCell("", "1", "7", "");
                    page.text("Collection Report From "+selectDateFrom+" To "+selectDateTo, "titletext");
                page.endCell();
            page.endRow();

            model.LedgerDetail detail=null;
            model.LedgerDetails details=new model.LedgerDetails();
            
            page.startRow();
                page.startCell("", "1", "7", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "7", "");
                    page.text("Total Amount :","attribtext");
                    page.text(""+details.getTotal(transDateFrom, transDateTo,login.getAccountId()),"");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "7", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("","1","1","25%");
                    page.text("Date", "attribtext");
                page.endCell();
                page.startCell("","1","1","25%");
                    page.text("Patient Name", "attribtext");
                page.endCell();
                page.startCell("","1","1","10%");
                    page.text("Amount", "attribtext");
                page.endCell();
                page.startCell("","1","1","10%");
                    page.text("Type", "attribtext");
                page.endCell();
                page.startCell("","1","1","10%");
                    page.text("Status", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Insurance Provider", "attribtext");
                page.endCell();
                page.startCell("","1","1","10%");
                    page.text("Remarks", "attribtext");
                page.endCell();
            page.endRow();
                
            int count=details.fetch(transDateFrom, transDateTo,login.getAccountId());
            for (int i=0; i<details.size(); i++) {
                detail=(model.LedgerDetail)details.get(i);
                page.startRow();
                    page.startCell("","1","1","");
                        page.text(ctr.convertDateToString(detail.getTransDate()), "");
                    page.endCell();
                    page.startCell("","1","1","");
                        model.Patient patient=new model.Patient();
                        if (patient.fetch(detail.getPatientId(), login.getAccountId())) {
                            page.text(patient.getFirstName()+" "+patient.getLastName(), "");
                        } else {
                            page.space();
                        }
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text(""+detail.getAmount(), "");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text(detail.getType(), "");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text(detail.getStatus(), "");
                    page.endCell();
                    page.startCell("","1","1","");
                        model.InsuranceCodes code=new model.InsuranceCodes();
                        page.text(code.lookupDescription(detail.getInsuranceProviderId()), "");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text(detail.getRemarks(), "");
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
