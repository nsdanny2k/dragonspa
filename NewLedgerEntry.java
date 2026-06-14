/*
 * NewLedgerEntry.java
 * 
 * Created on Aug 25, 2007, 8:18:31 PM
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
public class NewLedgerEntry extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("New Ledger Entry","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("newledgerentry");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }       
        
        int iPatients=0;
        model.Patients patients=new model.Patients();
        model.Patient patient=null;
        model.LedgerDetail detail=new model.LedgerDetail();
        detail.add();
        
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

            detail.setAmount(ctr.convertToDouble(request.getParameter("amount")));
            detail.setCheckNumber(request.getParameter("checkNumber"));
            detail.setCheckDate(ctr.convertToDate(request.getParameter("checkDate")));
            detail.setBankName(request.getParameter("bankName"));
            detail.setRemarks(request.getParameter("remarks"));
            detail.setType(request.getParameter("type"));
            
            model.Treatment treatment=new model.Treatment();
            if (iPatients==1) {
                if (!treatment.fetchLast(patientId, login.getAccountId())) {
                    treatment.setTreatmentId("");
                }
                
                detail.setTransDate(ctr.TODAY());
                detail.setPatientId(patientId);
                //if (request.getParameter("provider").equals("")) {
                //    detail.setInsuranceProviderId(0);
                //}
                    detail.setInsuranceProviderId(ctr.convertToInt(request.getParameter("provider")));
                //}
                detail.setStatus("Adjustment");
                detail.setAccountId(login.getAccountId());
                detail.setTreatmentId(ctr.convertToInt(treatment.getTreatmentId()));
                if (detail.save()) {
                    ctr.forward("ledger?selectDate="+ctr.convertDateToString(ctr.TODAY()),request,response);
                    return;
                }
            }
        }
// ********************************
// ** TITLE **            
// ********************************            
            page.text("New Ledger Entry", "titletext", "ledger.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");

            page.startForm("newentry", "newledgerentry", "post");
            //page.startTable("stdgrid","200px","175px","468px","30px","4");
            //    page.startRow();
            //        page.startCell("extralinkcell", "1", "1", "");
            //            page.text("Save", "attribtext", "", "", "", "30px", "30px");
            //            page.inputImage("save", "saveit.jpg", "");
                        //page.text("Ok", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("ok", "check.jpg", "");
                        //page.text("Cancel", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("cancel", "cross.jpg", "");
            //        page.endCell();
            //    page.endRow();
            //page.endTable(true);
        
            //page.startTable("boxgrid","200px","230px","472px","300px","4");
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Patient Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                            model.Patients ps=new model.Patients();
                            model.Patient p;
                            ps.fetch(login.getAccountId());
                            ListBox listbox=new ListBox();
                            ListBoxItem item=new ListBoxItem();
                            for (int i=0; i<ps.size(); i++) {
                                p=(model.Patient)ps.get(i);
                                item=new ListBoxItem(p.getFirstName()+" "+p.getLastName(),p.getFirstName()+" "+p.getLastName());
                                listbox.add(item);
                            }
                            page.inputSelect("patient", (request.getParameter("patient")==null)?"":request.getParameter("patient"), listbox,""); 
                    
                        //page.inputText("patient", (request.getParameter("patient")==null)?"":request.getParameter("patient"), "");
                        //page.image("insert.jpg", "Select Patient", "", "25px", "25px");
                    page.endCell();

                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Insurance Provider:", "attribtext");
                    page.endCell();
                    
                    page.startCell("","1","1","75%");
                        listbox=new ListBox();
                        item=new ListBoxItem("","");
                        listbox.add(item);
                        model.InsuranceCode code;
                        model.InsuranceCodes codes=new model.InsuranceCodes();
                        codes.fetch(login.getAccountId());
                        //codes.fetch("system");
                        for (int i=0; i<codes.size(); i++) {
                            code=(model.InsuranceCode)codes.get(i);   
                            item=new ListBoxItem(""+code.getId(),code.getDescription());
                            listbox.add(item);
                        }
                        page.inputSelect("provider",""+detail.getInsuranceProviderId(), listbox,"300px"); //155px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Amount:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        page.inputText("amount",""+detail.getAmount(), "");
                    page.endCell();
                page.endRow();                
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Type:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","");
                        listbox=new ListBox();
                        item=new ListBoxItem("Cash","Cash");
                        listbox.add(item);
                        item=new ListBoxItem("Check","Check");
                        listbox.add(item);
                        page.inputSelect("type", detail.getType(), listbox,"155px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("", "1", "1", "25%");
                        page.text("Check Number:", "attribtext");
                    page.endCell();
                    page.startCell("", "1", "1", "75%");
                        page.inputText("checkNumber", detail.getCheckNumber(), "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("", "1", "1", "25%");
                        page.text("Check Date:", "attribtext");
                    page.endCell();
                    page.startCell("", "1", "1", "75%");
                        page.inputText("checkDate",(detail.getCheckDate()==ctr.BLANKDATE())?"":ctr.convertDateToString(detail.getCheckDate()), "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("", "1", "1", "25%");
                        page.text("Bank Name:", "attribtext");
                    page.endCell();
                    page.startCell("", "1", "1", "75%");
                        page.inputText("bankName", detail.getBankName(), "");
                    page.endCell();
                page.endRow();                
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Remarks:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        page.inputText("remarks", detail.getRemarks(), "");
                    page.endCell();
                page.endRow();                 
                page.startRow();
                    page.startCell("extralinkcell", "1", "2", "");
                        page.inputHidden("command", "1");
                        page.inputSubmit("save", "Save Record");
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
