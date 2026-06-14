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
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class DateRange extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Date Range","global.css");
        

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
        String selectDate=request.getParameter("selectDate");
        if (selectDate==null) {
            transDate=ctr.TODAY();
        } else {
            transDate=ctr.convertToDate(selectDate);
        }
        String frm=request.getParameter("frm");
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Date Range", "titletext", "ledger.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
// ********************************
// ** MAIN SUB MENU **            
// ********************************            
            
           page.startForm("selection", frm, "post"); 
           page.startTable("stdgrid","200px","175px","468px","30px","4");
                page.startRow();
                    page.startCell("","1","1","");
                        page.text("Date From:", "attribtext");
                        page.inputText("selectDateFrom", ctr.convertDateToString(transDate), "81px");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text("Date To:", "attribtext");
                        page.inputText("selectDateTo", ctr.convertDateToString(transDate), "81px");
                    page.endCell();
                    page.startCell("","1","1","");
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
