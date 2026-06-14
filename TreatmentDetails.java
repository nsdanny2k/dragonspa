/*
 * TreatmentDetails.java
 * 
 * Created on Sep 12, 2007, 4:42:40 PM
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
public class TreatmentDetails extends HttpServlet {
   
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
        login.setLastForm("treatmentdetail");
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
                treatment.setT1(request.getParameter("T1"));
                treatment.setT2(request.getParameter("T2"));
                treatment.setT3(request.getParameter("T3"));
                treatment.setT4(request.getParameter("T4"));
                treatment.setT5(request.getParameter("T5"));
                treatment.setT6(request.getParameter("T6"));
                treatment.setT7(request.getParameter("T7"));
                treatment.setT8(request.getParameter("T8"));
                treatment.setT9(request.getParameter("T9"));
                treatment.setT10(request.getParameter("T10"));
                treatment.setT11(request.getParameter("T11"));
                treatment.setT12(request.getParameter("T12"));
                treatment.setT13(request.getParameter("T13"));
                treatment.setT14(request.getParameter("T14"));
                treatment.setT15(request.getParameter("T15"));
                treatment.setT16(request.getParameter("T16"));
                treatment.setT17(request.getParameter("T17"));
                treatment.setT18(request.getParameter("T18"));
                treatment.setT19(request.getParameter("T19"));
                treatment.setT20(request.getParameter("T20"));
                treatment.setT21(request.getParameter("T21"));
                treatment.setT22(request.getParameter("T22"));
                treatment.setT23(request.getParameter("T23"));
                treatment.setT24(request.getParameter("T24"));
                treatment.setT25(request.getParameter("T25"));
                treatment.setT26(request.getParameter("T26"));
                treatment.setT27(request.getParameter("T27"));
                treatment.setT28(request.getParameter("T28"));
                treatment.setT29(request.getParameter("T29"));
                treatment.setT30(request.getParameter("T30"));
                treatment.setT31(request.getParameter("T31"));
                treatment.setT32(request.getParameter("T32"));
                treatment.setTA(request.getParameter("TA"));
                treatment.setTB(request.getParameter("TB"));
                treatment.setTC(request.getParameter("TC"));
                treatment.setTD(request.getParameter("TD"));
                treatment.setTE(request.getParameter("TE"));
                treatment.setTF(request.getParameter("TF"));
                treatment.setTG(request.getParameter("TG"));
                treatment.setTH(request.getParameter("TH"));
                treatment.setTI(request.getParameter("TI"));
                treatment.setTJ(request.getParameter("TJ"));
                treatment.setTK(request.getParameter("TK"));
                treatment.setTL(request.getParameter("TL"));
                treatment.setTM(request.getParameter("TM"));
                treatment.setTN(request.getParameter("TN"));
                treatment.setTO(request.getParameter("TO"));
                treatment.setTP(request.getParameter("TP"));
                treatment.setTQ(request.getParameter("TQ"));
                treatment.setTR(request.getParameter("TR"));
                treatment.setTS(request.getParameter("TS"));
                treatment.setTT(request.getParameter("TT"));
                
                treatment.setDiagnosis(request.getParameter("diagnosis"));
                treatment.setTreatment(request.getParameter("treatment"));
                
                if (treatment.save()) {
                    page=view.initialize("Treatment Detail","global.css");
                    page.text("Treatment Detail", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                    page.startTable("stdgrid","250px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("Record successfully saved.","attribtext");
                                //page.text("treatment"+request.getParameter("treatment"),"attribtext");
                            page.endCell();
                        page.endRow();
                        page.startRow();
                            page.startCell("","1","1","");
                                page.text("Return to record","","insert.jpg","","treatmentdetails?id="+id,"25px","25px");
                            page.endCell();
                        page.endRow();
                    page.endTable(true);
                    page.flush();
                    view.finalize();   
                } else {
                    page=view.initialize("Treatment Detail","global.css");
                    page.text("Treatment", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                    page.startTable("stdgrid","250px","210px","468px","30px","4");
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
        
        page=view.initialize("Treatment Detail","global.css","");
// ********************************
// ** TITLE **            
// ********************************      
        page.startTable("stdgrid", "100%");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("Treatment Detail", "titletext");
                page.endCell();
                page.startCell("", "1", "3", "");
                    page.text("Previous", "commandlinktext", "", "", "treatmentdetails?command=previous&id="+treatment.getTreatmentId(), "", "");
                    page.text("Next", "commandlinktext", "", "", "treatmentdetails?command=next&id="+treatment.getTreatmentId(), "", "");
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
                page.startCell("extralinkcell", "1", "1", "");
                    page.text("Voice Recorder", "extralinktext", "mp3.jpg", "", "voicerecorder?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    page.text("Dental Images", "extralinktext", "", "", "dentalimages?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
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
                page.space();
            page.endCell();
            page.startCell("extralinkcell", "1", "1", "");
                page.text("Treatment...", "extralinktext", "", "", "treatment?id="+treatment.getTreatmentId(), "", "");
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
// ** DENTAL CHARTS PERMANENT **            
// ********************************
        page.startForm("Charts", "treatmentdetails", "post");
        page.startTable("boxgrid", "100%");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.inputHidden("treatmentId", treatment.getTreatmentId());
                    page.text("Chart - Permanent Teeth:", "attribtext");
                page.endCell();
                page.startCell("extralinkcell", "1", "1", "");
                    //page.text("Legend...", "extralinktext", "", "", "servicerates", "", "");
                    page.space();
                    //page.text("Save", "attribtext", "", "", "", "30px", "30px");
                    //page.inputImage("save", "saveit.jpg", "");
                    //page.text("Cancel", "attribtext", "", "", "", "30px", "30px");
                    //page.inputImage("cancel", "cross.jpg", "");
                page.endCell();
            page.endRow();
    if (login.getNotation().equals("FDI")) {            
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.text("18", "toothlabeltext");
                    page.inputText("T1", (treatment.getT1()==null)?"":treatment.getT1(), "250px","1");
                    page.inputHidden("command", "1");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("48", "toothlabeltext");
                    page.inputText("T32",(treatment.getT32()==null)?"": treatment.getT32(), "250px","17");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.text("17", "toothlabeltext");
                    page.inputText("T2", (treatment.getT2()==null)?"":treatment.getT2(), "250px","2");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("47", "toothlabeltext");
                    page.inputText("T31",(treatment.getT31()==null)?"": treatment.getT31(), "250px","18");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.text("16", "toothlabeltext");
                    page.inputText("T3", (treatment.getT3()==null)?"":treatment.getT3(), "250px","3");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("46", "toothlabeltext");
                    page.inputText("T30",(treatment.getT30()==null)?"": treatment.getT30(), "250px","19");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("15", "toothlabeltext");
                    page.inputText("T4", (treatment.getT4()==null)?"":treatment.getT4(), "250px","4");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("45", "toothlabeltext");
                    page.inputText("T29",(treatment.getT29()==null)?"": treatment.getT29(), "250px","20");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("14", "toothlabeltext");
                    page.inputText("T5", (treatment.getT5()==null)?"":treatment.getT5(), "250px","5");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("44", "toothlabeltext");
                    page.inputText("T28",(treatment.getT28()==null)?"": treatment.getT28(), "250px","21");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("13", "toothlabeltext");
                    page.inputText("T6", (treatment.getT6()==null)?"":treatment.getT6(), "250px","6");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("43", "toothlabeltext");
                    page.inputText("T27",(treatment.getT27()==null)?"": treatment.getT27(), "250px","22");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("12", "toothlabeltext");
                    page.inputText("T7", (treatment.getT7()==null)?"":treatment.getT7(), "250px","7");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("42", "toothlabeltext");
                    page.inputText("T26",(treatment.getT26()==null)?"": treatment.getT26(), "250px","23");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("11", "toothlabeltext");
                    page.inputText("T8", (treatment.getT8()==null)?"":treatment.getT8(), "250px","8");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("41", "toothlabeltext");
                    page.inputText("T25",(treatment.getT25()==null)?"": treatment.getT25(), "250px","24");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("21", "toothlabeltext");
                    page.inputText("T9", (treatment.getT9()==null)?"":treatment.getT9(), "250px","9");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("31", "toothlabeltext");
                    page.inputText("T24",(treatment.getT24()==null)?"": treatment.getT24(), "250px","25");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("22", "toothlabeltext");
                    page.inputText("T10",(treatment.getT10()==null)?"": treatment.getT10(), "250px","10");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("32", "toothlabeltext");
                    page.inputText("T23",(treatment.getT23()==null)?"": treatment.getT23(), "250px","26");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("23", "toothlabeltext"); 
                    page.inputText("T11",(treatment.getT11()==null)?"": treatment.getT11(), "250px","11");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("33", "toothlabeltext");
                    page.inputText("T22",(treatment.getT22()==null)?"": treatment.getT22(), "250px","27");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("24", "toothlabeltext");
                    page.inputText("T12",(treatment.getT12()==null)?"": treatment.getT12(), "250px","12");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("34", "toothlabeltext");
                    page.inputText("T21",(treatment.getT21()==null)?"": treatment.getT21(), "250px","28");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("25", "toothlabeltext");
                    page.inputText("T13",(treatment.getT13()==null)?"": treatment.getT13(), "250px","13");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("35", "toothlabeltext");
                    page.inputText("T20",(treatment.getT20()==null)?"": treatment.getT20(), "250px","29");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("26", "toothlabeltext");
                    page.inputText("T14",(treatment.getT14()==null)?"": treatment.getT14(), "250px","14");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("36", "toothlabeltext");
                    page.inputText("T19",(treatment.getT19()==null)?"": treatment.getT19(), "250px","30");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("27", "toothlabeltext");
                    page.inputText("T15",(treatment.getT15()==null)?"": treatment.getT15(), "250px","15");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("37", "toothlabeltext");
                    page.inputText("T18",(treatment.getT18()==null)?"": treatment.getT18(), "250px","31");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("28", "toothlabeltext");
                    page.inputText("T16",(treatment.getT16()==null)?"": treatment.getT16(), "250px","16");
                page.endCell();
                 page.startCell("boxcell", "1", "1", "");
                    page.text("38", "toothlabeltext");
                    page.inputText("T17",(treatment.getT17()==null)?"": treatment.getT17(), "250px","32");
                page.endCell();
           page.endRow();
    } else {
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.text("1", "toothlabeltext");
                    page.inputText("T1", (treatment.getT1()==null)?"":treatment.getT1(), "250px","1");
                    page.inputHidden("command", "1");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("32", "toothlabeltext");
                    page.inputText("T32",(treatment.getT32()==null)?"": treatment.getT32(), "250px","17");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.text("2", "toothlabeltext");
                    page.inputText("T2", (treatment.getT2()==null)?"":treatment.getT2(), "250px","2");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("31", "toothlabeltext");
                    page.inputText("T31",(treatment.getT31()==null)?"": treatment.getT31(), "250px","18");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.text("3", "toothlabeltext");
                    page.inputText("T3", (treatment.getT3()==null)?"":treatment.getT3(), "250px","3");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("30", "toothlabeltext");
                    page.inputText("T30",(treatment.getT30()==null)?"": treatment.getT30(), "250px","19");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("4", "toothlabeltext");
                    page.inputText("T4", (treatment.getT4()==null)?"":treatment.getT4(), "250px","4");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("29", "toothlabeltext");
                    page.inputText("T29",(treatment.getT29()==null)?"": treatment.getT29(), "250px","20");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("5", "toothlabeltext");
                    page.inputText("T5", (treatment.getT5()==null)?"":treatment.getT5(), "250px","5");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("28", "toothlabeltext");
                    page.inputText("T28",(treatment.getT28()==null)?"": treatment.getT28(), "250px","21");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("6", "toothlabeltext");
                    page.inputText("T6", (treatment.getT6()==null)?"":treatment.getT6(), "250px","6");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("27", "toothlabeltext");
                    page.inputText("T27",(treatment.getT27()==null)?"": treatment.getT27(), "250px","22");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("7", "toothlabeltext");
                    page.inputText("T7", (treatment.getT7()==null)?"":treatment.getT7(), "250px","7");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("26", "toothlabeltext");
                    page.inputText("T26",(treatment.getT26()==null)?"": treatment.getT26(), "250px","23");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("8", "toothlabeltext");
                    page.inputText("T8", (treatment.getT8()==null)?"":treatment.getT8(), "250px","8");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("25", "toothlabeltext");
                    page.inputText("T25",(treatment.getT25()==null)?"": treatment.getT25(), "250px","24");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("9", "toothlabeltext");
                    page.inputText("T9", (treatment.getT9()==null)?"":treatment.getT9(), "250px","9");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("24", "toothlabeltext");
                    page.inputText("T24",(treatment.getT24()==null)?"": treatment.getT24(), "250px","25");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("10", "toothlabeltext");
                    page.inputText("T10",(treatment.getT10()==null)?"": treatment.getT10(), "250px","10");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("23", "toothlabeltext");
                    page.inputText("T23",(treatment.getT23()==null)?"": treatment.getT23(), "250px","26");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("11", "toothlabeltext"); 
                    page.inputText("T11",(treatment.getT11()==null)?"": treatment.getT11(), "250px","11");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("22", "toothlabeltext");
                    page.inputText("T22",(treatment.getT22()==null)?"": treatment.getT22(), "250px","27");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("12", "toothlabeltext");
                    page.inputText("T12",(treatment.getT12()==null)?"": treatment.getT12(), "250px","12");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("21", "toothlabeltext");
                    page.inputText("T21",(treatment.getT21()==null)?"": treatment.getT21(), "250px","28");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("13", "toothlabeltext");
                    page.inputText("T13",(treatment.getT13()==null)?"": treatment.getT13(), "250px","13");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("20", "toothlabeltext");
                    page.inputText("T20",(treatment.getT20()==null)?"": treatment.getT20(), "250px","29");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("14", "toothlabeltext");
                    page.inputText("T14",(treatment.getT14()==null)?"": treatment.getT14(), "250px","14");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("19", "toothlabeltext");
                    page.inputText("T19",(treatment.getT19()==null)?"": treatment.getT19(), "250px","30");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("15", "toothlabeltext");
                    page.inputText("T15",(treatment.getT15()==null)?"": treatment.getT15(), "250px","15");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("18", "toothlabeltext");
                    page.inputText("T18",(treatment.getT18()==null)?"": treatment.getT18(), "250px","31");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("16", "toothlabeltext");
                    page.inputText("T16",(treatment.getT16()==null)?"": treatment.getT16(), "250px","16");
                page.endCell();
                 page.startCell("boxcell", "1", "1", "");
                    page.text("17", "toothlabeltext");
                    page.inputText("T17",(treatment.getT17()==null)?"": treatment.getT17(), "250px","32");
                page.endCell();
           page.endRow();
    }
       page.endTable(false);
// ********************************
// ** DENTAL CHARTS PRIMARY **            
// ********************************
        page.startTable("boxgrid", "100%");
            page.startRow();
                page.startCell("", "1", "16", "");
                    page.text("Chart - Primary Teeth:", "attribtext");
                page.endCell();
            page.endRow();
    if (login.getNotation().equals("FDI")) {              
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.text("55", "toothlabeltext");
                    page.inputText("TA",(treatment.getTA()==null)?"": treatment.getTA(), "250px","33");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("85", "toothlabeltext");
                    page.inputText("TT",(treatment.getTT()==null)?"": treatment.getTT(), "250px","43");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("54", "toothlabeltext");
                    page.inputText("TB",(treatment.getTB()==null)?"": treatment.getTB(), "250px","34");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("84", "toothlabeltext");
                    page.inputText("TS",(treatment.getTS()==null)?"": treatment.getTS(), "250px","44");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("53", "toothlabeltext");
                    page.inputText("TC",(treatment.getTC()==null)?"": treatment.getTC(), "250px","35");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("83", "toothlabeltext");
                    page.inputText("TR",(treatment.getTR()==null)?"": treatment.getTR(), "250px","45");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("52", "toothlabeltext");
                    page.inputText("TD",(treatment.getTD()==null)?"": treatment.getTD(), "250px","36");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("82", "toothlabeltext");
                    page.inputText("TQ",(treatment.getTQ()==null)?"": treatment.getTQ(), "250px","46");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("51", "toothlabeltext");
                    page.inputText("TE",(treatment.getTE()==null)?"": treatment.getTE(), "250px","37");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("81", "toothlabeltext");
                    page.inputText("TP",(treatment.getTP()==null)?"": treatment.getTP(), "250px","47");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("61", "toothlabeltext");
                    page.inputText("TF",(treatment.getTF()==null)?"": treatment.getTF(), "250px","38");
                page.endCell();
                 page.startCell("boxcell", "1", "1", "");
                    page.text("71", "toothlabeltext");
                    page.inputText("TO",(treatment.getTO()==null)?"": treatment.getTO(), "250px","48");
                page.endCell();
           page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("62", "toothlabeltext");
                    page.inputText("TG",(treatment.getTG()==null)?"": treatment.getTG(), "250px","39");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("72", "toothlabeltext");
                    page.inputText("TN",(treatment.getTN()==null)?"": treatment.getTN(), "250px","49");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("63", "toothlabeltext");
                    page.inputText("TH",(treatment.getTH()==null)?"": treatment.getTH(), "250px","40");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("73", "toothlabeltext");
                    page.inputText("TM",(treatment.getTM()==null)?"": treatment.getTM(), "250px","50");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("64", "toothlabeltext");
                    page.inputText("TI",(treatment.getTI()==null)?"": treatment.getTI(), "250px","41");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("74", "toothlabeltext");
                    page.inputText("TL",(treatment.getTL()==null)?"": treatment.getTL(), "250px","51");
                page.endCell();
            page.endRow();
            page.startRow();               
                page.startCell("boxcell", "1", "1", "");
                    page.text("65", "toothlabeltext");
                    page.inputText("TJ",(treatment.getTJ()==null)?"": treatment.getTJ(), "250px","42");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("75", "toothlabeltext");
                    page.inputText("TK",(treatment.getTK()==null)?"": treatment.getTK(), "250px","52");
                    page.inputHidden("id",id);
                page.endCell();
            page.endRow();
    } else {
            page.startRow();
                page.startCell("boxcell", "1", "1", "");
                    page.text("A", "toothlabeltext");
                    page.inputText("TA",(treatment.getTA()==null)?"": treatment.getTA(), "250px","33");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("T", "toothlabeltext");
                    page.inputText("TT",(treatment.getTT()==null)?"": treatment.getTT(), "250px","43");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("B", "toothlabeltext");
                    page.inputText("TB",(treatment.getTB()==null)?"": treatment.getTB(), "250px","34");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("S", "toothlabeltext");
                    page.inputText("TS",(treatment.getTS()==null)?"": treatment.getTS(), "250px","44");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("C", "toothlabeltext");
                    page.inputText("TC",(treatment.getTC()==null)?"": treatment.getTC(), "250px","35");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("R", "toothlabeltext");
                    page.inputText("TR",(treatment.getTR()==null)?"": treatment.getTR(), "250px","45");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("D", "toothlabeltext");
                    page.inputText("TD",(treatment.getTD()==null)?"": treatment.getTD(), "250px","36");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("Q", "toothlabeltext");
                    page.inputText("TQ",(treatment.getTQ()==null)?"": treatment.getTQ(), "250px","46");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("E", "toothlabeltext");
                    page.inputText("TE",(treatment.getTE()==null)?"": treatment.getTE(), "250px","37");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("P", "toothlabeltext");
                    page.inputText("TP",(treatment.getTP()==null)?"": treatment.getTP(), "250px","47");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("F", "toothlabeltext");
                    page.inputText("TF",(treatment.getTF()==null)?"": treatment.getTF(), "250px","38");
                page.endCell();
                 page.startCell("boxcell", "1", "1", "");
                    page.text("O", "toothlabeltext");
                    page.inputText("TO",(treatment.getTO()==null)?"": treatment.getTO(), "250px","48");
                page.endCell();
           page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("G", "toothlabeltext");
                    page.inputText("TG",(treatment.getTG()==null)?"": treatment.getTG(), "250px","39");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("N", "toothlabeltext");
                    page.inputText("TN",(treatment.getTN()==null)?"": treatment.getTN(), "250px","49");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("H", "toothlabeltext");
                    page.inputText("TH",(treatment.getTH()==null)?"": treatment.getTH(), "250px","40");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("M", "toothlabeltext");
                    page.inputText("TM",(treatment.getTM()==null)?"": treatment.getTM(), "250px","50");
                page.endCell();
            page.endRow();
            page.startRow();                
                page.startCell("boxcell", "1", "1", "");
                    page.text("I", "toothlabeltext");
                    page.inputText("TI",(treatment.getTI()==null)?"": treatment.getTI(), "250px","41");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("L", "toothlabeltext");
                    page.inputText("TL",(treatment.getTL()==null)?"": treatment.getTL(), "250px","51");
                page.endCell();
            page.endRow();
            page.startRow();               
                page.startCell("boxcell", "1", "1", "");
                    page.text("J", "toothlabeltext");
                    page.inputText("TJ",(treatment.getTJ()==null)?"": treatment.getTJ(), "250px","42");
                page.endCell();
                page.startCell("boxcell", "1", "1", "");
                    page.text("K", "toothlabeltext");
                    page.inputText("TK",(treatment.getTK()==null)?"": treatment.getTK(), "250px","52");
                    page.inputHidden("id",id);
                page.endCell();
            page.endRow();                
    }
        page.endTable(false);
        
        page.startTable("boxgrid", "100%");
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("diagnosis","attribtext");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "1", "");
                    String d=(treatment.getDiagnosis()==null)?"":treatment.getDiagnosis();
                    page.textArea("diagnosis",d,5, 95);
                    //page.inputText("diagnosis", d, "650px");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "1", "");
                    page.text("treatment","attribtext");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("", "1", "1", "");
                    String t=(treatment.getTreatment()==null)?"":treatment.getTreatment();
                    page.textArea("treatment",t ,5, 95);
                    //page.inputText("treatment", t, "650px");
                page.endCell();
            page.endRow();
            page.startRow();
                page.startCell("extralinkcell", "1", "1", "");
                    if (RO==null) {
                        page.inputSubmit("save", "Save Record");
                    }
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
