/*
 * PatientProfile.java
 * 
 * Created on Aug 25, 2007, 8:56:09 PM
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
public class PatientProfile extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Patient Registration","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("newpatient");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }      
        
        model.Patient patient=(model.Patient)request.getSession().getAttribute("patient");
        if (patient==null) { patient=new model.Patient(); }

        String save=request.getParameter("save");
        String id=request.getParameter("id");
        if ((id!=null)) {
            patient.fetch(id, login.getAccountId());
        } else {
            patient.setFirstName(request.getParameter("firstName"));
            patient.setMiddleName(request.getParameter("middleName"));
            patient.setLastName(request.getParameter("lastName"));
            patient.setBirthDate(ctr.convertToDate(request.getParameter("birthDate")));
            patient.setGender(request.getParameter("gender"));
            patient.setMaritalStatus(request.getParameter("maritalStatus"));
            patient.setReferredBy(request.getParameter("referredBy"));
            patient.setStudent(request.getParameter("student"));
            patient.setEmailAddress(request.getParameter("emailAddress"));
            patient.setMobileNumber(request.getParameter("mobileNumber"));
            patient.setHomePhoneNumber(request.getParameter("homePhoneNumber"));
            patient.setOfficePhoneNumber(request.getParameter("officePhoneNumber"));
            patient.setHomeAddress1(request.getParameter("homeAddress1"));
            patient.setHomeAddress2(request.getParameter("homeAddress2"));
            patient.setHomeCity(request.getParameter("homeCity"));
            patient.setHomeState(request.getParameter("homeState"));
            patient.setHomeZip(request.getParameter("homeZip"));
            patient.setOccupation(request.getParameter("occupation"));
            patient.setSsn(request.getParameter("ssn"));
            patient.setEmployer(request.getParameter("employer"));
            patient.setOfficeAddress1(request.getParameter("officeAddress1"));
            patient.setOfficeAddress2(request.getParameter("officeAddress2"));
            patient.setOfficeCity(request.getParameter("officeCity"));
            patient.setOfficeState(request.getParameter("officeState"));
            patient.setOfficeZip(request.getParameter("officeZip"));
            patient.setHeadOfHousehold(request.getParameter("headOfHousehold"));
            patient.setRelation(request.getParameter("relation"));
            patient.setProvider(request.getParameter("provider"));
            patient.setProviderPlan(request.getParameter("providerPlan"));
        }
        patient.setAccountId(login.getAccountId());
        
        if ((save!=null)) {
            if (!(patient.getFirstName().equals("")) && !(patient.getLastName().equals(""))) {

                if (patient.save()) {
                    page.text("Patient Registration", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("Record successfully saved.","attribtext");
                            page.endCell();
                        page.endRow();
                        String pg=request.getParameter("pg");
                        String pattern=request.getParameter("pattern");
                        if (pg!=null && pattern!=null) {
                            page.startRow();
                                page.startCell("","1","1","");
                                    page.text("Return to list","","","","patients?pg="+pg+"&command="+pattern,"25px","25px");
                                page.endCell();
                            page.endRow();
                        }                              
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("Return to record","","insert.jpg","","patientprofile?id="+patient.getPartyId(),"25px","25px");
                            page.endCell();
                        page.endRow();
                    page.endTable(true);
                    page.flush();
                    view.finalize();
                }
            }
        }
        request.getSession().setAttribute("patient",patient);        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Patient Profile", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
       
            
        page.startTable("stdgrid","200px","175px","468px","30px","4");
            page.startRow();
                //page.startCell("", "1", "1", "");
                //    page.text("Voice Recorder", "extralinktext", "mp3.jpg", "", "voicerecorder?id="+patient.getPartyId(), "20px", "20px");
                //page.endCell();
                //page.startCell("", "1", "1", "");
                //    page.text("Dental Images", "extralinktext", "", "", "dentalimages?id="+patient.getPartyId(), "20px", "20px");
                //page.endCell();
                //page.startCell("", "1", "1", "");
                //    page.text("Treatment Plans", "extralinktext", "", "", "treatmentplans?id="+patient.getPartyId(), "20px", "20px");
                //page.endCell(); 
                page.startCell("", "1", "1", "");
                    page.text("Treatment History", "extralinktext", "", "", "treatmenthistory?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
                page.startCell("", "1", "1", "");
                    page.text("Medical & Dental History", "extralinktext", "", "", "medicalhistory?id="+patient.getPartyId(), "20px", "20px");
                page.endCell();
            page.endRow();
        page.endTable(true);  


            page.startForm("editpatient", "patientprofile", "post");
            //page.startTable("stdgrid","200px","220px","468px","30px","4");
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
        
            //page.startTable("boxgrid","200px","270px","472px","300px","4");
            page.startTable("boxgrid","200px","230px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("First Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("firstName", ( patient.getFirstName()==null)?"":patient.getFirstName(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Middle Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("middleName", ( patient.getMiddleName()==null)?"":patient.getMiddleName(), "130px");
                    page.endCell();
                page.endRow();  
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Last Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("lastName", ( patient.getLastName()==null)?"":patient.getLastName(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Birthday:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("birthDate", ( patient.getBirthDate()==null)?"":ctr.convertDateToString(patient.getBirthDate()), "130px");
                    page.endCell();
                page.endRow();    
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Gender:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        ListBox listbox=new ListBox();
                        ListBoxItem item=new ListBoxItem();
                        item=new ListBoxItem("M","Male");
                        listbox.add(item);
                        item=new ListBoxItem("F","Female");
                        listbox.add(item);
                        page.inputSelect("gender", ( patient.getGender()==null)?"M":patient.getGender(), listbox,"135px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Status:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        listbox=new ListBox();
                        item=new ListBoxItem("S","Single");
                        listbox.add(item);
                        item=new ListBoxItem("M","Married");
                        listbox.add(item);
                        item=new ListBoxItem("P","Seperated");
                        listbox.add(item);
                        item=new ListBoxItem("D","Divorced");
                        listbox.add(item);
                        item=new ListBoxItem("W","Widowed");
                        listbox.add(item);
                        page.inputSelect("maritalStatus", ( patient.getMaritalStatus()==null)?"S":patient.getMaritalStatus(), listbox,"135px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Referred By:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("referredBy",( patient.getReferredBy()==null)?"":patient.getReferredBy(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Student:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        listbox=new ListBox();
                        item=new ListBoxItem("_NA_","");
                        listbox.add(item);
                        item=new ListBoxItem("F","Full Time");
                        listbox.add(item);
                        item=new ListBoxItem("P","Part Time");
                        listbox.add(item);
                        page.inputSelect("student", (patient.getStudent()==null)?"_NA_":patient.getStudent(), listbox,"135px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Email :", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("emailAddress", (patient.getEmailAddress()==null)?"":patient.getEmailAddress(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Mobile#:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("mobileNumber", (patient.getMobileNumber()==null)?"":patient.getMobileNumber(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Home#:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("homePhoneNumber", (patient.getHomePhoneNumber()==null)?"":patient.getHomePhoneNumber(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Office#:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("officePhoneNumber", (patient.getOfficePhoneNumber()==null)?"":patient.getOfficePhoneNumber(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Home Address :", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("homeAddress1", (patient.getHomeAddress1()==null)?"":patient.getHomeAddress1(), "130px");
                    page.endCell();
                    page.startCell("","1","2","50%");
                        page.inputText("homeAddress2", (patient.getHomeAddress2()==null)?"":patient.getHomeAddress2(), "97%");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("City:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("homeCity", (patient.getHomeCity()==null)?"":patient.getHomeCity(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("State / Province:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("homeState", (patient.getHomeState()==null)?"":patient.getHomeState(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Zip:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        page.inputText("homeZip", (patient.getHomeZip()==null)?"":patient.getHomeZip(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Occupation:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("occupation", (patient.getOccupation()==null)?"":patient.getOccupation(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("SSN:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","25%");
                        page.inputText("ssn", (patient.getSsn()==null)?"":patient.getSsn(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Employer:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        page.inputText("employer", (patient.getEmployer()==null)?"":patient.getEmployer(), "98%");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Office Address :", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("officeAddress1", (patient.getOfficeAddress1()==null)?"":patient.getOfficeAddress1(), "130px");
                    page.endCell();
                    page.startCell("","1","2","50%");
                        page.inputText("officeAddress2", (patient.getOfficeAddress2()==null)?"":patient.getOfficeAddress2(), "97%");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("City:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("officeCity", (patient.getOfficeCity()==null)?"":patient.getOfficeCity(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("State / Province:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("officeState", (patient.getOfficeState()==null)?"":patient.getOfficeState(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Zip:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        page.inputText("officeZip", (patient.getOfficeZip()==null)?"":patient.getOfficeZip(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Head of Household:", "attribtext","insert.jpg", "Select Patient", "", "25px", "25px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("headOfHousehold", (patient.getHeadOfHousehold()==null)?"":patient.getHeadOfHousehold(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Relation:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        listbox=new ListBox();
                        item=new ListBoxItem("S","Self");
                        listbox.add(item);
                        item=new ListBoxItem("P","Spouse");
                        listbox.add(item);
                        item=new ListBoxItem("D","Dependent");
                        listbox.add(item);
                        item=new ListBoxItem("O","Other");
                        listbox.add(item);
                        page.inputSelect("relation", (patient.getRelation()==null)?"S":patient.getRelation(), listbox,"135px");
                    page.endCell(); 
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Insurance Provider:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
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
                        page.inputSelect("provider",(patient.getProvider()==null)?"":patient.getProvider(), listbox,"100%"); //135px");                    
                    page.endCell();
                    /*
                    page.startCell("","1","1","25%");
                        page.text("Plan / Group#:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("providerPlan", (patient.getProviderPlan()==null)?"":patient.getProviderPlan(), "130px");
                           
                    page.endCell();                    
                     */
                page.endRow();
                page.startRow();
                    page.startCell("extralinkcell", "1", "4", "");
                        page.inputHidden("command", "1");
                        
                        String pg=request.getParameter("pg");
                        if (pg!=null) {
                            page.inputHidden("pg", pg);
                        }      
                        
                        String pattern=request.getParameter("pattern");
                        if (pattern!=null) {
                            page.inputHidden("pattern", pattern);
                        }                           //page.text("Save Record", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("save", "saveit.jpg", "");
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
