/*
 * Reports.java
 * 
 * Created on Aug 25, 2007, 5:33:21 PM
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
public class Reports extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Reports","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("reports");
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
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Reports", "titletext", "reports.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");

            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Reports", "attribtext");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.space();
                        page.space();
                        page.text("Service Report", "","","","daterange?frm=servicereport","","");
                    page.endCell();
                page.endRow();                 
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.space();
                        page.space();
                        page.text("Sales Report", "","","","datetimerange?frm=salesreport","","");
                    page.endCell();
                page.endRow();   
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.space();
                        page.space();
                        page.text("Sales Report By Cutoff", "","","","datecutoff?frm=salesreportcutoff","","");
                    page.endCell();
                page.endRow();   
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.space();
                        page.space();
                        page.text("Customer Sales Report", "","","","daterange?frm=customersalesreport","","");
                    page.endCell();
                page.endRow();   
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.space();
                        page.space();
                        page.text("Therapist Report", "","","","daterange?frm=therapistreport","","");
                    page.endCell();
                page.endRow();   

                /*
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.space();
                        page.space();
                        page.text("Customer Balance Report", "","","","balancereport","","");
                    page.endCell();
                page.endRow();  
                if (login.getExtended()==1) {
                    page.startRow();
                        page.startCell("","1","1","25%");
                            page.space();
                            page.space();
                            page.text("Commissions Report", "","","","daterange?frm=commissionsreport","","");
                        page.endCell();
                    page.endRow();  
                    page.startRow();
                        page.startCell("","1","1","25%");
                            page.space();
                            page.space();
                            page.text("Inventory Report", "","","","inventoryreport","","");
                        page.endCell();
                    page.endRow();  
                    page.startRow();
                        page.startCell("","1","1","25%");
                            page.space();
                            page.space();
                            page.text("Attendance Report", "","","","daterange?frm=attendancereport","","");
                        page.endCell();
                    page.endRow();  
                    page.startRow();
                        page.startCell("","1","1","25%");
                            page.space();
                            page.space();
                            page.text("Payroll Report", "","","","daterange?frm=payrollreport","","");
                        page.endCell();
                    page.endRow();                  
                }
                 */
                
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
