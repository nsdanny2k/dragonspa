/*
 * LayerToolBarBasic.java
 * 
 * Created on Aug 25, 2007, 5:48:55 PM
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
public class LayerToolBarBasic {

    public LayerToolBarBasic(Page region,HttpServletRequest request, HttpServletResponse response) {
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
            page.startTable("stdgrid","225px","100px","392px","89px","4");
                page.startRow();
                    page.startCell("tbarcell","1","1","");
                        page.text("Examination", "tbartext", "", "", "examination", "", "");
                    page.endCell();
                    page.startCell("tbarcell","1","1","");
                        page.text("Records", "tbartext", "", "", "patients", "", "");
                    page.endCell();
                    page.startCell("tbarcell","1","1","");
                        page.text("Payments", "tbartext", "", "", "payments", "", "");
                    page.endCell();
                    page.startCell("tbarcell","1","1","");
                        page.text("Ledger", "tbartext", "", "", "ledger", "", "");
                    page.endCell();
                    page.startCell("tbarcell","1","1","");
                        page.text("Appointments", "tbartext", "", "", "appointments", "", "");
                    page.endCell();
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
