/*
 * Login.java
 * 
 * Created on Aug 30, 2007, 4:03:57 PM
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
public class Login extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            login=new model.Login(); 
        }        
        String lastf=login.getLastForm();
        if (lastf.equals("")) {
            login.setLastForm("home");
            lastf="home";
        }
        String cmd=request.getParameter("command");
        if (!(cmd==null)) {
            if (cmd.equals("1")) {
                if (login.signIn(request.getParameter("user"), request.getParameter("password"))) {
                    request.getSession().setAttribute("login", login);
                    if (login.getAccountId().equals("system")) {
                        ctr.forward("system", request, response);
                    } else {
                        ctr.forward(lastf, request, response);
                    }
                    return;
                }
            } else if (cmd.equals("2")) {
                login.setLastForm("home");
                lastf="home";                
                login.signOut();
                request.getSession().setAttribute("login", login);
                ctr.forward("home", request, response);
                return;
            }
        }
        request.getSession().setAttribute("login", login);
        
        PageView view=new PageView(request,response);
        Page page=view.initialize("Login","global.css");
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Login", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");

            page.startForm("newentry", "login", "post");
        
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("User Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        page.inputText("user",login.getUser() , "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Password:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","75%");
                        page.inputPassword("password", "", "130px");
                        page.inputHidden("command", "1");
                    page.endCell();
                page.endRow();
                //page.startRow();
                //    page.startCell("extralinkcell","1","2","");
                //        page.inputSubmit("ok", "Ok","60px");
                //        page.space();
                //        page.inputReset("cancel", "Cancel","60px");
                //    page.endCell();
                //page.endRow();
          //  page.endTable(true);

          //  page.startTable("stdgrid","200px","235px","468px","30px","4");
                page.startRow();
                    //page.startCell("extralinkcell", "1", "1", "");
                    page.startCell("extralinkcell","1","2","100%");
                        page.text("Ok", "attribtext", "", "", "", "30px", "30px");
                        page.inputImage("ok", "check.jpg", "");
                        page.text("Cancel", "attribtext", "", "", "", "30px", "30px");
                        page.inputImage("cancel", "cross.jpg", "");
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
