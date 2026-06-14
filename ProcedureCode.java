/*
 * ProcedureCode.java
 * 
 * Created on Sep 11, 2007, 12:01:00 PM
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
public class ProcedureCode extends HttpServlet {
   
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
        login.setLastForm("procedurecode");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }      
        

        Page page;
        if (login.getAccountId().equals("system")) {
            page=view.initialize("Item Code","global.css","System");    
        } else {
            page=view.initialize("Item Code","global.css");
        }                
        model.ProcedureCode code=new model.ProcedureCode();
        
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
            code.setRate(ctr.convertToDouble(request.getParameter("rate")));
        }
        code.setAccountId(login.getAccountId());
        
        if ((save!=null)) {
            if (!(code.getCode().equals("")) && !(code.getDescription().equals(""))) {

                if (code.save()) {
                    page.text("Item Code", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
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
                                    page.text("Return to list","","","","procedurecodes?pg="+pg,"25px","25px");
                                page.endCell();
                            page.endRow();
                        } 
                        page.startRow();
                            page.startCell("","1","1","");
                                page.text("Add another record","","insert.jpg","","procedurecode?command=2","25px","25px");
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
            page.text("Item Code", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
       

            page.startForm("editcode", "procedurecode", "post");
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
                        page.text("Rate:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("rate", ""+code.getRate(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.space();
                        page.inputHidden("command", "1");
                        page.inputHidden("id", ""+code.getId());                        
                        
                        String pg=request.getParameter("pg");
                        if (pg!=null) {
                            page.inputHidden("pg", pg);
                        } 
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
