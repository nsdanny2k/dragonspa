/*
 * LayerLogo.java
 * 
 * Created on Aug 25, 2007, 6:08:41 PM
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
public class LayerLogo {

    public LayerLogo(Page region,HttpServletRequest request, HttpServletResponse response) {
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
            /*
            page.image("dentalworxlogo.gif","dentalworx","","35px","10px","165px","35px","3","","");
            page.text("Virtual Assistant", "logotaglinetext", "", "", "", "60px", "45px", "225px", "40px", "3", "", "");
             */ 
    }

}
