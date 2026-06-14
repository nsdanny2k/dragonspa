/*
 * CommissionsReport.java
 * 
 * Created on Sep 26, 2007, 4:11:35 PM
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
public class CommissionsReport extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Commissions Reports","global.css","");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("commissionreports");
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
                    page.text("Commission Report From "+selectDateFrom+" To "+selectDateTo, "titletext");
                page.endCell();
            page.endRow();

           
            page.startRow();
                page.startCell("", "1", "7", "");
                    page.space();
                page.endCell();
            page.endRow();
            
            DBConnectionManager db=new DBConnectionManager();
            
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT treatmentcommissions.DOCTOR, SUM(ledger.AMOUNT) AS SUM_AMOUNT "
                        +" ,SUM(ledger.AMOUNT*treatmentcommissions.COMMISSION/100) AS SUM_COMMISSION_AMOUNT"
                        +" FROM treatmentcommissions, ledger "
                        +" WHERE treatmentcommissions.TREATMENT_ID=ledger.TREATMENT_ID "
                        +" AND ledger.STATUS=\'Payment\' AND ledger.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND ledger.TRANS_DATE>=\'"+ctr.convertDateToSQLString(transDateFrom)+"\' AND ledger.TRANS_DATE<=\'"+ctr.convertDateToSQLString(transDateTo)+"\'" 
                        +" GROUP BY treatmentcommissions.DOCTOR "
                        +" ORDER BY treatmentcommissions.DOCTOR";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    
                     page.startRow();
                        page.startCell("","1","1","");
                            page.text(r.getString("DOCTOR"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+r.getDouble("SUM_AMOUNT"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+r.getDouble("SUM_COMMISSION_AMOUNT"), "");
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
            page.startRow();
                page.startCell("", "1", "7", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("","1","1","10%");
                    page.text("Date", "attribtext");
                page.endCell();
                page.startCell("","1","1","10%");
                    page.text("Control Number", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Doctor", "attribtext");
                page.endCell();
                page.startCell("","1","1","30%");
                    page.text("Remarks", "attribtext");
                page.endCell();
                page.startCell("","1","1","10%");
                    page.text("Amount", "attribtext");
                page.endCell();
                page.startCell("","1","1","10%");
                    page.text("Percent", "attribtext");
                page.endCell();
                page.startCell("","1","1","10%");
                    page.text("Commission", "attribtext");
                page.endCell();
            page.endRow();

            //WebController ctr=new WebController();
            //DBConnectionManager db=new DBConnectionManager();
            
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT ledger.*, treatment.CONTROL_NUMBER, treatmentcommissions.DOCTOR, treatmentcommissions.COMMISSION "
                        +" ,ledger.AMOUNT*treatmentcommissions.COMMISSION/100 AS COMMISSION_AMOUNT"
                        +" FROM treatmentcommissions, ledger, treatment "
                        +" WHERE treatment.TREATMENT_ID=ledger.TREATMENT_ID "
                        +" AND treatmentcommissions.TREATMENT_ID=ledger.TREATMENT_ID "
                        +" AND ledger.STATUS=\'Payment\' AND ledger.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND ledger.TRANS_DATE>=\'"+ctr.convertDateToSQLString(transDateFrom)+"\' AND ledger.TRANS_DATE<=\'"+ctr.convertDateToSQLString(transDateTo)+"\'" 
                        +" ORDER BY treatmentcommissions.DOCTOR, ledger.ID";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    
                     page.startRow();
                        page.startCell("","1","1","");
                            page.text(ctr.convertDateToString(r.getDate("TRANS_DATE")), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+r.getInt("CONTROL_NUMBER"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(r.getString("DOCTOR"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(r.getString("REMARKS"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+r.getDouble("AMOUNT"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+r.getDouble("COMMISSION"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+r.getDouble("COMMISSION_AMOUNT"), "");
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
