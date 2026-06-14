/*
 * InsuranceCode.java
 * 
 * Created on Sep 11, 2007, 12:00:06 PM
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
public class InsuranceCode extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("insurancecode");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }      
        

        Page page;
        if (login.getAccountId().equals("system")) {
            page=view.initialize("Insurance Code","global.css","System");    
        } else {
            page=view.initialize("Insurance Code","global.css");
        }                
        model.InsuranceCode code=new model.InsuranceCode();
        
        String save=request.getParameter("save");
        String cmd=request.getParameter("command");
        String id=request.getParameter("id");
        if (cmd!=null) {
            if (cmd.equals("2")) {
                code.add();
            }
        } else {
            if (id!=null) {
                code.fetch(ctr.convertToInt(id), login.getAccountId());
            }
        }
        if ((save!=null)) {
            code.setId(ctr.convertToInt(request.getParameter("id")));
            code.setCode(request.getParameter("code"));
            code.setDescription(request.getParameter("description"));
            code.setPhoneNumber(request.getParameter("phoneNumber"));
            code.setFaxNumber(request.getParameter("faxNumber"));
            code.setAddress1(request.getParameter("address1"));
            code.setAddress2(request.getParameter("address2"));
            code.setCity(request.getParameter("city"));
            code.setState(request.getParameter("state"));
            code.setZip(request.getParameter("zip"));
        }
        code.setAccountId(login.getAccountId());
        
        if ((save!=null)) {
            if (!(code.getCode().equals("")) && !(code.getDescription().equals(""))) {

                if (code.save()) {
                    page.text("Insurance Code", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                    page.startTable("stdgrid","200px","210px","468px","30px","4");
                        page.startRow();
                            page.startCell("", "1", "1", "");
                                page.text("Record successfully saved.","attribtext");
                            page.endCell();
                        page.endRow();
                        String pg=request.getParameter("pg");
                        if (pg!=null) {
                            page.startRow();
                                page.startCell("","1","1","");
                                    page.text("Return to list","","","","insurancecodes?pg="+pg,"25px","25px");
                                page.endCell();
                            page.endRow();
                        }                              
                        page.startRow();
                            page.startCell("","1","1","");
                                page.text("Add another record","","insert.jpg","","insurancecode?command=2","25px","25px");
                            page.endCell();
                        page.endRow();
                    page.endTable(true);
                    page.flush();
                    view.finalize();
                }
            }
        }
    
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Insurance Code", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
       

            page.startForm("editcode", "insurancecode", "post");
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Code:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("code", ( code.getCode()==null)?"":code.getCode(), "130px");
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
                        page.text("Description:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        page.inputText("description", (  code.getDescription()==null)?"":code.getDescription(), "370px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Phone#:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("phoneNumber", (code.getPhoneNumber()==null)?"":code.getPhoneNumber(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Fax#:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("faxNumber", (code.getFaxNumber()==null)?"":code.getFaxNumber(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Address :", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("address1", (code.getAddress1()==null)?"":code.getAddress1(), "130px");
                    page.endCell();
                    page.startCell("","1","2","50%");
                        page.inputText("address2", (code.getAddress2()==null)?"":code.getAddress2(), "97%");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("City:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("city", (code.getCity()==null)?"":code.getCity(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("State / Province:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("state", (code.getState()==null)?"":code.getState(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Zip:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("zip", (code.getZip()==null)?"":code.getZip(), "130px");
                        page.inputHidden("command", "1");
                        page.inputHidden("id", ""+code.getId());
                        String pg=request.getParameter("pg");
                        if (pg!=null) {
                            page.inputHidden("pg", pg);
                        }                              
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                    page.endCell();
                page.endRow();                
                if (!code.getErrMessage().equals("")) {
                    page.startRow();
                        page.startCell("","1","4","100%");
                            page.text(code.getErrMessage(), "errortext");
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
