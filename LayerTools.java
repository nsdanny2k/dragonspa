/*
 * LayerTools.java
 * 
 * Created on Aug 25, 2007, 5:06:44 PM
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
public class LayerTools {

    public LayerTools(Page region,HttpServletRequest request, HttpServletResponse response) {
        this.response=response;
        this.request=request;
        page=region;
    }

    private HttpServletResponse response;
    private HttpServletRequest request;
    private Page page;
    
    public void render() {
// ********************************
// ** LOGIN TOOLBAR **            
// ********************************        
            model.Login login=(model.Login)request.getSession().getAttribute("login");
            if (login==null) {
                login=new model.Login(); 
                //debug
                //login.signIn("demo", "demo");
                request.getSession().setAttribute("login", login);
            }
            
            if (login.isAuthorized()) {
                page.image("logout.gif","login","login?command=2","570px","5px","80px","40px","3","","");
                page.text(login.getDisplayName(), "usertext", "", "", "", "470px", "5px", "100px", "40px", "3", "", "");
            } else {
                //page.image("login.gif","login","login","570px","5px","80px","40px","3","","");
                page.image("login.gif","login","login","570px","35px","80px","40px","3","","");
            }
// ********************************
// ** SEARCH TOOLBAR **            
// ********************************            
            page.startForm("searchform","search","post");
            if (login.isAuthorized()) {
                page.startTable("stdgrid","215px","0px","225px","40px","4");
            } else {
                page.startTable("stdgrid","215px","30px","225px","40px","4");
            }
                /*
                page.startRow();
                    page.startCell("","1","1","");
                        page.text("Search", "searchtext", "", "", "", "", "");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.inputText("SearchText", "", "90px");
                    page.endCell();
                    page.startCell("","1","1","");
                        page.inputImage("SearchGo", "go.gif", "Go");
                    page.endCell();
                page.endRow();
                 */
            page.endTable(true);
            page.endForm();
    }

}
