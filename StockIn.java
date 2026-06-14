/*
 * StockIn.java
 * 
 * Created on Oct 17, 2007, 2:45:16 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dentalworx.controllers;

import com.dentalworx.view.PageView;
import com.ownx.util.WebController;
import com.ownx.webpages.ListBox;
import com.ownx.webpages.ListBoxItem;
import com.ownx.webpages.Page;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class StockIn extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Inventory In","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("stockin");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }       
        
        model.InventoryItem detail=new model.InventoryItem();
        detail.add();
        
        String save=request.getParameter("save");
        if (save!=null) {
            detail.setItemId(ctr.convertToInt(request.getParameter("itemId")));
            detail.setTreatmentId(0);
            detail.setQtyOut(0);
            detail.setQtyIn(ctr.convertToDouble(request.getParameter("qtyIn")));
            if (detail.save()) {
                page.text("Inventory In", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
                page.startTable("stdgrid","200px","210px","468px","30px","4");
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text("Record successfully saved.","attribtext");
                        page.endCell();
                    page.endRow();
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text("Add another entry","","insert.jpg","","stockin","25px","25px");
                        page.endCell();
                    page.endRow();
                page.endTable(true);
                page.flush();
                view.finalize();
                return;
            }
        }
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Inventory In", "titletext", "ledger.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");

            page.startForm("newentry", "stockin", "post");
        
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Item Code:", "attribtext");
                    page.endCell();
                    
                    page.startCell("","1","1","75%");
                        ListBox listbox=new ListBox();
                        ListBoxItem item;
                        model.ItemCode code;
                        model.ItemCodes codes=new model.ItemCodes();
                        codes.fetch(login.getAccountId());
                        //codes.fetch("system");
                        for (int i=0; i<codes.size(); i++) {
                            code=(model.ItemCode)codes.get(i);   
                            item=new ListBoxItem(""+code.getId(),code.getDescription());
                            listbox.add(item);
                        }
                        page.inputSelect("itemId",""+detail.getItemId(), listbox,"300px"); //155px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Qty In:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        page.inputText("qtyIn",""+detail.getQtyIn(), "");
                    page.endCell();
                page.endRow();                
                page.startRow();
                    page.startCell("extralinkcell", "1", "2", "");
                        page.inputHidden("command", "1");
                        page.inputSubmit("save", "Save Record");
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
