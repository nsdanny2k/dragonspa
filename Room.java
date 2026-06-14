/*
 * Room.java
 * 
 * Created on Feb 4, 2008, 4:06:33 PM
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
public class Room extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Doctor","global.css");
        //Page page;
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("room");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        } 
        
        model.Treatment treatment=new model.Treatment();
        model.Patient patient=null;
        String save=request.getParameter("save");
        String id=request.getParameter("id");
        
        String checkout=request.getParameter("checkout");
        if (checkout!=null) {
            if (id!=null) {
                model.Exam exam=new model.Exam();
                exam.checkout(id,login.getAccountId());
                ctr.forward("frontdesk", request, response);
                return;
            }
        } 
        
        boolean bFetchTreatment=false;
        boolean bFetchPatient=false;
        if (id==null) { // get the first treatment
            model.Exams exams=new model.Exams();
            if (exams.fetch(login.getAccountId())>0) {
                model.Exam exam=(model.Exam)exams.get(0);
                id=exam.getTreatmentId();
            }
        }
        String RO=request.getParameter("RO");
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
        if (save!=null) {
            String treatmentId=request.getParameter("treatmentId");
            if (treatmentId!=null) {
                treatment.setU1(request.getParameter("U1"));
                treatment.setU2(request.getParameter("U2"));
                treatment.setDoctorId(request.getParameter("doctor"));
                model.Doctors d=new model.Doctors();
                treatment.setDoctor(d.lookup(ctr.convertToInt(request.getParameter("doctor"))));
                if (treatment.save()) {
                    page=view.initialize("Room","global.css");
                    page.text("Room", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("Record successfully saved.","attribtext");
                            page.endCell();
                        page.endRow();
                        page.startRow();
                            page.startCell("","1","1","");
                                page.text("Return to record","","insert.jpg","","room?id="+id,"25px","25px");
                            page.endCell();
                        page.endRow();
                    page.endTable(true);
                    page.flush();
                    view.finalize();   
                } else {
                    page=view.initialize("Treatment","global.css");
                    page.text("Treatment", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("An error occurred :" + treatment.getErrMessage(),"attribtext");
                            page.endCell();
                        page.endRow();
                    page.endTable(true);
                    page.flush();
                    view.finalize();                }
            }
        }
        
        //page=view.initialize("Room","global.css","");
// ********************************
// ** TITLE **            
// ********************************      
        page.text("Room", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
        page.startForm("Charts", "room", "post");

        page.startTable("stdgrid","200px","210px","468px","30px","4");       
        //page.startTable("stdgrid", "100%");
            page.startRow();
                /*
                page.startCell("", "1", "1", "");
                    page.text("Room", "titletext");
                page.endCell();
                 */
                page.startCell("", "1", "3", "");
                    page.text("Previous", "commandlinktext", "", "", "room?command=previous&id="+treatment.getTreatmentId(), "", "");
                    page.text("Next", "commandlinktext", "", "", "room?command=next&id="+treatment.getTreatmentId(), "", "");
                page.endCell();       
                
                /*
                page.startCell("","1","1","");
                    page.startForm("Room", "room", "post");
                    page.inputSubmit("checkout","Check-out");
                    page.inputHidden("id", ""+treatment.getTreatmentId()); //exam.getId());
                    page.endForm();
                page.endCell();
                 */
                /*
                page.startCell("extralinkcell", "1", "1", "");
                    //page.image("yellowdot.gif", "", "", "10px", "10px");
                    //page.text("Home", "attribtext", "home.jpg", "", "home", "35px", "35px");
                    page.text("Home", "extralinktext", "", "", "home", "", "");
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    //page.image("yellowdot.gif", "", "", "10px", "10px");
                    //page.text("Examination Room", "attribtext", "chair.png", "", "examination", "35px", "35px");
                    page.text("Frontdesk", "extralinktext", "", "", "frontdesk", "", "");
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    //page.image("yellowdot.gif", "", "", "10px", "10px");
                    //page.text("Payment Register", "attribtext", "payments.jpg", "", "payments", "35px", "35px");
                    page.text("Payment Register", "extralinktext", "", "", "payments?id="+treatment.getTreatmentId(), "", "");
                page.endCell();
                 */
            page.endRow();
       page.endTable(false);
       /*
       page.startTable("stdgrid", "100%");
            page.startRow();
                //page.startCell("extralinkcell", "1", "1", "");
                //    page.text("Voice Recorder", "extralinktext", "mp3.jpg", "", "voicerecorder?id="+patient.getPartyId(), "20px", "20px");
                //page.endCell();
                //page.startCell("extralinkcell", "1", "1", "");
                //    page.text("Dental Images", "extralinktext", "", "", "dentalimages?id="+patient.getPartyId(), "20px", "20px");
                //page.endCell();
                //page.startCell("extralinkcell", "1", "1", "");
                //    page.text("Treatment Plans", "extralinktext", "", "", "treatmentplans?id="+patient.getPartyId(), "20px", "20px");
                //page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    page.text("Guest History", "extralinktext", "", "", "treatmenthistory?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    page.text("Guest Profile", "extralinktext", "", "", "guestprofile?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
            page.endRow();
        page.endTable(false);   
        */

        page.startTable("stdgrid", "100%");
// ********************************
// ** SERVICE TRANSACTION **            
// ********************************    
       page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Control Number:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(treatment.getControlNumber(), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Room:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(treatment.getDentalUnit(), "valuetext");
            page.endCell();
       page.startRow();
       page.endRow();
            page.startCell("", "1", "1", "");
                page.text("Therapist:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                //page.text(treatment.getDoctor(), "valuetext");
                model.Doctors doctors=new model.Doctors();
                model.Doctor doctor;
                doctors.fetch(login.getAccountId());
                ListBox listbox=new ListBox();
                ListBoxItem item=new ListBoxItem();
                for (int i=0; i<doctors.size(); i++) {
                    doctor=(model.Doctor)doctors.get(i);
                    //page.text(doctor.getPartyId()+doctor.getFirstName()+doctor.getLastName(), "");
                    item=new ListBoxItem(doctor.getPartyId(),doctor.getFirstName()+" "+doctor.getLastName());
                    listbox.add(item);
                }
                page.inputSelect("doctor",treatment.getDoctorId() , listbox,"100px"); 
            
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Date:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(ctr.convertDateToString(treatment.getDateVisit()), "valuetext");
            page.endCell();
        page.endRow();
// ********************************
// ** PATIENT INFO **            
// ********************************    
        page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Guest:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(treatment.getPatient(), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Last Visit:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(ctr.convertDateToString(treatment.getLastVisit()), "valuetext");
            page.endCell();
        page.endRow();
        page.endTable(false);
// ********************************
// ** TRANSACTION RECORD **            
// ********************************    
        page.startTable("boxgrid", "100%");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Item", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Charge", "attribtext");
                page.endCell();
                /*
                page.startCell("", "1", "1", "");
                    page.text("Paid", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Balance", "attribtext");
                page.endCell();
                */
                /*
                page.startCell("", "1", "1", "");
                    page.text("Remarks", "attribtext");
                page.endCell();
                 */
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
                        model.ProcedureCodes pcodes=new model.ProcedureCodes();
                        page.text(pcodes.lookupDescription(detail.getProcedureId()), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getCharge(), "");
                    page.endCell();
                    /*
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getPaid(), "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text(""+detail.getBalance(), "");
                    page.endCell();
                    */
                    /*
                    page.startCell("", "1", "1", "");
                        page.text((detail.getRemarks()==null)?"":detail.getRemarks(), "");
                    page.endCell();
                     */
                    page.startCell("extralinkcell", "1", "1", "");
                        if (RO==null) {
                            page.text("Delete", "commandlinktext", "", "", "room?id="+treatment.getTreatmentId()+"&command=3&entryId="+detail.getId(), "", "");
                        } else {
                            page.space();
                        }
                    page.endCell();
                page.endRow(); 
            }
            if (RO==null) {
                page.startRow();
                    page.startCell("", "1", "2", "");
                        page.space();
                    page.endCell();
                    page.startCell("extralinkcell", "1", "1", "");
                        page.text("Add", "commandlinktext", "insert.jpg", "", "newtreatmentrecord?rec="+treatment.getTreatmentId()+"&frm=room", "20px", "20px");
                    page.endCell();
                page.endRow(); 
            }
        page.endTable(false);


        
        page.startTable("stdgrid", "60%");
            page.startRow();
                page.startCell("", "1", "2", "");
                    page.text("Remarks","attribtext");
                    page.inputHidden("command", "1");
                page.endCell();
                page.startCell("", "1", "4", "");
                    page.inputText("U2", (treatment.getU2()==null)?"":treatment.getU2(), "400px");
                page.endCell();
                page.inputHidden("id",id);
            page.endRow();
            page.startRow();
                page.startCell("", "1", "2", "");
                    page.text("Credit Card No.","attribtext");
                    page.inputHidden("command", "1");
                page.endCell();
                page.startCell("", "1", "4", "");
                    page.inputText("U1", (treatment.getU1()==null)?"":treatment.getU1(), "300px");
                    page.inputHidden("command", "1");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("extralinkcell", "1", "4", "");
                    //page.text("Legend...", "extralinktext", "", "", "servicerates", "", "");
                    if (RO==null) {
                        page.inputSubmit("save", "Save Record");
                    }
                    //page.text("Save", "attribtext", "", "", "", "30px", "30px");
                    //page.inputImage("save", "saveit.jpg", "");
                    //page.text("Cancel", "attribtext", "", "", "", "30px", "30px");
                    //page.inputImage("cancel", "cross.jpg", "");
                page.endCell();
                page.startCell("", "1", "2", "");
                    page.inputHidden("treatmentId", treatment.getTreatmentId());
                    page.text("", "attribtext");
                page.endCell();
            page.endRow();
            page.endTable(false);
            
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
