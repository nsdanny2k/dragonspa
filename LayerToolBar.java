/*
 * LayerToolBar.java
 * 
 * Created on Aug 25, 2007, 5:07:41 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dentalworx.view;

import com.ownx.webpages.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nsdan2k
 */
public class LayerToolBar {

    public LayerToolBar(Page region,HttpServletRequest request, HttpServletResponse response) {
        this.response=response;
        this.request=request;
        page=region;
    }

    private HttpServletResponse response;
    private HttpServletRequest request;
    private Page page;
    
    public void render() {
            model.Login login=(model.Login)request.getSession().getAttribute("login");
            if (login==null) {
                login=new model.Login(); 
            }
            
            if (login.isAuthorized()) {
        
// ********************************
// ** PRIMARY TOOLBAR **           
// ********************************            
            page.startTable("stdgrid","200px","40px","400px","4");
                page.startRow();
                    page.startCell("tbarcell","1","1","");
                        page.image("chair.png", "Frontdesk", "frontdesk", "50px", "50px");
                    page.endCell();
                    page.startCell("tbarcell","1","1","");
                        page.image("patients.jpg", "Patient Records", "patients", "50px", "50px");
                    page.endCell();
                    /*
                    page.startCell("tbarcell","1","1","");
                        page.image("payments.jpg", "Payment Register", "payments", "50px", "50px");
                    page.endCell();
                     */
                    /*
                    page.startCell("tbarcell","1","1","");
                        page.image("ledger.jpg", "Ledger", "ledger", "50px", "50px");
                    page.endCell();
                    page.startCell("tbarcell","1","1","");
                        page.image("appointments.jpg", "Appointment Book", "appointments", "50px", "50px");
                    page.endCell();
                     */
                    if (login.getAccountNumber()==1) {
                        page.startCell("tbarcell","1","1","");
                            page.image("reports.jpg", "Reports", "reports", "50px", "50px");
                        page.endCell();
                    }
                page.endRow();
                page.startRow();
                    page.startCell("tbarcell","1","1","");
                        page.text("Frontdesk", "tbartext", "", "", "frontdesk", "", "");
                    page.endCell();
                    page.startCell("tbarcell","1","1","");
                        page.text("Records", "tbartext", "", "", "guests", "", "");
                    page.endCell();
                    /*
                    page.startCell("tbarcell","1","1","");
                        page.text("Payments", "tbartext", "", "", "payments", "", "");
                    page.endCell();
                     */
                    /*
                    page.startCell("tbarcell","1","1","");
                        page.text("Ledger", "tbartext", "", "", "ledger", "", "");
                    page.endCell();
                    page.startCell("tbarcell","1","1","");
                        page.text("Appointments", "tbartext", "", "", "appointments", "", "");
                    page.endCell();
                     */
                    if (login.getAccountNumber()==1) {
                        page.startCell("tbarcell","1","1","");
                            page.text("Reports", "tbartext", "", "", "reports", "", "");
                        page.endCell();
                    }
                page.endRow();
            page.endTable(true);
// ********************************
// ** EXTENDED TOOLBAR **           
// ********************************            
                if (login.getAccountNumber()==1) {
                    page.startTable("stdgrid","500px","140px","150px","89px","4");
                        page.startRow();
                            //page.startCell("tbarcell","1","1","");
                            //    page.text("Assistants", "tbartext", "", "", "assistants", "", "");
                            //page.endCell();
                            //page.startCell("tbarcell","1","1","");
                            //    page.text("Others", "tbartext", "", "", "othercommands", "", "");
                            //page.endCell();
                            page.startCell("tbarcell","1","1","");
                                page.text("Setup", "tbartext", "", "", "setup", "", "");
                            page.endCell();
                        page.endRow();
                    page.endTable(true);
                }
            
            }

    }

}