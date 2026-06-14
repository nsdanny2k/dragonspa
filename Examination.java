
/*
 * Examination.java
 * 
 * Created on Aug 25, 2007, 12:03:53 AM
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
 * @author danny
 */
public class Examination extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        PageView view=new PageView(request,response);
        Page page=view.initialize("Examination Room","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("examination");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }        

        
        model.Patients patients=null;
        model.Patient patient=null;
        int iCheckin=-1;
        String checkin=request.getParameter("checkin");
        if (checkin!=null) {
            String patientId=request.getParameter("patientId");
            if (patientId==null) {
                patients=new model.Patients();
                iCheckin=patients.search( request.getParameter("patient"), login.getAccountId());
                if (iCheckin==1) {
                    patient=new model.Patient();
                    patient=(model.Patient)patients.get(0);
                    patientId=patient.getPartyId();
                } else {
                    // 0 or more patients with name
                }
            } else {
                iCheckin=1;
            }
            if (request.getParameter("dentalUnit").equals("")) {
                // dental unit not specified
            } else if (iCheckin==1) {
                // checkin patientId
                model.Exam exam=new model.Exam();
                exam.setAccountId(login.getAccountId());
                exam.setDentalUnit(request.getParameter("dentalUnit"));
                exam.setDoctorId(request.getParameter("doctor"));
                exam.setPatientId(patientId);
                int Tid=exam.checkin();
                if (Tid==0) {
                    page.text("Examination", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("An error occurred : "+exam.getErrMessage(),"attribtext");
                            page.endCell();
                        page.endRow();
                    page.endTable(true);
                    page.flush();
                    view.finalize();                
                 } else {
                    ctr.forward("treatment?id="+Tid, request, response);
                 }
            }
        }
        String checkout=request.getParameter("checkout");
        if (checkout!=null) {
            String id=request.getParameter("id");
            if (id!=null) {
                model.Exam exam=new model.Exam();
                exam.checkout(id,login.getAccountId());
            }
        }
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Examination Room", "titletext", "chair.png", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
// ********************************
// ** MAIN SUB MENU **            
// ********************************            
            page.startTable("stdgrid","200px","175px","468px","30px","4");
                page.startRow();
                    page.startCell("extralinkcell","1","1","");
                        //page.text("Settings", "extralinktext", "", "", "settings", "", "");
                        //page.space();
                        //page.space();
                        page.text("Register a patient", "extralinktext", "insert.jpg", "", "newpatient", "35px", "35px");
                    page.endCell();
                page.endRow();
            page.endTable(true);
// ********************************
// ** MAIN TABLE **            
// ********************************            
            page.startTable("boxgrid","200px","230px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","");
                        page.text("Dental Unit", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text("Patient Name", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text("Doctor", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.text("Process", "attribtext");
                    page.endCell();
                    //page.startCell("","1","1","");
                    //    page.text("Time-in", "attribtext");
                    //page.endCell();
                page.endRow();
                model.Exam exam;
                model.Exams exams=new model.Exams();
                int count=exams.fetch(login.getAccountId());
                for (int i=0; i<exams.size(); i++) {
                    exam=(model.Exam)exams.get(i);
                    page.startRow();
                        page.startForm("examform", "examination", "post");
                        page.startCell("","1","1","");
                            page.text(exam.getDentalUnit(), "", "file.jpg", "Treatment", "treatment?id="+exam.getTreatmentId(), "25px", "25px");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(exam.getPatient(), "extralinktext", "", "", "treatment?id="+exam.getTreatmentId(), "", "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.text(exam.getDoctor(), "");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.inputSubmit("checkout","Check-out");
                            page.inputHidden("id", ""+exam.getId());
                        page.endCell();
                        //page.startCell("","1","1","");
                        //    page.text(ctr.convertTimeStampToString(exam.getCreatedStamp()), "");
                        //page.endCell();
                        page.endForm();
                    page.endRow();
                }
                if (checkin!=null) {
                    page.startForm("examform", "examination", "post");
                    page.startRow();
                        page.startCell("","1","1","");
                            page.inputText("dentalUnit", request.getParameter("dentalUnit"), "12px");
                            page.image("insert.jpg", "Select Patient", "", "25px", "25px");
                            page.endCell();
                        page.startCell("","1","1","");
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
                            page.inputSelect("patient", request.getParameter("patient"), listbox,"180px"); 
                        page.endCell();
                        page.startCell("","1","1","");
                            model.Doctors doctors=new model.Doctors();
                            model.Doctor doctor;
                            doctors.fetch(login.getAccountId());
                            listbox=new ListBox();
                            item=new ListBoxItem();
                            for (int i=0; i<doctors.size(); i++) {
                                doctor=(model.Doctor)doctors.get(i);
                                //page.text(doctor.getPartyId()+doctor.getFirstName()+doctor.getLastName(), "");
                                item=new ListBoxItem(doctor.getPartyId(),doctor.getFirstName()+" "+doctor.getLastName());
                                listbox.add(item);
                            }
                            page.inputSelect("doctor", request.getParameter("doctor"), listbox,"100px"); 
                        page.endCell();
                        page.startCell("","1","1","");
                            page.inputSubmit("checkin","Check-in  ");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.space();
                        page.endCell();
                    page.endRow(); 
                    if (iCheckin==0 && !request.getParameter("patient").equals("")) {
                        page.startRow();
                            page.startCell("","1","5","");
                                page.text("Patient name not found.", "errortext");
                            page.endCell();
                        page.endRow(); 
                    } else if ( iCheckin>1) {
                        for (int i=0; i<patients.size(); i++) {
                            patient=(model.Patient)patients.get(i);
                            page.startRow();
                                page.startCell("","1","5","");
                                    String descrip=patient.getFirstName()+" "+patient.getMiddleName()+" "+patient.getLastName()+" "+ctr.convertDateToString(patient.getBirthDate());
                                    page.inputRadio("patientId", patient.getPartyId(), descrip);
                                page.endCell();
                            page.endRow();                                 
                        }
                    }
                    if (request.getParameter("dentalUnit").equals("")) {
                         page.startRow();
                            page.startCell("","1","5","");
                                page.text("Please specify a dental unit.", "errortext");
                            page.endCell();
                        page.endRow();                        
                    }
                    page.endForm();
                } else {
                    page.startRow();
                        page.startForm("examform", "examination", "post");
                        page.startCell("","1","1","");
                            page.inputText("dentalUnit", "", "12px");
                            page.image("insert.jpg", "Select Patient", "", "25px", "25px");
                            page.endCell();
                        page.startCell("","1","1","");
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
                            page.inputSelect("patient", request.getParameter("patient"), listbox,"180px"); 
                        page.endCell();
                        page.startCell("","1","1","");
                            model.Doctors doctors=new model.Doctors();
                            model.Doctor doctor;
                            doctors.fetch(login.getAccountId());
                            listbox=new ListBox();
                            item=new ListBoxItem();
                            for (int i=0; i<doctors.size(); i++) {
                                doctor=(model.Doctor)doctors.get(i);
                                //page.text(doctor.getPartyId()+doctor.getFirstName()+doctor.getLastName(), "");
                                item=new ListBoxItem(doctor.getPartyId(),doctor.getFirstName()+" "+doctor.getLastName());
                                listbox.add(item);
                            }
                            page.inputSelect("doctor", "", listbox,"100px"); 
                        page.endCell();
                        page.startCell("","1","1","");
                            page.inputSubmit("checkin","Check-in  ");
                        page.endCell();
                        page.startCell("","1","1","");
                            page.space();
                        page.endCell();
                        page.endForm();
                    page.endRow();     
                }
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
