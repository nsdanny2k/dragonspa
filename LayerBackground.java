/*
 * LayerBackground.java
 * 
 * Created on Aug 25, 2007, 5:06:18 PM
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
public class LayerBackground {

    public LayerBackground(Page region,HttpServletRequest request, HttpServletResponse response) {
        this.response=response;
        this.request=request;
        page=region;
    }

    private HttpServletResponse response;
    private HttpServletRequest request;
    private Page page;
    
    public void render() {
// ********************************        
// ** BORDERS & BACKGROUNDS **        
// ********************************
            //page.image("vbar1.gif","","","0px","0px","61px","700px","1","","");
            page.image("hbar1.gif","","","28px","0px","732px","59px","2","","");
            page.image("hbar2.gif","","","28px","59px","732px","61px","2","","");
    }
    
}
