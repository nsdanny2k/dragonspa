/*
 * LayerToolBarSystem.java
 * 
 * Created on Sep 9, 2007, 5:45:42 PM
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
public class LayerToolBarSystem {

    public LayerToolBarSystem(Page region,HttpServletRequest request, HttpServletResponse response) {
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
        
        
            page.startTable("stdgrid","500px","140px","150px","89px","4");
                page.startRow();
                    //page.startCell("tbarcell","1","1","");
                    //    page.text("Assistants", "tbartext", "", "", "assistants", "", "");
                    //page.endCell();
                    page.startCell("tbarcell","1","1","");
                        page.text("System", "tbartext", "", "", "system", "", "");
                    page.endCell();
                page.endRow();
            page.endTable(true);
            
            }
    }
}
