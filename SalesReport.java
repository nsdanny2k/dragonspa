/*
 * SalesReport.java
 * 
 * Created on Feb 4, 2008, 6:09:49 PM
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
import java.sql.Timestamp;
import java.sql.Timestamp;

import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author nsdan2k
 */
public class SalesReport extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Sales Reports","global.css","");
        
        
        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("salesreports");
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

        //double COMMRATE = 300.0;
        double COMMRATE = ctr.convertToDouble(login.getCommission());
        
        Timestamp transDateFrom;
        //Date transDateFrom;
        String selectDateFrom=request.getParameter("selectDateFrom");
        if (selectDateFrom==null) {
            transDateFrom=ctr.TIMESTAMP();
            //transDateFrom=ctr.TODAY();
        } else {
            transDateFrom=ctr.convertToTimestamp(selectDateFrom);  
            //transDateFrom=ctr.convertToTime(ctr.convertDateToString( ctr.TODAY())+" 24:00:00");  
            //transDateFrom=ctr.convertToDate(selectDateFrom);
        }        
        
        Timestamp transDateTo;
        //Date transDateTo;
        String selectDateTo=request.getParameter("selectDateTo");
        if (selectDateTo==null) {
            transDateTo=ctr.TIMESTAMP();
            //transDateTo=ctr.TODAY();
        } else {
            transDateTo=ctr.convertToTimestamp(selectDateTo);  
            //transDateTo=ctr.convertToTime(selectDateTo);
            //transDateTo=ctr.convertToDate(selectDateTo);
        }        

        page.startTable("stdgrid", "100%");
            page.startRow();
                page.startCell("", "1", "5", "");
                    //page.text("Sales Report From "+selectDateFrom+" To "+selectDateTo, "titletext");
                page.text("Sales Report From "+selectDateFrom+" To "+selectDateTo, "titletext");
                page.endCell();
            page.endRow();

           
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.space();
                page.endCell();
            page.endRow();
            
            DBConnectionManager db=new DBConnectionManager();

            /*
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.text("Total Cash Amount :","attribtext");
                page.endCell();
            page.endRow();
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT SUM(treatment_entry.CHARGE) AS TOTCHARGE, treatment.DATE_VISIT "
                        +" FROM treatment, treatment_entry"
                        +" WHERE treatment.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND treatment.DATE_VISIT>=\'"+ctr.convertTimestampToSQLString(transDateFrom)+"\' AND treatment.DATE_VISIT<=\'"+ctr.convertTimestampToSQLString(transDateTo)+"\'" 
                        +" AND treatment.TREATMENT_ID=treatment_entry.TREATMENT_ID"
                        +" AND NULLIF(treatment.U1,'') IS NULL"
                        +" GROUP BY treatment.DATE_VISIT"
                        +" ORDER BY treatment.DATE_VISIT";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text("   "+ctr.convertDateToString(r.getDate("DATE_VISIT")),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+r.getDouble("TOTCHARGE"),"");
                        page.endCell();
                        page.startCell("", "1", "3", "");
                            page.text("","");
                        page.endCell();
                    page.endRow();
                }
            } catch (Exception e) {
                    page.startRow();
                        page.startCell("","1","5","");
                            page.text(e.getMessage(), "");
                        page.endCell();
                    page.endRow();
            }            

            page.startRow();
                page.startCell("", "1", "5", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.text("Total Card Amount :","attribtext");
                page.endCell();
            page.endRow();
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT SUM(treatment_entry.CHARGE) AS TOTCHARGE, treatment.DATE_VISIT "
                        +" FROM treatment, treatment_entry"
                        +" WHERE treatment.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND treatment.DATE_VISIT>=\'"+ctr.convertTimestampToSQLString(transDateFrom)+"\' AND treatment.DATE_VISIT<=\'"+ctr.convertTimestampToSQLString(transDateTo)+"\'" 
                        +" AND treatment.TREATMENT_ID=treatment_entry.TREATMENT_ID"
                        +" AND NULLIF(treatment.U1,'') IS NOT NULL"
                        +" GROUP BY treatment.DATE_VISIT"
                        +" ORDER BY treatment.DATE_VISIT";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text("   "+ctr.convertDateToString(r.getDate("DATE_VISIT")),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+r.getDouble("TOTCHARGE"),"");
                        page.endCell();
                        page.startCell("", "1", "2", "");
                            page.text("","");
                        page.endCell();
                    page.endRow();
                }
            } catch (Exception e) {
                    page.startRow();
                        page.startCell("","1","5","");
                            page.text(e.getMessage(), "");
                        page.endCell();
                    page.endRow();
            }            

            page.startRow();
                page.startCell("", "1", "5", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.text("Total Commission:","attribtext");
                page.endCell();
            page.endRow();
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT SUM(treatment_entry.CHARGE) AS TOTCHARGE, treatment.DATE_VISIT, COUNT(treatment.ACCOUNT_ID) AS NUMTRANS  "
                        +" FROM treatment, treatment_entry"
                        +" WHERE treatment.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND treatment.DATE_VISIT>=\'"+ctr.convertTimestampToSQLString(transDateFrom)+"\' AND treatment.DATE_VISIT<=\'"+ctr.convertTimestampToSQLString(transDateTo)+"\'" 
                        +" AND treatment.TREATMENT_ID=treatment_entry.TREATMENT_ID"
                        +" GROUP BY treatment.DATE_VISIT"
                        +" ORDER BY treatment.DATE_VISIT";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text("   "+ctr.convertDateToString(r.getDate("DATE_VISIT")),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+(r.getInt("NUMTRANS")*COMMRATE),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+r.getInt("NUMTRANS"),"");
                        page.endCell();
                        page.startCell("", "1", "3", "");
                            page.text("","");
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
            */
                    
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.text("TOTAL NET AMOUNT :","attribtext");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("","1","1","20%");
                    page.text("Date", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Cash", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Card", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Commision", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Net Cash", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Count", "attribtext");
                page.endCell();

            page.endRow();            
            try {
                int i=0;
                Connection c=db.getConnection();
                //String sql="SELECT SUM(treatment_entry.CHARGE - IF((NULLIF(treatment.U1,'') IS NULL),0.0,treatment_entry.CHARGE) - "+COMMRATE+" ) AS TOTCHARGE, treatment.DATE_VISIT "
                String sql="SELECT SUM( treatment_entry.CHARGE ) AS TOTCASH, SUM("+COMMRATE+") AS TOTCOMM, SUM(IF((NULLIF(treatment.U1,'') IS NULL),0.0,treatment_entry.CHARGE)) AS TOTCARD, treatment.DATE_VISIT, COUNT(treatment.ACCOUNT_ID) AS NUMTRANS "
                        +" FROM treatment, treatment_entry"
                        +" WHERE treatment.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND treatment.TIME_FROM>=\'"+ctr.convertTimestampToSQLString(transDateFrom)+"\' AND treatment.TIME_FROM<=\'"+ctr.convertTimestampToSQLString(transDateTo)+"\'" 
                        +" AND treatment.TREATMENT_ID=treatment_entry.TREATMENT_ID"
                        +" GROUP BY treatment.DATE_VISIT"
                        +" ORDER BY treatment.DATE_VISIT";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text("   "+ctr.convertDateToString(r.getDate("DATE_VISIT")),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+(r.getDouble("TOTCASH")-r.getDouble("TOTCARD")),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+(r.getDouble("TOTCARD")),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+(r.getDouble("TOTCOMM")),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+(r.getDouble("TOTCASH")-r.getDouble("TOTCARD")-r.getDouble("TOTCOMM")),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+(r.getInt("NUMTRANS")),"");
                        page.endCell();
                        page.startCell("", "1", "3", "");
                            page.text("","");
                        page.endCell();
                    page.endRow();
                }
            } catch (Exception e) {
                    page.startRow();
                        page.startCell("","1","5","");
                            page.text(e.getMessage(), "");
                        page.endCell();
                    page.endRow();
            }            

            
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.text("Total Commission By Therapist:","attribtext");
                page.endCell();
            page.endRow();
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT SUM(treatment_entry.CHARGE) AS TOTCHARGE, treatment.DOCTOR, COUNT(treatment.ACCOUNT_ID) AS NUMTRANS  "
                        +" FROM treatment, treatment_entry"
                        +" WHERE treatment.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND treatment.TIME_FROM>=\'"+ctr.convertTimestampToSQLString(transDateFrom)+"\' AND treatment.TIME_FROM<=\'"+ctr.convertTimestampToSQLString(transDateTo)+"\'" 
                        +" AND treatment.TREATMENT_ID=treatment_entry.TREATMENT_ID"
                        +" GROUP BY treatment.Doctor"
                        +" ORDER BY TOTCHARGE DESC";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text("   "+r.getString("DOCTOR"),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+(r.getInt("NUMTRANS")*COMMRATE),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+r.getInt("NUMTRANS"),"");
                        page.endCell();
                        page.startCell("", "1", "2", "");
                            page.text("","");
                        page.endCell();
                    page.endRow();
                }
            } catch (Exception e) {
                    page.startRow();
                        page.startCell("","1","5","");
                            page.text(e.getMessage(), "");
                        page.endCell();
                    page.endRow();
            }            

            
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.space();
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.text("Total By Item :","attribtext");
                page.endCell();
            page.endRow();
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT SUM(treatment_entry.CHARGE) AS TOTCHARGE, treatment_entry.PROCEDURE, COUNT(treatment.ACCOUNT_ID) AS NUMTRANS "
                        +" FROM treatment, treatment_entry"
                        +" WHERE treatment.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND treatment.TIME_FROM>=\'"+ctr.convertTimestampToSQLString(transDateFrom)+"\' AND treatment.TIME_FROM<=\'"+ctr.convertTimestampToSQLString(transDateTo)+"\'" 
                        +" AND treatment.TREATMENT_ID=treatment_entry.TREATMENT_ID"
                        +" GROUP BY treatment_entry.PROCEDURE"
                        +" ORDER BY TOTCHARGE DESC";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text("   "+r.getString("PROCEDURE"),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+r.getDouble("TOTCHARGE"),"");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(""+r.getInt("NUMTRANS"),"");
                        page.endCell();
                        page.startCell("", "1", "2", "");
                            page.text("","");
                        page.endCell();
                    page.endRow();
                }
            } catch (Exception e) {
                    page.startRow();
                        page.startCell("","1","5","");
                            page.text(e.getMessage(), "");
                        page.endCell();
                    page.endRow();
            }            
            
            page.startRow();
                page.startCell("", "1", "5", "");
                    page.space();
                page.endCell();
            page.endRow();            
            page.startRow();
                page.startCell("","1","1","20%");
                    page.text("Date", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Guest", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Therapist", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Item", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Amount", "attribtext");
                page.endCell();
                page.startCell("","1","1","20%");
                    page.text("Credit Card", "attribtext");
                page.endCell();

            page.endRow();

            
            try {
                int i=0;
                Connection c=db.getConnection();

                String sql="SELECT treatment_entry.CHARGE, treatment_entry.PROCEDURE, treatment.* "
                        +" FROM treatment, treatment_entry"
                        +" WHERE treatment.ACCOUNT_ID=\'"+login.getAccountId()+"\'"
                        +" AND treatment.TIME_FROM>=\'"+ctr.convertTimestampToSQLString(transDateFrom)+"\' AND treatment.TIME_FROM<=\'"+ctr.convertTimestampToSQLString(transDateTo)+"\'"                         +" AND treatment.TREATMENT_ID=treatment_entry.TREATMENT_ID"
                        +" AND treatment.TREATMENT_ID=treatment_entry.TREATMENT_ID"
                        +" ORDER BY TIME_FROM";
                
                //DEBUG HERE ====================================================               
                //transDateTo=ctr.convertToTimestamp(selectDateTo);       
                ////page.text(selectDateFrom, "");
                //page.text(ctr.convertTimeStampToString(transDateFrom), "");
                //page.text(ctr.convertTimestampToSQLString(transDateFrom), "");
                //page.text(" ","");
                //page.text(ctr.convertTimeStampToString(transDateTo), "");
                //page.text(ctr.convertTimestampToSQLString(transDateTo), "");
                
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {
                    
                     page.startRow();
                        page.startCell("","1","1","");
                            //page.text(ctr.convertDateToString(r.getDate("DATE_VISIT")), "");
                        page.text(ctr.convertTimeStampToString(r.getTimestamp("TIME_FROM")), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(r.getString("PATIENT"), "", "", "","room?id="+r.getInt("TREATMENT_ID"),"","");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(r.getString("DOCTOR"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(r.getString("PROCEDURE"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+r.getDouble("CHARGE"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(r.getString("U1"), "");
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
