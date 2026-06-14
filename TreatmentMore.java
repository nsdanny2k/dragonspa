/*
 * TreatmentMore.java
 * 
 * Created on Sep 26, 2007, 2:39:31 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dentalworx.controllers;

import com.dentalworx.view.PageView;
import com.ownx.util.WebController;
import com.ownx.webpages.Page;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class TreatmentMore extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Treatment Extended","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("treatmentmore");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }     
        
        model.Treatment treatment=new model.Treatment();
        model.Patient patient=null;
        String id=request.getParameter("id");
        boolean bFetchTreatment=false;
        boolean bFetchPatient=false;
        if (id==null) { // get the first treatment
            model.Exams exams=new model.Exams();
            if (exams.fetch(login.getAccountId())>0) {
                model.Exam exam=(model.Exam)exams.get(0);
                id=exam.getTreatmentId();
            } else {
                    page.text("Treatment Extended", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("There is no active patient.","attribtext");
                            page.endCell();
                        page.endRow();
                    page.endTable(true);
                    page.flush();
                    view.finalize();                
                    return;
            }
        }
        String cmd=request.getParameter("command");
        model.Exams exams=null;
        if (cmd!=null) {
            if (cmd.equals("previous")) {
                if (id!=null) {
                    String previd="";
                    exams=new model.Exams();
                    if (exams.fetch(login.getAccountId())>0) {
                        model.Exam exam;
                        exam=(model.Exam)exams.get(exams.size()-1);
                        for (int i=0; i<exams.size(); i++) {
                            exam=(model.Exam)exams.get(i);
                            if (exam.getTreatmentId().equals(id)) {
                                if (i==0) {
                                    exam=(model.Exam)exams.get(exams.size()-1);
                                    id=exam.getTreatmentId();
                                } else {
                                    id=previd;
                                }
                                break;
                            } else {
                                previd=exam.getTreatmentId();
                            }
                        }
                    }
                }
            } else if (cmd.equals("next")) {
                if (id!=null) {
                    String nextid="";
                    exams=new model.Exams();
                    if (exams.fetch(login.getAccountId())>0) {
                        model.Exam exam;
                        exam=(model.Exam)exams.get(0);
                        for (int i=exams.size()-1; i>=0; i--) {
                            exam=(model.Exam)exams.get(i);
                            if (exam.getTreatmentId().equals(id)) {
                                if (i==exams.size()-1) {
                                    exam=(model.Exam)exams.get(0);
                                    id=exam.getTreatmentId();
                                } else {
                                    id=nextid;
                                }
                                break;
                            } else {
                                nextid=exam.getTreatmentId();
                            }
                        }
                    }                    
                }
            }
        }
        if ((id!=null)) {
            bFetchTreatment=treatment.fetch(id, login.getAccountId());
            if (bFetchTreatment) {
                patient=new model.Patient();
                bFetchPatient=patient.fetch(treatment.getPatientId(), login.getAccountId());
                request.getSession().setAttribute("treatment",treatment); 
            }
        }    
        if (!bFetchTreatment || !bFetchPatient || (id==null)) {
            treatment=(model.Treatment)request.getSession().getAttribute("treatment");
            if (treatment!=null) {
                
            } else {
                // error , return ?
            }
        }   
        String entryId=request.getParameter("entryId");
        if (cmd!=null && entryId!=null) {
            if (cmd.equals("3")) {
                model.TreatmentCommission detail=new model.TreatmentCommission();
                detail.delete(ctr.convertToInt(entryId));
            }
            if (cmd.equals("6")) {
                model.InventoryItem item=new model.InventoryItem();
                item.delete(ctr.convertToInt(entryId));
            }
        }           
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Treatment Extended", "titletext", "payments.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");

// ********************************
// ** MAIN SUB MENU **            
// ********************************            
            page.startTable("stdgrid","200px","175px","468px","30px","4");
                page.startRow();
                    page.startCell("", "1", "3", "");
                        page.text("Previous", "commandlinktext", "", "", "treatmentmore?command=previous&id="+treatment.getTreatmentId(), "", "");
                        page.text("Next", "commandlinktext", "", "", "treatmentmore?command=next&id="+treatment.getTreatmentId(), "", "");
                    page.endCell();
                    page.startCell("extralinkcell","1","1","");
                        //page.text("Settings", "extralinktext", "", "", "settings", "", "");
                        //page.space();
                        //page.space();
                        page.text("Treatment", "extralinktext", "", "", "treatment?id="+treatment.getTreatmentId(), "", "");
                    page.endCell();
                page.endRow();
            page.endTable(true);

        page.startTable("stdgrid","200px","210px","468px","3");
// ********************************
// ** TRANSACTION HEADER **            
// ********************************            
       page.startTable("stdgrid", "468px");
       page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Control Number:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(treatment.getControlNumber(), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Dental Unit:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(treatment.getDentalUnit(), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.space();
            page.endCell();
            page.startCell("", "1", "1", "");
                page.space();
            page.endCell();
        page.endRow();
        page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Dentist:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(treatment.getDoctor(), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Date:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(ctr.convertDateToString(treatment.getDateVisit()), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Last Visit:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(ctr.convertDateToString(treatment.getLastVisit()), "valuetext");
            page.endCell();
       page.endRow();
       page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Patient:", "attribtext");
            page.endCell();
            page.startCell("", "1", "4", "");
                page.text(treatment.getPatient(), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.space();
            page.endCell();
       page.endRow();
       page.endTable(false);
// ********************************
// ** TRANSACTION DETAIL **            
// ********************************            
        page.startTable("boxgrid", "468px");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Doctor", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Commission", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
            page.endRow();
            
            model.TreatmentCommission detail;
            model.TreatmentCommissions details=new model.TreatmentCommissions();
            details.fetch(ctr.convertToInt(treatment.getTreatmentId()));
            for (int i=0; i<details.size(); i++) {
                detail=(model.TreatmentCommission)details.get(i);            
                page.startRow();
                    page.startCell("", "1", "1", "");
                        page.text(detail.getDoctor(), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getCommission(), "");
                    page.endCell();
                    page.startCell("extralinkcell", "1", "1", "");
                        page.text("Delete", "commandlinktext", "", "", "treatmentmore?id="+treatment.getTreatmentId()+"&command=3&entryId="+detail.getId(), "", "");
                    page.endCell();
                page.endRow(); 
            }
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    page.text("Add", "commandlinktext", "insert.jpg", "", "newtreatmentmore?rec="+treatment.getTreatmentId()+"&frm=treatmentmore", "20px", "20px");
                page.endCell();
            page.endRow(); 
        page.endTable(false);
 
         page.startTable("boxgrid", "468px");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Item", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Qty Used", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
            page.endRow();
            model.InventoryItem item;
            model.Inventory items=new model.Inventory();
            items.fetch(ctr.convertToInt(treatment.getTreatmentId()));
            for (int i=0; i<items.size(); i++) {
                item=(model.InventoryItem)items.get(i);            
                page.startRow();
                    page.startCell("", "1", "1", "");
                        model.ItemCodes ic=new model.ItemCodes();
                        page.text(ic.lookupDescription(item.getItemId()), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(""+item.getQtyOut(), "");
                    page.endCell();
                    page.startCell("extralinkcell", "1", "1", "");
                        page.text("Delete", "commandlinktext", "", "", "treatmentmore?id="+treatment.getTreatmentId()+"&command=6&entryId="+item.getId(), "", "");
                    page.endCell();
                page.endRow(); 
            }
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    page.text("Add", "commandlinktext", "insert.jpg", "", "newtreatmentitem?rec="+treatment.getTreatmentId()+"&frm=treatmentmore", "20px", "20px");
                page.endCell();
            page.endRow(); 
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
