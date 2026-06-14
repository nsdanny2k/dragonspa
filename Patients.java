/*
 * Patients.java
 * 
 * Created on Aug 25, 2007, 5:02:31 PM
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
public class Patients extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);
        Page page=view.initialize("Patient Records","global.css");
        

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("patients");
        request.getSession().setAttribute("login", login);
        if (!login.isAuthorized()) {
            ctr.forward("login", request, response);
            return;
        }        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Patient Records", "titletext", "patients.jpg", "", "", "210px", "125px", "300px", "35px", "3", "35px", "35px");
// ********************************
// ** MAIN SUB MENU **            
// ********************************            
            page.startTable("stdgrid","200px","175px","468px","30px","4");
                page.startRow();
                    page.startCell("extralinkcell","1","1","");
                        //page.text("Settings", "extralinktext", "", "", "settings", "", "");
                        //page.space();
                        //page.space();
                        page.text("Register a Patient", "extralinktext", "insert.jpg", "", "newpatient", "35px", "35px");
                    page.endCell();
                page.endRow();
            page.endTable(true);

            page.startTable("stdgrid","200px","220px","468px","30px","4");
                page.startRow();
                    page.startCell("extralinkcell","1","1","");
                        page.text("A", "extralinktext", "", "", "patients?command=a", "", "");
                        page.text("B", "extralinktext", "", "", "patients?command=b", "", "");
                        page.text("C", "extralinktext", "", "", "patients?command=c", "", "");
                        page.text("D", "extralinktext", "", "", "patients?command=d", "", "");
                        page.text("E", "extralinktext", "", "", "patients?command=e", "", "");
                        page.text("F", "extralinktext", "", "", "patients?command=f", "", "");
                        page.text("G", "extralinktext", "", "", "patients?command=g", "", "");
                        page.text("H", "extralinktext", "", "", "patients?command=h", "", "");
                        page.text("I", "extralinktext", "", "", "patients?command=i", "", "");
                        page.text("J", "extralinktext", "", "", "patients?command=j", "", "");
                        page.text("K", "extralinktext", "", "", "patients?command=k", "", "");
                        page.text("L", "extralinktext", "", "", "patients?command=l", "", "");
                        page.text("M", "extralinktext", "", "", "patients?command=m", "", "");
                        page.text("N", "extralinktext", "", "", "patients?command=n", "", "");
                        page.text("O", "extralinktext", "", "", "patients?command=o", "", "");
                        page.text("P", "extralinktext", "", "", "patients?command=p", "", "");
                        page.text("Q", "extralinktext", "", "", "patients?command=q", "", "");
                        page.text("R", "extralinktext", "", "", "patients?command=r", "", "");
                        page.text("S", "extralinktext", "", "", "patients?command=s", "", "");
                        page.text("T", "extralinktext", "", "", "patients?command=t", "", "");
                        page.text("U", "extralinktext", "", "", "patients?command=u", "", "");
                        page.text("V", "extralinktext", "", "", "patients?command=v", "", "");
                        page.text("W", "extralinktext", "", "", "patients?command=w", "", "");
                        page.text("X", "extralinktext", "", "", "patients?command=x", "", "");
                        page.text("Y", "extralinktext", "", "", "patients?command=y", "", "");
                        page.text("Z", "extralinktext", "", "", "patients?command=z", "", "");
                    page.endCell();
                page.endRow();
            page.endTable(true);
            
            page.startTable("boxgrid","200px","250px","468px","3");
                page.startRow();
                    page.startCell("", "1", "1", "");
                        page.text("Name", "attribtext", "", "", "", "", "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("Birthday", "attribtext", "", "", "", "", "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("Age", "attribtext", "", "", "", "", "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("Gender", "attribtext", "", "", "", "", "");
                    page.endCell();
                    page.startCell("", "1", "1", "");
                        page.text("Status", "attribtext", "", "", "", "", "");
                    page.endCell();
                page.endRow();
                
                String cmd=request.getParameter("command");
                if (cmd==null) {
                    cmd="a";
                }
                String pg=request.getParameter("pg");
                if (pg==null) {
                    pg="1";
                }
                int bar=20;
                int size=20;
                int iPg=Integer.valueOf(pg).intValue();
                model.Patient patient;
                model.Patients patients=new model.Patients();
                int count=patients.fetchAlphabetical(login.getAccountId(),cmd, iPg, size);
                for (int i=0; i<patients.size(); i++) {
                    patient=(model.Patient)patients.get(i);
                    page.startRow();
                        page.startCell("", "1", "1", "");
                            page.text(patient.getLastName()+", "+patient.getFirstName()+" "+patient.getMiddleName(), "extralinktext", "", "", "patientprofile?id="+patient.getPartyId()+"&pg="+pg+"&pattern="+cmd, "", "");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(ctr.convertDateToString(patient.getBirthDate()), "");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            if (patient.getBirthDate()==null) {
                                page.text("", "");
                            } else {
                                page.text(""+ctr.getAge(patient.getBirthDate()), "");
                            }
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(patient.getGenderDesc(patient.getGender()), "");
                        page.endCell();
                        page.startCell("", "1", "1", "");
                            page.text(patient.getMaritalStatusDesc(patient.getMaritalStatus()), "");
                        page.endCell();
                    page.endRow();                
                }                
                
                page.startRow();
                    page.startCell("", "1", "5", "");
                        if (patients.getSets()>1) {
                            for (int j=1; j<=patients.getSets(); j++) {
                                if (iPg==j) {
                                    page.text(""+j,"pagingtext");
                                } else {
                                    page.text("<a href=\"patients?command="+cmd+"&pg="+j+"\">"+j+"</a>&nbsp;","extralinktext","","","","","");
                                }
                                if ((j % bar)==0) {
                                    page.println("<br/>");
                                }
                            }
                        }
                    page.endCell();
                page.endRow();
            page.endTable(false);
            
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
