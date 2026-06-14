/*
 * Treatment.java
 * 
 * Created on Aug 25, 2007, 5:03:02 PM
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
public class Treatment extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page;
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("treatment");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        } 
        
        model.Treatment treatment=new model.Treatment();
        model.Patient patient=null;
        String save=request.getParameter("save");
        String id=request.getParameter("id");
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
                treatment.setU3(request.getParameter("U3"));
                treatment.setU4(request.getParameter("U4"));
                treatment.setU5(request.getParameter("U5"));
                treatment.setU6(request.getParameter("U6"));
                treatment.setU7(request.getParameter("U7"));
                treatment.setU8(request.getParameter("U8"));
                treatment.setU9(request.getParameter("U9"));
                treatment.setU10(request.getParameter("U10"));
                treatment.setU11(request.getParameter("U11"));
                treatment.setU12(request.getParameter("U12"));
                treatment.setU13(request.getParameter("U13"));
                treatment.setU14(request.getParameter("U14"));
                treatment.setU15(request.getParameter("U15"));
                treatment.setU16(request.getParameter("U16"));
                treatment.setU17(request.getParameter("U17"));
                treatment.setU18(request.getParameter("U18"));
                treatment.setU19(request.getParameter("U19"));
                treatment.setU20(request.getParameter("U20"));
                treatment.setU21(request.getParameter("U21"));
                treatment.setU22(request.getParameter("U22"));
                treatment.setU23(request.getParameter("U23"));
                treatment.setU24(request.getParameter("U24"));
                treatment.setU25(request.getParameter("U25"));
                treatment.setU26(request.getParameter("U26"));
                treatment.setU27(request.getParameter("U27"));
                treatment.setU28(request.getParameter("U28"));
                treatment.setU29(request.getParameter("U29"));
                treatment.setU30(request.getParameter("U30"));
                treatment.setU31(request.getParameter("U31"));
                treatment.setU32(request.getParameter("U32"));
                treatment.setUA(request.getParameter("UA"));
                treatment.setUB(request.getParameter("UB"));
                treatment.setUC(request.getParameter("UC"));
                treatment.setUD(request.getParameter("UD"));
                treatment.setUE(request.getParameter("UE"));
                treatment.setUF(request.getParameter("UF"));
                treatment.setUG(request.getParameter("UG"));
                treatment.setUH(request.getParameter("UH"));
                treatment.setUI(request.getParameter("UI"));
                treatment.setUJ(request.getParameter("UJ"));
                treatment.setUK(request.getParameter("UK"));
                treatment.setUL(request.getParameter("UL"));
                treatment.setUM(request.getParameter("UM"));
                treatment.setUN(request.getParameter("UN"));
                treatment.setUO(request.getParameter("UO"));
                treatment.setUP(request.getParameter("UP"));
                treatment.setUQ(request.getParameter("UQ"));
                treatment.setUR(request.getParameter("UR"));
                treatment.setUS(request.getParameter("US"));
                treatment.setUT(request.getParameter("UT"));
                if (treatment.save()) {
                    page=view.initialize("Treatment","global.css");
                    page.text("Treatment", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("Record successfully saved.","attribtext");
                            page.endCell();
                        page.endRow();
                        page.startRow();
                            page.startCell("","1","1","");
                                page.text("Return to record","","insert.jpg","","treatment?id="+id,"25px","25px");
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
        
        page=view.initialize("Treatment","global.css","");
// ********************************
// ** TITLE **            
// ********************************      
        page.startTable("stdgrid", "100%");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Treatment", "titletext");
                page.endCell();
                page.startCell("", "1", "3", "");
                    page.text("Previous", "commandlinktext", "", "", "treatment?command=previous&id="+treatment.getTreatmentId(), "", "");
                    page.text("Next", "commandlinktext", "", "", "treatment?command=next&id="+treatment.getTreatmentId(), "", "");
                page.endCell();                
                page.startCell("extralinkcell", "1", "1", "");
                    //page.image("yellowdot.gif", "", "", "10px", "10px");
                    //page.text("Home", "attribtext", "home.jpg", "", "home", "35px", "35px");
                    page.text("Home", "extralinktext", "", "", "home", "", "");
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    //page.image("yellowdot.gif", "", "", "10px", "10px");
                    //page.text("Examination Room", "attribtext", "chair.png", "", "examination", "35px", "35px");
                    page.text("Examination Room", "extralinktext", "", "", "examination", "", "");
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    //page.image("yellowdot.gif", "", "", "10px", "10px");
                    //page.text("Payment Register", "attribtext", "payments.jpg", "", "payments", "35px", "35px");
                    page.text("Payment Register", "extralinktext", "", "", "payments?id="+treatment.getTreatmentId(), "", "");
                page.endCell();
            page.endRow();
       page.endTable(false);
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
                    page.text("Treatment History", "extralinktext", "", "", "treatmenthistory?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    page.text("Medical & Dental History", "extralinktext", "", "", "medicalhistory?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    page.text("Patient Profile", "extralinktext", "", "", "patientprofile?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
            page.endRow();
        page.endTable(false);   


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
                page.text("Dental Unit:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(treatment.getDentalUnit(), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                if (login.getExtended()==1) {
                    page.text("More...", "extralinktext", "", "", "treatmentmore?id="+treatment.getTreatmentId(), "", "");
                } else {
                    page.space();
                }
            page.endCell();
            page.startCell("extralinkcell", "1", "1", "");
                page.text("Details...", "extralinktext", "", "", "treatmentdetails?id="+treatment.getTreatmentId(), "", "");
            page.endCell();
       page.startRow();
       page.endRow();
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
// ********************************
// ** PATIENT INFO **            
// ********************************    
        page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Patient:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(treatment.getPatient(), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Age:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                if (patient.getBirthDate()==null) {
                    page.text("", "");
                } else {
                    page.text(""+ctr.getAge(patient.getBirthDate()), "valuetext");
                }
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Birthday:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(ctr.convertDateToString(patient.getBirthDate()), "valuetext");
            page.endCell();
       page.endRow();
       page.startRow();
            page.startCell("", "1", "1", "");
                page.text("Occupation:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(patient.getOccupation(), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Sex:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(patient.getGenderDesc(patient.getGender()), "valuetext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text("Status:", "attribtext");
            page.endCell();
            page.startCell("", "1", "1", "");
                page.text(patient.getMaritalStatusDesc(patient.getMaritalStatus()), "valuetext");
            page.endCell();
        page.endRow();
        page.endTable(false);
// ********************************
// ** TRANSACTION RECORD **            
// ********************************    
        page.startTable("boxgrid", "100%");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Tooth#", "attribtext");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Diagnosis", "attribtext");
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
                        model.DiagnosisCodes dcodes=new model.DiagnosisCodes();
                        page.text(dcodes.lookupDescription(detail.getDiagnosisId()), "");
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
                        if (RO==null) {
                            page.text("Delete", "commandlinktext", "", "", "treatment?id="+treatment.getTreatmentId()+"&command=3&entryId="+detail.getId(), "", "");
                        } else {
                            page.space();
                        }
                    page.endCell();
                page.endRow(); 
            }
            if (RO==null) {
                page.startRow();
                    page.startCell("", "1", "7", "");
                        page.space();
                    page.endCell();
                    page.startCell("extralinkcell", "1", "1", "");
                        page.text("Add", "commandlinktext", "insert.jpg", "", "newtreatmentrecord?rec="+treatment.getTreatmentId()+"&frm=treatment", "20px", "20px");
                    page.endCell();
                page.endRow(); 
            }
        page.endTable(false);
// ********************************
// ** NOTES **            
// ********************************    
        model.MedicalHistory mh=new model.MedicalHistory();
        model.MedicalAlert alert;
        int count=mh.fetch(patient.getPartyId(), login.getAccountId());
        if (count>0) {
            page.startTable("boxgridwarning", "100%");
                page.startRow();
                    page.startCell("", "1", "1", "");
                        page.text("Notes:", "attribtext");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("", "1", "1", "");
                    for (int i=0; i<mh.size(); i++) {
                        alert=(model.MedicalAlert)mh.get(i);                        
                        page.text(alert.getDescription()+((mh.size()>1)?",":""), "valuetext");
                    }
                    page.endCell();
                page.endRow();
            page.endTable(false);
        }
// ********************************
// ** DENTAL CHARTS PERMANENT **            
// ********************************
        page.startForm("Charts", "treatment", "post");
        page.startTable("boxgrid", "60%");
            page.startRow();
                page.startCell("", "1", "6", "");
                    page.inputHidden("treatmentId", treatment.getTreatmentId());
                    page.text("Chart - Permanent Teeth:", "attribtext");
                page.endCell();
                page.startCell("extralinkcell", "1", "10", "");
                    //page.text("Legend...", "extralinktext", "", "", "servicerates", "", "");
                    if (RO==null) {
                        page.inputSubmit("save", "Save Record");
                    }
                    //page.text("Save", "attribtext", "", "", "", "30px", "30px");
                    //page.inputImage("save", "saveit.jpg", "");
                    //page.text("Cancel", "attribtext", "", "", "", "30px", "30px");
                    //page.inputImage("cancel", "cross.jpg", "");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U1", (treatment.getU1()==null)?"":treatment.getU1(), "25px");
                    page.inputHidden("command", "1");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U2", (treatment.getU2()==null)?"":treatment.getU2(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U3", (treatment.getU3()==null)?"":treatment.getU3(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U4", (treatment.getU4()==null)?"":treatment.getU4(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U5", (treatment.getU5()==null)?"":treatment.getU5(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U6", (treatment.getU6()==null)?"":treatment.getU6(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U7", (treatment.getU7()==null)?"":treatment.getU7(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U8", (treatment.getU8()==null)?"":treatment.getU8(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U9", (treatment.getU9()==null)?"":treatment.getU9(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U10",(treatment.getU10()==null)?"": treatment.getU10(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U11",(treatment.getU11()==null)?"": treatment.getU11(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U12",(treatment.getU12()==null)?"": treatment.getU12(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U13",(treatment.getU13()==null)?"": treatment.getU13(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U14",(treatment.getU14()==null)?"": treatment.getU14(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U15",(treatment.getU15()==null)?"": treatment.getU15(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U16",(treatment.getU16()==null)?"": treatment.getU16(), "25px");
                page.endCell();
            page.endRow();
            page.startRow();
                if (login.getNotation().equals("FDI")) {
                    page.startCell("", "1", "1", "");
                        page.text("18", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("17", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("16", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("15", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("14", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("13", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("12", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("11", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("21", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("22", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("23", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("24", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("25", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("26", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("27", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("28", "toothlabeltext");
                    page.endCell();                    
                } else {
                    page.startCell("", "1", "1", "");
                        page.text("1", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("2", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("3", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("4", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("5", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("6", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("7", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("8", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("9", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("10", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("11", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("12", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("13", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("14", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("15", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("16", "toothlabeltext");
                    page.endCell();
                }
            page.endRow();
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
            page.endRow();            
            page.startRow();
                if (login.getNotation().equals("FDI")) {
                    page.startCell("", "1", "1", "");
                        page.text("48", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("47", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("46", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("45", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("44", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("43", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("42", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("41", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("31", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("32", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("33", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("34", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("35", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("36", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("37", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("38", "toothlabeltext");
                    page.endCell();                    
                } else {
                    page.startCell("", "1", "1", "");
                        page.text("32", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("31", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("30", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("29", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("28", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("27", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("26", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("25", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("24", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("23", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("22", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("21", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("20", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("19", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("18", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("17", "toothlabeltext");
                    page.endCell();
                }
            page.endRow();
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U32",(treatment.getU32()==null)?"": treatment.getU32(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U31",(treatment.getU31()==null)?"": treatment.getU31(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U30",(treatment.getU30()==null)?"": treatment.getU30(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U29",(treatment.getU29()==null)?"": treatment.getU29(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U28",(treatment.getU28()==null)?"": treatment.getU28(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U27",(treatment.getU27()==null)?"": treatment.getU27(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U26",(treatment.getU26()==null)?"": treatment.getU26(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U25",(treatment.getU25()==null)?"": treatment.getU25(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U24",(treatment.getU24()==null)?"": treatment.getU24(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U23",(treatment.getU23()==null)?"": treatment.getU23(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U22",(treatment.getU22()==null)?"": treatment.getU22(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U21",(treatment.getU21()==null)?"": treatment.getU21(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U20",(treatment.getU20()==null)?"": treatment.getU20(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U19",(treatment.getU19()==null)?"": treatment.getU19(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U18",(treatment.getU18()==null)?"": treatment.getU18(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("U17",(treatment.getU17()==null)?"": treatment.getU17(), "25px");
                page.endCell();
            page.endRow();            
        page.endTable(false);
// ********************************
// ** DENTAL CHARTS PRIMARY **            
// ********************************
        //page.startTable("boxgrid", "62.5%");
        page.startTable("boxgrid", "37.5%");
            page.startRow();
            page.startRow();
                page.startCell("", "1", "16", "");
                    page.text("Chart - Primary Teeth:", "attribtext");
                page.endCell();
            page.endRow();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UA",(treatment.getUA()==null)?"": treatment.getUA(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UB",(treatment.getUB()==null)?"": treatment.getUB(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UC",(treatment.getUC()==null)?"": treatment.getUC(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UD",(treatment.getUD()==null)?"": treatment.getUD(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UE",(treatment.getUE()==null)?"": treatment.getUE(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UF",(treatment.getUF()==null)?"": treatment.getUF(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UG",(treatment.getUG()==null)?"": treatment.getUG(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UH",(treatment.getUH()==null)?"": treatment.getUH(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UI",(treatment.getUI()==null)?"": treatment.getUI(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UJ",(treatment.getUJ()==null)?"": treatment.getUJ(), "25px");
                page.endCell();
            page.endRow();
            page.startRow();
                if (login.getNotation().equals("FDI")) {
                    page.startCell("", "1", "1", "");
                        page.text("55", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("54", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("53", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("52", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("51", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("61", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("62", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("63", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("64", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("65", "toothlabeltext");
                    page.endCell();                    
                } else {
                    page.startCell("", "1", "1", "");
                        page.text("A", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("B", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("C", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("D", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("E", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("F", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("G", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("H", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("I", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("J", "toothlabeltext");
                    page.endCell();
                }
            page.endRow();
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.image("tooth01.jpg", "", "", "25px", "25px");
                page.endCell();
            page.endRow();            
            page.startRow();
                if (login.getNotation().equals("FDI")) {
                    page.startCell("", "1", "1", "");
                        page.text("85", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("84", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("83", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("82", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("81", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("71", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("72", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("73", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("74", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("75", "toothlabeltext");
                    page.endCell();
                } else {
                    page.startCell("", "1", "1", "");
                        page.text("T", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("S", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("R", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("Q", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("P", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("O", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("N", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("M", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("L", "toothlabeltext");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("K", "toothlabeltext");
                    page.endCell();                
                }
            page.endRow();
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UT",(treatment.getUT()==null)?"": treatment.getUT(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("US",(treatment.getUS()==null)?"": treatment.getUS(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UR",(treatment.getUR()==null)?"": treatment.getUR(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UQ",(treatment.getUQ()==null)?"": treatment.getUQ(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UP",(treatment.getUP()==null)?"": treatment.getUP(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UO",(treatment.getUO()==null)?"": treatment.getUO(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UN",(treatment.getUN()==null)?"": treatment.getUN(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UM",(treatment.getUM()==null)?"": treatment.getUM(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UL",(treatment.getUL()==null)?"": treatment.getUL(), "25px");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.inputText("UK",(treatment.getUK()==null)?"": treatment.getUK(), "25px");
                    page.inputHidden("id",id);
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
