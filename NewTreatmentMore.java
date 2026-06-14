
/*
 * NewTreatmentMore.java
 * 
 * Created on Sep 26, 2007, 3:32:48 PM
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
public class NewTreatmentMore extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Treatment Commission","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("newtreatmentmore");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }      
        
        model.TreatmentCommission detail=new model.TreatmentCommission();
        model.Doctors doctors=new model.Doctors();
        
        String frm=request.getParameter("frm");
        String rec=request.getParameter("rec");
        String save=request.getParameter("save");
        if ((save==null)) {
            detail.add();
        } else {
        }
        
        if ((save!=null)) {
           
            boolean validated= true; //(rec!=null && request.getParameter("diagnosisId")!=null && request.getParameter("procedureId")!=null);
            if (validated) {
                detail.setTreatmentId(ctr.convertToInt(rec));
                detail.setDoctorId(ctr.convertToInt(request.getParameter("doctorId")));
                detail.setDoctor(doctors.lookup(ctr.convertToInt(request.getParameter("doctorId"))));
                detail.setCommission(ctr.convertToDouble(request.getParameter("commission")));

                if (detail.save()) {
                    ctr.forward(frm+"?id="+rec, request, response);
                    return;
                    /*
                    page.text("Treatment Entry", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("Record successfully saved.","attribtext");
                            page.endCell();
                        page.endRow();
                        if (frm!=null && rec!=null) {
                            page.startRow();
                                page.startCell("","1","1","");
                                    page.text("Return to transaction","","","",frm+"?id="+rec,"25px","25px");
                                page.endCell();
                            page.endRow();
                        }                               
                    page.endTable(true);
                    page.flush();
                    view.finalize();
                    */
                }
            }
            
        }
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Treatment Commission", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");

            page.startForm("newentry", "newtreatmentmore", "post");
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Doctor:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        ListBox listbox=new ListBox();
                        ListBoxItem item=new ListBoxItem();
                        model.Doctor doctor;
                        doctors=new model.Doctors();
                        doctors.fetch(login.getAccountId());
                        for (int i=0; i<doctors.size(); i++) {
                            doctor=(model.Doctor)doctors.get(i);   
                            item=new ListBoxItem(""+doctor.getPartyId(),doctor.getFirstName()+" "+doctor.getLastName());
                            listbox.add(item);
                        }
                        page.inputSelect("doctorId",""+detail.getDoctorId(), listbox,"355px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Commission:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("commission", ""+detail.getCommission(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                        page.inputHidden("command", "1");
                        if (frm!=null) {
                            page.inputHidden("frm", frm);
                        } 
                        if (rec!=null) {
                            page.inputHidden("rec", rec);
                        }                         
                    page.endCell();
                page.endRow();  
                if (!detail.getErrMessage().equals("")) {
                    page.startRow();
                        page.startCell("","1","4","100%");
                            page.text(detail.getErrMessage(), "errortext");
                        page.endCell();
                    page.endRow();                
                }                
                page.startRow();
                    page.startCell("extralinkcell", "1", "4", "");
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
