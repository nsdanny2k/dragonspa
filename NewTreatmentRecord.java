/*
 * NewTreatment.java
 * 
 * Created on Aug 25, 2007, 11:34:01 PM
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
public class NewTreatmentRecord extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Treatment Entry","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("newtreatmentrecord");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }      
        
        model.TreatmentDetail detail=new model.TreatmentDetail();
        model.DiagnosisCodes dcodes=new model.DiagnosisCodes();
        model.ProcedureCodes pcodes=new model.ProcedureCodes();
        
        String frm=request.getParameter("frm");
        String rec=request.getParameter("rec");
        String save=request.getParameter("save");
        if ((save==null)) {
            detail.add();
        } else {
        }
        
        if ((save!=null)) {
           
            //boolean validated=(rec!=null && request.getParameter("diagnosisId")!=null && request.getParameter("procedureId")!=null);
            boolean validated=(rec!=null && request.getParameter("procedureId")!=null);
            if (validated) {
                detail.setTreatmentId(ctr.convertToInt(rec));
                /*
                detail.setTooth(request.getParameter("tooth"));
                detail.setDiagnosisId(ctr.convertToInt(request.getParameter("diagnosisId")));
                detail.setDiagnosis(dcodes.lookupDescription(ctr.convertToInt(request.getParameter("diagnosisId"))));
                */
                detail.setProcedureId(ctr.convertToInt(request.getParameter("procedureId")));
                detail.setProcedure(pcodes.lookupDescription(ctr.convertToInt(request.getParameter("procedureId"))));
                double charge=ctr.convertToDouble(request.getParameter("charge"));
                if (charge==0) {
                    detail.setCharge(pcodes.lookupRate(ctr.convertToInt(request.getParameter("procedureId"))));
                } else {
                    detail.setCharge(charge);
                }
                detail.setPaid(0);
                detail.setInsCharge(0);
                detail.setBalance(0);
                detail.setRemarks(request.getParameter("remarks"));

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
            page.text("Charge Entry", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");

            page.startForm("newentry", "newtreatmentrecord", "post");
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                /*
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Tooth:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("tooth", ( detail.getTooth()==null)?"":detail.getTooth(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                    page.endCell();
                page.endRow();  
                 */
                 /*
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Diagnosis:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        ListBox listbox=new ListBox();
                        ListBoxItem item=new ListBoxItem();
                        model.DiagnosisCode dcode;
                        dcodes=new model.DiagnosisCodes();
                        dcodes.fetch(login.getAccountId());
                        for (int i=0; i<dcodes.size(); i++) {
                            dcode=(model.DiagnosisCode)dcodes.get(i);   
                            item=new ListBoxItem(""+dcode.getId(),dcode.getCode()+" - "+dcode.getDescription());
                            listbox.add(item);
                        }
                        page.inputSelect("diagnosisId",""+detail.getDiagnosisId(), listbox,"355px");
                    page.endCell();
                page.endRow();
                */
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Item:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
    ListBox listbox=new ListBox();
    ListBoxItem item=new ListBoxItem();
                       listbox=new ListBox();
                        model.ProcedureCode pcode;
                        pcodes=new model.ProcedureCodes();
                        pcodes.fetch(login.getAccountId());
                        for (int i=0; i<pcodes.size(); i++) {
                            pcode=(model.ProcedureCode)pcodes.get(i);                        
                            item=new ListBoxItem(""+pcode.getId(),pcode.getCode()+" - "+pcode.getDescription());
                            listbox.add(item);
                        }
                        page.inputSelect("procedureId",""+detail.getProcedureId(), listbox,"355px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Charge:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("charge", ""+detail.getCharge(), "130px");
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
