/*
 * Doctor.java
 * 
 * Created on Sep 5, 2007, 11:34:15 AM
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
public class Doctor extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Doctor","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("doctor");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }      
        
        model.Doctor doctor=(model.Doctor)request.getSession().getAttribute("doctor");
        if (doctor==null) { doctor=new model.Doctor(); }

        String save=request.getParameter("save");
        String cmd=request.getParameter("command");
        String id=request.getParameter("id");
        if (cmd!=null) {
            if (cmd.equals("2")) {
                doctor.add();
            }
        } else {
            if (id!=null) {
                doctor.fetch(id, login.getAccountId());
            }
        }
        if ((save!=null)) {
            doctor.setFirstName(request.getParameter("firstName"));
            doctor.setMiddleName(request.getParameter("middleName"));
            doctor.setLastName(request.getParameter("lastName"));
            doctor.setCredentials(request.getParameter("credentials"));
            doctor.setLicenseNumber(request.getParameter("licenseNumber"));
            doctor.setSsn(request.getParameter("ssn"));
            doctor.setPhoneNumber(request.getParameter("phoneNumber"));
            doctor.setAddress1(request.getParameter("address1"));
            doctor.setAddress2(request.getParameter("address2"));
            doctor.setCity(request.getParameter("city"));
            doctor.setState(request.getParameter("state"));
            doctor.setZip(request.getParameter("zip"));
            doctor.setDailyRate(ctr.convertToDouble(request.getParameter("dailyRate")));
        }
        doctor.setAccountId(login.getAccountId());
        
        if ((save!=null)) {
            if (!(doctor.getFirstName().equals("")) && !(doctor.getLastName().equals(""))) {

                if (doctor.save()) {
                    page.text("Doctor", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("Record successfully saved.","attribtext");
                            page.endCell();
                        page.endRow();
                        page.startRow();
                            page.startCell("","1","1","");
                                page.text("Add another record","","insert.jpg","","doctor?command=2","25px","25px");
                            page.endCell();
                        page.endRow();
                    page.endTable(true);
                    page.flush();
                    view.finalize();
                }
            }
        }
        request.getSession().setAttribute("doctor",doctor);        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Doctor", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
       

            page.startForm("editdoctor", "doctor", "post");
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
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("First Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("firstName", ( doctor.getFirstName()==null)?"":doctor.getFirstName(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Middle Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("middleName", ( doctor.getMiddleName()==null)?"":doctor.getMiddleName(), "130px");
                    page.endCell();
                page.endRow();  
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Last Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("lastName", ( doctor.getLastName()==null)?"":doctor.getLastName(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Credentials:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("credentials", ( doctor.getCredentials()==null)?"":doctor.getCredentials(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("License Number:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("licenseNumber", ( doctor.getLicenseNumber()==null)?"":doctor.getLicenseNumber(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("SSN:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("ssn", ( doctor.getSsn()==null)?"":doctor.getSsn(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Phone#:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("phoneNumber", (doctor.getPhoneNumber()==null)?"":doctor.getPhoneNumber(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Address :", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("address1", (doctor.getAddress1()==null)?"":doctor.getAddress1(), "130px");
                    page.endCell();
                    page.startCell("","1","2","50%");
                        page.inputText("address2", (doctor.getAddress2()==null)?"":doctor.getAddress2(), "97%");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("City:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("city", (doctor.getCity()==null)?"":doctor.getCity(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("State / Province:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("state", (doctor.getState()==null)?"":doctor.getState(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Zip:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        page.inputText("zip", (doctor.getZip()==null)?"":doctor.getZip(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Daily Rate:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        page.inputText("dailyRate",""+doctor.getDailyRate(), "130px");
                        page.inputHidden("command", "1");
                    page.endCell();
                page.endRow();
                if (!doctor.getErrMessage().equals("")) {
                    page.startRow();
                        page.startCell("","1","4","100%");
                            page.text(doctor.getErrMessage(), "errortext");
                        page.endCell();
                    page.endRow();                
                }
                page.startRow();
                    page.startCell("extralinkcell", "1", "4", "");
                        //page.text("Save Record", "attribtext", "", "", "", "30px", "30px");
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
