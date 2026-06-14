/*
 * SalesParam.java
 * 
 * Created on Sep 15, 2007, 1:25:42 PM
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

import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class DateCutoff extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Cutoff Day","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("daterange");
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
        Date transDate;
        String CutOffTime="6:00 AM";
        String selectDate=request.getParameter("selectDate");
        if (selectDate==null) {
            //transDateFrom=ctr.TIMESTAMP();
            transDate=ctr.TODAY();
        } else {
            transDate=ctr.convertToDate(selectDate);
        }
        String selectTimeCutOff=request.getParameter("selectTimeCutoff");
        if (selectTimeCutOff==null) {
            selectTimeCutOff=CutOffTime;
        }


        
        String frm=request.getParameter("frm");
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Cutoff Day", "titletext", "ledger.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
// ********************************
// ** MAIN SUB MENU **            
// ********************************            
            
           page.startForm("selection", frm, "post"); 
//            page.startForm("selection", "datecutoff", "post"); 
           page.startTable("stdgrid","200px","175px","468px","30px","4");
                page.startRow();
                    page.startCell("","1","1","");
                        page.text("Date:", "attribtext");
                        page.inputText("selectDate", ctr.convertDateToString(transDate), "151px");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text("Time Cutoff:", "attribtext");
                        page.inputText("selectTimeCutoff", CutOffTime, "151px");
                    page.endCell();
                    page.startCell("","1","1","");
                        //page.inputHidden("selectDateFrom",ctr.convertTimeStampToString(transDateFrom));
                        //page.inputHidden("selectDateTo",ctr.convertTimeStampToString(transDateTo));
                        page.inputSubmit("select", "Select");
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
