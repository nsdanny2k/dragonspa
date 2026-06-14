/*
 * NewAttendanceItem.java
 * 
 * Created on Oct 20, 2007, 3:19:29 PM
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
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class NewAttendanceItem extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("New Attendance Entry","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("newattendanceitem");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
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
        
        model.AttendanceItem detail=new model.AttendanceItem();
        detail.add();
        
        String save=request.getParameter("save");
        if (save!=null) {
            
            String patientId=request.getParameter("patientId");

            detail.setNumDays(ctr.convertToDouble(request.getParameter("numDays")));
            detail.setTransDate(transDate);
            detail.setAccountId(login.getAccountId());
            detail.setDoctorId(ctr.convertToInt(request.getParameter("doctorId")));
            model.Doctors doctors=new model.Doctors();
            detail.setDoctor(doctors.lookup(ctr.convertToInt(request.getParameter("doctorId"))));
            if (detail.save()) {
                ctr.forward("attendance?selectDate="+ctr.convertDateToString(transDate),request,response);
                return;
            }
        }
// ********************************
// ** TITLE **            
// ********************************            
            page.text("New Attendance Entry", "titletext", "ledger.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");

            page.startForm("newentry", "newattendanceitem", "post");
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Doctor:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                            model.Doctor d;
                            model.Doctors docs=new model.Doctors();
                            docs.fetch(login.getAccountId());
                            ListBox listbox=new ListBox();
                            ListBoxItem item=new ListBoxItem();
                            for (int i=0; i<docs.size(); i++) {
                                d=(model.Doctor)docs.get(i);
                                item=new ListBoxItem(d.getPartyId(),d.getFirstName()+" "+d.getLastName());
                                listbox.add(item);
                            }
                            page.inputSelect("doctorId", (request.getParameter("doctorId")==null)?"":request.getParameter("doctorId"), listbox,""); 
                    
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Days:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        page.inputText("numDays",""+detail.getNumDays(), "");
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
