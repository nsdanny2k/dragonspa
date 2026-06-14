/*
 * Payments.java
 * 
 * Created on Aug 25, 2007, 3:46:01 PM
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
public class Payments extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Payment Register","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("payments");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }     
        
        
        model.Treatment treatment=new model.Treatment();
        model.Patient patient=null;
        String id=request.getParameter("id");

        String patientId=request.getParameter("patientId");
        if (patientId!=null) {
            if (patientId.equals("")) {
            } else {
                model.TreatmentDetails details=new model.TreatmentDetails();
                int tId=details.getLastTransaction(ctr.convertToInt(patientId));
                if (tId>0) {
                    id=""+tId;
                }
            }
        }        
        
        boolean bFetchTreatment=false;
        boolean bFetchPatient=false;
        if (id==null) { // get the first treatment
            model.Exams exams=new model.Exams();
            if (exams.fetch(login.getAccountId())>0) {
                model.Exam exam=(model.Exam)exams.get(0);
                id=exam.getTreatmentId();
            } else {
                    page.text("Payments", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "3", "");
                                page.startForm("getpatient", "payments", "post");
                                model.Patients ps=new model.Patients();
                                model.Patient p;
                                ps.fetch(login.getAccountId());
                                ListBox listbox=new ListBox();
                                ListBoxItem item=new ListBoxItem();
                                item=new ListBoxItem("","");
                                listbox.add(item);
                                for (int i=0; i<ps.size(); i++) {
                                    p=(model.Patient)ps.get(i);
                                    item=new ListBoxItem(p.getPartyId(),p.getFirstName()+" "+p.getLastName());
                                    listbox.add(item);
                                }
                                page.inputSelect("patientId", "", listbox,""); 
                                page.inputSubmit("submit", "fetch");
                                page.endForm();  

                            page.endCell();
                            page.endRow();
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("There is no active guest.","attribtext");
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
                model.TreatmentDetail detail=new model.TreatmentDetail();
                detail.delete(ctr.convertToInt(entryId));
            }
        }   
        String payment=request.getParameter("payment");
        if (payment!=null) {
            model.LedgerDetail detail=new model.LedgerDetail();
            
            String rem="";
            model.TreatmentDetail detail2;
            model.TreatmentDetails details=new model.TreatmentDetails();
            details.fetch(ctr.convertToInt(treatment.getTreatmentId()));
            for (int i=0; i<details.size(); i++) {
                detail2=(model.TreatmentDetail)details.get(i);   
                rem=rem+detail2.getProcedure()+",";
            }
            
            if (!request.getParameter("cashAmount").equals("")) {
                detail.add();
                detail.setTransDate(ctr.TODAY());
                detail.setPatientId(patient.getPartyId());
                detail.setTreatmentId(ctr.convertToInt(treatment.getTreatmentId()));
                detail.setStatus("Payment");
                detail.setType("Cash");
                detail.setAmount(ctr.convertToDouble(request.getParameter("cashAmount")));
                detail.setAccountId(login.getAccountId());
                detail.setRemarks(rem+request.getParameter("remarks"));
                detail.save();
            }
            if (!request.getParameter("checkAmount").equals("")) {
                detail.add();
                detail.setTransDate(ctr.TODAY());
                detail.setPatientId(patient.getPartyId());
                detail.setTreatmentId(ctr.convertToInt(treatment.getTreatmentId()));
                detail.setStatus("Payment");
                detail.setType("Check");
                detail.setAmount(ctr.convertToDouble(request.getParameter("checkAmount")));
                detail.setCheckNumber(request.getParameter("checkNumber"));
                detail.setCheckDate(ctr.convertToDate(request.getParameter("checkDate")));
                detail.setBankName(request.getParameter("bankName"));
                detail.setAccountId(login.getAccountId());
                detail.setRemarks(rem+request.getParameter("remarks"));
                detail.save();
            }
            if (!request.getParameter("creditAmount").equals("")) {
                detail.add();
                detail.setTransDate(ctr.TODAY());
                detail.setPatientId(patient.getPartyId());
                detail.setTreatmentId(ctr.convertToInt(treatment.getTreatmentId()));
                detail.setStatus("Payment");
                detail.setType("Credit");
                detail.setAmount(ctr.convertToDouble(request.getParameter("creditAmount")));
                detail.setAuthNumber(request.getParameter("authNumber"));
                detail.setCardType(request.getParameter("cardType"));
                detail.setAccountId(login.getAccountId());
                detail.setRemarks(rem+request.getParameter("remarks"));
                detail.save();
            }      
        }
        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Payment Register", "titletext", "payments.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");

// ********************************
// ** MAIN SUB MENU **            
// ********************************            
            page.startTable("stdgrid","200px","175px","468px","30px","4");
                page.startRow();
                    page.startCell("", "1", "3", "");
                        page.startForm("getpatient", "payments", "post");
                        model.Patients ps=new model.Patients();
                        model.Patient p;
                        ps.fetch(login.getAccountId());
                        ListBox listbox=new ListBox();
                        ListBoxItem item=new ListBoxItem();
                        item=new ListBoxItem("","");
                        listbox.add(item);
                        for (int i=0; i<ps.size(); i++) {
                            p=(model.Patient)ps.get(i);
                            item=new ListBoxItem(p.getPartyId(),p.getFirstName()+" "+p.getLastName());
                            listbox.add(item);
                        }
                        page.inputSelect("patientId", "", listbox,""); 
                        page.inputSubmit("submit", "fetch");
                        page.endForm();  
                        
                    page.endCell();
                    page.startCell("extralinkcell","1","1","");
                        //page.text("Settings", "extralinktext", "", "", "settings", "", "");
                        //page.space();
                        //page.space();
                        page.text("Previous", "commandlinktext", "", "", "payments?command=previous&id="+treatment.getTreatmentId(), "", "");
                        page.text("Next", "commandlinktext", "", "", "payments?command=next&id="+treatment.getTreatmentId(), "", "");
                        page.text("Treatment", "extralinktext", "", "", "treatment?id="+treatment.getTreatmentId(), "", "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","3","");
                  
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
                    page.text("Tooth#", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Procedure", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Charge", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Paid", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Balance", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Remarks", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
            page.endRow();
            
            model.TreatmentDetail detail;
            model.TreatmentDetails details=new model.TreatmentDetails();
            details.fetch(ctr.convertToInt(treatment.getTreatmentId()));
            for (int i=0; i<details.size(); i++) {
                detail=(model.TreatmentDetail)details.get(i);            
                page.startRow();
                    page.startCell("", "1", "1", "");
                        page.text((detail.getTooth()==null)?"":detail.getTooth(), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        model.ProcedureCodes pcodes=new model.ProcedureCodes();
                        page.text(pcodes.lookupDescription(detail.getProcedureId()), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getCharge(), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getPaid(), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getBalance(), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text((detail.getRemarks()==null)?"":detail.getRemarks(), "");
                    page.endCell();
                    page.startCell("extralinkcell", "1", "1", "");
                        page.text("Delete", "commandlinktext", "", "", "payments?id="+treatment.getTreatmentId()+"&command=3&entryId="+detail.getId(), "", "");
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
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.space();
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    page.text("Add", "commandlinktext", "insert.jpg", "", "newtreatmentrecord?rec="+treatment.getTreatmentId()+"&frm=payments", "20px", "20px");
                page.endCell();
            page.endRow(); 
        page.endTable(false);
// ********************************
// ** TRANSACTION FOOTER **            
// ********************************            
        page.startForm("paymentform", "payments", "post");
        page.startTable("stdgrid", "468px");
        page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Cash Amount:", "attribmtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.inputText("cashAmount", "", "81px");
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
                page.text("Check Amount:", "attribmtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.inputText("checkAmount", "", "81px");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Check Number:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.inputText("checkNumber", "", "135px");
            page.endCell();
        page.endRow();
        page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Check Date:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.inputText("checkDate", "", "81px");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Bank Name:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.inputText("bankName", "", "135px");
            page.endCell();
        page.endRow();
        page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Credit Card Amount:", "attribmtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.inputText("creditAmount", "", "81px");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Auth. Number:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.inputText("authNumber", "", "135px");
            page.endCell();
        page.endRow();  
        page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Card Type:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.inputText("cardType", "", "81px");
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
                page.text("Remarks:", "attribtext");
            page.endCell();
            page.startCell("", "1", "3", "");
                page.inputText("remarks", "", "310px");
            page.endCell();
        page.endRow();
        page.startRow();
            page.startCell("", "1", "1", "");
                page.inputHidden("id", id);
                page.inputSubmit("payment", "Make Payments");
            page.endCell();
            /*
            page.startCell("extralinkcell", "1", "3", "");
                //page.text("Receipt", "extralinktext", "", "", "receipt", "", "");
                //page.space();
                page.text("ADA Form", "extralinktext", "", "", "adaform", "", "");
            page.endCell();
             */
        page.endRow();
        page.endTable(false);
        page.endForm();

        page.endTable(true);
            
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
