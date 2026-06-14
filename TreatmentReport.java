/*
 * TreatmentReport.java
 * 
 * Created on Oct 23, 2007, 3:54:39 PM
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
public class TreatmentReport extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Treatment Reports","global.css","");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("treatmentreports");
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
                    page.text("Treatment Report From "+selectDateFrom+" To "+selectDateTo, "titletext");
                page.endCell();
            page.endRow();

           
            page.startRow();
                page.startCell("", "1", "4", "");
                    page.space();
                page.endCell();
            page.endRow();
            
            DBConnectionManager db=new DBConnectionManager();

            page.startRow();
                    page.startCell("","1","1","");
                        page.text("Dental Unit", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text("Patient Name", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text("Doctor", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text("Time-in", "attribtext");
                    page.endCell();
            page.endRow();

            
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT * "
                        +" FROM treatment"
                        +" WHERE ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND DATE_VISIT>=\'"+ctr.convertDateToSQLString(transDateFrom)+"\' AND DATE_VISIT<=\'"+ctr.convertDateToSQLString(transDateTo)+"\'" 
                        +" ORDER BY TIME_FROM";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    
                     page.startRow();
                        page.startCell("","1","1","");
                            page.text(r.getString("DENTAL_UNIT"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(r.getString("PATIENT"), "", "", "","treatment?id="+r.getInt("TREATMENT_ID"),"","");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(r.getString("DOCTOR"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(ctr.convertTimeStampToString(r.getTimestamp("TIME_FROM")), "");
                        page.endCell();                        
                    page.endRow();                   
                    
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
