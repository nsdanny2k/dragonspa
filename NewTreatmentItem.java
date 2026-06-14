/*
 * NewTreatmentItem.java
 * 
 * Created on Oct 17, 2007, 2:06:03 PM
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
public class NewTreatmentItem extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Treatment Item","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("newtreatmentitem");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }      
        
        model.InventoryItem detail=new model.InventoryItem();
        model.ItemCodes ic=new model.ItemCodes();
        
        String frm=request.getParameter("frm");
        String rec=request.getParameter("rec");
        String save=request.getParameter("save");
        if ((save==null)) {
            detail.add();
        } else {
        }
        
        if ((save!=null)) {
           
            boolean validated= true; //(rec!=null && request.getParameter("diagnosisId")!=null && request.getParameter("procedureId")!=null);
            if (validated) {
                detail.setTreatmentId(ctr.convertToInt(rec));
                detail.setItemId(ctr.convertToInt(request.getParameter("itemId")));
                detail.setQtyIn(0);
                detail.setQtyOut(ctr.convertToDouble(request.getParameter("qtyOut")));

                if (detail.save()) {
                    ctr.forward(frm+"?id="+rec, request, response);
                    return;
                    /*
                    page.text("Treatment Entry", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("Record successfully saved.","attribtext");
                            page.endCell();
                        page.endRow();
                        if (frm!=null && rec!=null) {
                            page.startRow();
                                page.startCell("","1","1","");
                                    page.text("Return to transaction","","","",frm+"?id="+rec,"25px","25px");
                                page.endCell();
                            page.endRow();
                        }                               
                    page.endTable(true);
                    page.flush();
                    view.finalize();
                    */
                }
            }
            
        }
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Treatment Item", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");

            page.startForm("newentry", "newtreatmentitem", "post");
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Item:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        ListBox listbox=new ListBox();
                        ListBoxItem item=new ListBoxItem();
                        model.ItemCode c;
                        ic=new model.ItemCodes();
                        ic.fetch(login.getAccountId());
                        for (int i=0; i<ic.size(); i++) {
                            c=(model.ItemCode)ic.get(i);   
                            item=new ListBoxItem(""+c.getId(),c.getDescription());
                            listbox.add(item);
                        }
                        page.inputSelect("itemId",""+detail.getItemId(), listbox,"355px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Quantity:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("qtyOut", ""+detail.getQtyOut(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                        page.inputHidden("command", "1");
                        if (frm!=null) {
                            page.inputHidden("frm", frm);
                        } 
                        if (rec!=null) {
                            page.inputHidden("rec", rec);
                        }                         
                    page.endCell();
                page.endRow();  
                if (!detail.getErrMessage().equals("")) {
                    page.startRow();
                        page.startCell("","1","4","100%");
                            page.text(detail.getErrMessage(), "errortext");
                        page.endCell();
                    page.endRow();                
                }                
                page.startRow();
                    page.startCell("extralinkcell", "1", "4", "");
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
