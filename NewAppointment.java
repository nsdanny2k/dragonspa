/*
 * NewAppointment.java
 * 
 * Created on Aug 25, 2007, 8:38:30 PM
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
public class NewAppointment extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("New Appointment","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("newappointment");
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
        
        int iPatients=0;
        model.Patients patients=new model.Patients();
        model.Patient patient=null;
        model.Appointment appointment=new model.Appointment();
        appointment.add();
        
        String save=request.getParameter("save");
        if (save!=null) {
            
            String patientId=request.getParameter("patientId");
            if (patientId==null) {
                patients=new model.Patients();
                iPatients=patients.search( request.getParameter("patient"), login.getAccountId());
                if (iPatients==1) {
                    patient=new model.Patient();
                    patient=(model.Patient)patients.get(0);
                    patientId=patient.getPartyId();
                } else {
                    // 0 or more patients with name
                }
            } else {
                iPatients=1;
            }            
            boolean bOk=false;

            appointment.setRemarks(request.getParameter("remarks"));
            appointment.setTransTime(ctr.convertToTime(request.getParameter("transTime")));
            appointment.setTransDate(ctr.convertToDate(request.getParameter("transDate")));
            appointment.setDuration(ctr.convertToInt(request.getParameter("duration")));
            
            if (iPatients==1) {
                appointment.setPatientId(patientId);
                appointment.setAccountId(login.getAccountId());
                if (appointment.save()) {
                    ctr.forward("appointments?selectDate="+ctr.convertDateToString(transDate),request,response);
                    return;
                }
            }
        }        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("New Appointment", "titletext", "appointments.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");


            page.startForm("newentry", "newappointment", "post");
            //page.startTable("stdgrid","200px","175px","468px","30px","4");
                        //page.text("Ok", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("ok", "check.jpg", "");
                        //page.text("Cancel", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("cancel", "cross.jpg", "");
            //page.endTable(true);

            //page.startTable("boxgrid","200px","230px","472px","300px","4");
            page.startTable("boxgrid","200px","175px","472px","300px","4");    
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Date:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        page.inputText("transDate", ctr.convertDateToString(transDate), "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Time:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        ListBox listbox=new ListBox();
                        ListBoxItem item=new ListBoxItem();
                        item=new ListBoxItem("5:00:00","5:00 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("5:10:00","5:10 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("5:20:00","5:20 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("5:30:00","5:30 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("5:40:00","5:40 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("5:50:00","5:50 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("6:00:00","6:00 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("6:10:00","6:10 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("6:20:00","6:20 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("6:30:00","6:30 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("6:40:00","6:40 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("6:50:00","6:50 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("7:00:00","7:00 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("7:10:00","7:10 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("7:20:00","7:20 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("7:30:00","7:30 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("7:40:00","7:40 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("7:50:00","7:50 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("8:00:00","8:00 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("8:10:00","8:10 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("8:20:00","8:20 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("8:30:00","8:30 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("8:40:00","8:40 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("8:50:00","8:50 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("9:00:00","9:00 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("9:10:00","9:10 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("9:20:00","9:20 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("9:30:00","9:30 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("9:40:00","9:40 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("9:50:00","9:50 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("10:00:00","10:00 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("10:10:00","10:10 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("10:20:00","10:20 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("10:30:00","10:30 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("10:40:00","10:40 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("10:50:00","10:50 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("11:00:00","11:00 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("11:10:00","11:10 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("11:20:00","11:20 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("11:30:00","11:30 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("11:40:00","11:40 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("11:50:00","11:50 AM");
                        listbox.add(item);                        
                        item=new ListBoxItem("12:00:00","12:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("12:10:00","12:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("12:20:00","12:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("12:30:00","12:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("12:40:00","12:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("12:50:00","12:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("13:00:00","1:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("13:10:00","1:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("13:20:00","1:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("13:30:00","1:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("13:40:00","1:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("13:50:00","1:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("14:00:00","2:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("14:10:00","2:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("14:20:00","2:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("14:30:00","2:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("14:40:00","2:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("14:50:00","2:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("15:00:00","3:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("15:10:00","3:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("15:20:00","3:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("15:30:00","3:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("15:40:00","3:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("15:50:00","3:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("16:00:00","4:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("16:10:00","4:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("16:20:00","4:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("16:30:00","4:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("16:40:00","4:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("16:50:00","4:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("17:00:00","5:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("17:10:00","5:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("17:20:00","5:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("17:30:00","5:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("17:40:00","5:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("17:50:00","5:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("18:00:00","6:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("18:10:00","6:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("18:20:00","6:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("18:30:00","6:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("18:40:00","6:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("18:50:00","6:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("19:00:00","7:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("19:10:00","7:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("19:20:00","7:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("19:30:00","7:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("19:40:00","7:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("19:50:00","7:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("20:00:00","8:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("20:10:00","8:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("20:20:00","8:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("20:30:00","8:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("20:40:00","8:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("20:50:00","8:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("21:00:00","9:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("21:10:00","9:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("21:20:00","9:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("21:30:00","9:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("21:40:00","9:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("21:50:00","9:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("22:00:00","10:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("22:10:00","10:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("22:20:00","10:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("22:30:00","10:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("22:40:00","10:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("22:50:00","10:50 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("23:00:00","11:00 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("23:10:00","11:10 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("23:20:00","11:20 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("23:30:00","11:30 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("23:40:00","11:40 PM");
                        listbox.add(item);                        
                        item=new ListBoxItem("23:50:00","11:50 PM");
                        listbox.add(item);                        
                        page.inputSelect("transTime",ctr.convertTimeToString(appointment.getTransTime()), listbox,"155px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Duration:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        listbox=new ListBox();
                        item=new ListBoxItem("10","10 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("20","20 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("30","30 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("40","40 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("50","50 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("60","60 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("70","70 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("80","80 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("90","90 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("100","100 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("110","110 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("120","120 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("130","130 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("140","140 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("150","150 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("160","160 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("170","170 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("180","180 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("190","190 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("200","200 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("210","210 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("220","220 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("230","230 Minutes");
                        listbox.add(item);
                        item=new ListBoxItem("240","240 Minutes");
                        listbox.add(item);                        
                        page.inputSelect("duration",""+appointment.getDuration(), listbox,"155px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Patient Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        page.inputText("patient", (request.getParameter("patient")==null)?"":request.getParameter("patient"), "");
                        page.image("insert.jpg", "Select Patient", "", "25px", "25px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Remarks:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        page.inputText("remarks", appointment.getRemarks(), "");
                        page.inputHidden("command", "1");
                    page.endCell();
                page.endRow();  
                page.startRow();
                    page.startCell("extralinkcell", "1", "2", "");
                        //page.text("Save Record", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("save", "saveit.jpg", "");
                        page.inputSubmit("save", "Save Record");
                        page.inputHidden("selectDate",ctr.convertDateToString(transDate));
                    page.endCell();
                page.endRow();
                if (iPatients==0 && !request.getParameter("patient").equals("")) {
                    page.startRow();
                        page.startCell("","1","2","100%");
                            page.text("Patient name not found.", "errortext");
                        page.endCell();
                    page.endRow(); 
                } else if (iPatients>1) {
                    for (int i=0; i<patients.size(); i++) {
                        patient=(model.Patient)patients.get(i);
                        page.startRow();
                            page.startCell("","1","2","100%");
                                String descrip=patient.getFirstName()+" "+patient.getMiddleName()+" "+patient.getLastName()+" "+ctr.convertDateToString(patient.getBirthDate());
                                page.inputRadio("patientId", patient.getPartyId(), descrip);
                            page.endCell();
                        page.endRow();                                 
                    }
                }        
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
