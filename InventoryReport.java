/*
 * InventoryReport.java
 * 
 * Created on Oct 17, 2007, 3:11:07 PM
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

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class InventoryReport extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Inventory","global.css","");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("inventoryreport");
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
                    page.text("Inventory "+ctr.convertDateToString(ctr.TODAY()), "titletext");
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
                    page.text("Item", "attribtext");
                page.endCell();
                page.startCell("","1","1","50%");
                    page.text("Description", "attribtext");
                page.endCell();
                page.startCell("","1","1","25%");
                    page.text("Quantity", "attribtext");
                page.endCell();
            page.endRow();

            //WebController ctr=new WebController();
            //DBConnectionManager db=new DBConnectionManager();
            
            try {
                int i=0;
                Connection c=db.getConnection();
                String sql="SELECT * FROM itemcode WHERE ACCOUNT_ID = \'"+login.getAccountId()+"\'ORDER BY CODE";
                Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                ResultSet r=s.executeQuery(sql);
                SQLHelper h=new SQLHelper();        
                while (r.next()) {

                    String sql2="SELECT SUM(QTY_IN-QTY_OUT) AS QTY"
                             +" FROM inventory"
                             +" WHERE ITEM_ID = "+r.getString("ID")
                             +" GROUP BY ITEM_ID";
                    Statement s2=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                    ResultSet r2=s2.executeQuery(sql2);
                    
                    if (r2.next()) {
                        page.startRow();
                        page.startCell("","1","1","");
                            page.text(r.getString("CODE"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(r.getString("DESCRIPTION"), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(""+r2.getDouble("QTY"), "");
                        page.endCell();
                        page.endRow();                   
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
