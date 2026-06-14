/*
 * PayrollReport.java
 * 
 * Created on Oct 23, 2007, 2:53:07 PM
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
public class PayrollReport extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Payroll Reports","global.css","");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("payrollreport");
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
                    page.text("Payroll Report From "+selectDateFrom+" To "+selectDateTo, "titletext");
                page.endCell();
            page.endRow();

           
            page.startRow();
                page.startCell("", "1", "7", "");
                    page.space();
                page.endCell();
            page.endRow();
            
            DBConnectionManager db=new DBConnectionManager();
            
            page.startRow();
                page.startCell("","1","1","25%");
                    page.text("Doctor", "attribtext");
                page.endCell();
                page.startCell("","1","1","15%");
                    page.text("Days", "attribtext");
                page.endCell();
                page.startCell("","1","1","15%");
                    page.text("Rate", "attribtext");
                page.endCell();
                page.startCell("","1","1","15%");
                    page.text("Amount", "attribtext");
                page.endCell();
                page.startCell("","1","1","15%");
                    page.text("Commission", "attribtext");
                page.endCell();
                page.startCell("","1","1","15%");
                    page.text("Total", "attribtext");
                page.endCell();
                
            page.endRow();
            
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT DOCTOR, DOCTOR_ID, SUM(NUM_DAYS) AS TOT_DAYS "
                        +" FROM attendance "
                        +" WHERE ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND TRANS_DATE>=\'"+ctr.convertDateToSQLString(transDateFrom)+"\' AND TRANS_DATE<=\'"+ctr.convertDateToSQLString(transDateTo)+"\'" 
                        +" GROUP BY DOCTOR "
                        +" ORDER BY DOCTOR";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                
                while (r.next()) {
                    
                     page.startRow();
                        page.startCell("","1","1","");
                            page.text(r.getString("DOCTOR"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+r.getDouble("TOT_DAYS"), "");
                        page.endCell();
                        model.Doctor doctor=new model.Doctor();
                        doctor.fetch(r.getString("DOCTOR_ID"), login.getAccountId());
                        page.startCell("","1","1","");
                            page.text(""+doctor.getDailyRate(), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+doctor.getDailyRate()*r.getDouble("TOT_DAYS"), "");
                        page.endCell();
  

                        String sql2="SELECT ledger.*, treatment.CONTROL_NUMBER, treatmentcommissions.DOCTOR, treatmentcommissions.COMMISSION "
                                +" ,ledger.AMOUNT*treatmentcommissions.COMMISSION/100 AS COMMISSION_AMOUNT"
                                +" FROM treatmentcommissions, ledger, treatment "
                                +" WHERE treatment.TREATMENT_ID=ledger.TREATMENT_ID "
                                +" AND treatmentcommissions.TREATMENT_ID=ledger.TREATMENT_ID "
                                +" AND ledger.STATUS=\'Payment\' AND ledger.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                                +" AND ledger.TRANS_DATE>=\'"+ctr.convertDateToSQLString(transDateFrom)+"\' AND ledger.TRANS_DATE<=\'"+ctr.convertDateToSQLString(transDateTo)+"\'" 
                                +" AND treatmentcommissions.DOCTOR_ID=\'"+r.getString("DOCTOR_ID")+"\' "
                                +" ORDER BY treatmentcommissions.DOCTOR, ledger.ID";
                        Statement s2=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                        ResultSet r2=s2.executeQuery(sql2);                                  
    
                        if (r2.next()) {
                            page.startCell("","1","1","");
                                page.text(""+r2.getDouble("COMMISSION_AMOUNT"), "");
                            page.endCell();
                            page.startCell("","1","1","");
                                page.text(""+(doctor.getDailyRate()*r.getDouble("TOT_DAYS")+r2.getDouble("COMMISSION_AMOUNT")), "");
                            page.endCell();
                        } else {
                            page.startCell("","1","1","");
                                page.text("0.0", "");
                            page.endCell();
                            page.startCell("","1","1","");
                                page.text(""+doctor.getDailyRate()*r.getDouble("TOT_DAYS"), "");
                            page.endCell();
                        }
    
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
