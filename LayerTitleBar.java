/*
 * LayerTitleBar.java
 * 
 * Created on Aug 25, 2007, 5:20:22 PM
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
public class LayerTitleBar {

    public LayerTitleBar(Page region,HttpServletRequest request, HttpServletResponse response) {
        this.response=response;
        this.request=request;
        page=region;
    }

    private HttpServletResponse response;
    private HttpServletRequest request;
    private Page page;
    
    public void render() {
// ********************************
// ** TITLEBAR **            
// ********************************          
            page.image("yellowline.gif","","","200px","120px","472px","4px","3","","");
            page.image("yellowline.gif","","","200px","165px","472px","4px","3","","");
    }

}
