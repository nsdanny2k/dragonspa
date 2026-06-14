/*
 * Ledger.java
 * 
 * Created on Aug 25, 2007, 5:32:55 PM
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
public class Ledger extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Ledger","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("ledger");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }        
        Date transDate;
        String selectDate=request.getParameter("selectDate");
        if (selectDate==null) {
            transDate=ctr.TODAY();
        } else {
            transDate=ctr.convertToDate(selectDate);
        }
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Ledger", "titletext", "ledger.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
// ********************************
// ** MAIN SUB MENU **            
// ********************************            
            
           page.startForm("selection", "ledger", "post"); 
           page.startTable("stdgrid","200px","175px","468px","30px","4");
                page.startRow();
                    page.startCell("","1","1","");
                        page.text("Date:", "attribtext");
                        page.inputText("selectDate", ctr.convertDateToString(transDate), "81px");
                        page.inputSubmit("select", "Select");
                    page.endCell();
                    page.startCell("extralinkcell","1","1","");
                        //page.text("Settings", "extralinktext", "", "", "settings", "", "");
                        //page.space();
                        //page.space();
                        page.text("Create a New Entry", "extralinktext", "insert.jpg", "", "newledgerentry", "35px", "35px");
                    page.endCell();
                page.endRow();
            page.endTable(true);
            page.endForm();
            
            page.startTable("boxgrid","200px","230px","472px","300px","4");
                page.startRow();
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
                
                String pg=request.getParameter("pg");
                if (pg==null) {
                    pg="1";
                }
                int bar=20;
                int size=20;
                int iPg=Integer.valueOf(pg).intValue();
                model.LedgerDetail detail=null;
                model.LedgerDetails details=new model.LedgerDetails();
                int count=details.fetch(transDate, login.getAccountId(),iPg, size);
                for (int i=0; i<details.size(); i++) {
                    detail=(model.LedgerDetail)details.get(i);
                    page.startRow();
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
                page.startRow();
                    page.startCell("", "1", "3", "");
                        if (details.getSets()>1) {
                            for (int j=1; j<=details.getSets(); j++) {
                                if (iPg==j) {
                                    page.text(""+j,"pagingtext");
                                } else {
                                    page.text("<a href=\"ledger?pg="+j+"\">"+j+"</a>&nbsp;","extralinktext","","","","","");
                                }
                                if ((j % bar)==0) {
                                    page.println("<br/>");
                                }
                            }
                        }
                    page.endCell();
                page.endRow();                
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
