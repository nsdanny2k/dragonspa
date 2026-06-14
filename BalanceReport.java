/*
 * BalanceReport.java
 * 
 * Created on Oct 1, 2007, 3:35:52 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dentalworx.controllers;

import com.dentalworx.view.PageView;
import com.ownx.db.SQLHelper;
import com.ownx.util.WebController;
import com.ownx.webpages.Page;
import general.DBConnectionManager;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class BalanceReport extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Customer Balance","global.css","");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("balancereport");
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

        page.startTable("stdgrid", "100%");
            page.startRow();
                page.startCell("", "1", "4", "");
                    page.text("Customer Balance "+ctr.convertDateToString(ctr.TODAY()), "titletext");
                page.endCell();
            page.endRow();

           
            page.startRow();
                page.startCell("", "1", "4", "");
                    page.space();
                page.endCell();
            page.endRow();
            
            DBConnectionManager db=new DBConnectionManager();
           
            page.startRow();
                page.startCell("","1","1","25%");
                    page.text("Patient", "attribtext");
                page.endCell();
                page.startCell("","1","1","25%");
                    page.text("Date", "attribtext");
                page.endCell();
                page.startCell("","1","1","25%");
                    page.text("Balance", "attribtext");
                page.endCell();
                page.startCell("","1","1","25%");
                    page.text("Contact", "attribtext");
                page.endCell();
            page.endRow();

            //WebController ctr=new WebController();
            //DBConnectionManager db=new DBConnectionManager();
            
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT * FROM patient WHERE ACCOUNT_ID = \'"+login.getAccountId()+"\'ORDER BY LAST_NAME, FIRST_NAME, MIDDLE_NAME";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {

                    String sql2="SELECT TREATMENT.PATIENT, TREATMENT.DATE_VISIT AS TRANS_DATE, TREATMENT_ENTRY.BALANCE"
                             +" FROM TREATMENT, TREATMENT_ENTRY"
                             +" WHERE TREATMENT.TREATMENT_ID=TREATMENT_ENTRY.TREATMENT_ID"
                             +" AND TREATMENT.PATIENT_ID = "+r.getString("PARTY_ID")
                             +" ORDER BY TREATMENT_ENTRY.ID DESC";
                    Statement s2=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                    ResultSet r2=s2.executeQuery(sql2);
                    
                    if (r2.next()) {
                        if (r2.getDouble("BALANCE")!=0) {
                            page.startRow();
                            page.startCell("","1","1","");
                                page.text(r2.getString("PATIENT"), "");
                            page.endCell();
                            page.startCell("","1","1","");
                                page.text(ctr.convertDateToString(r2.getDate("TRANS_DATE")), "");
                            page.endCell();
                            page.startCell("","1","1","");
                                page.text(""+r2.getDouble("BALANCE"), "");
                            page.endCell();
                            page.startCell("","1","1","");
                                String contact=r.getString("HOME_PHONE_NUMBER")+" "+r.getString("OFFICE_PHONE_NUMBER")+" "+r.getString("MOBILE_NUMBER");
                                contact=contact.trim();
                                page.text(contact, "");
                            page.endCell();
                            page.endRow();                   
                        }
                    }    
                    
                    
                }
            } catch (Exception e) {
                    page.startRow();
                        page.startCell("","1","1","");
                            page.text(e.getMessage(), "");
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
