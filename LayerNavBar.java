/*
 * LayerNavBar.java
 * 
 * Created on Aug 25, 2007, 5:07:12 PM
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
public class LayerNavBar {

    public LayerNavBar(Page region,HttpServletRequest request, HttpServletResponse response) {
        this.response=response;
        this.request=request;
        page=region;
    }

    private HttpServletResponse response;
    private HttpServletRequest request;
    private Page page;
    
    public void render() {
// ********************************
// ** NAVIGATION BAR **
// ********************************            
            page.startTable("stdgrid","47px","175px","120px","500px","4");
                page.startRow();
                    page.startCell("navbarcell","1","1","");
                        page.image("yellowdot.gif", "", "", "10px", "10px");
                        page.text("Home", "sitemaptext", "home.jpg", "", "home", "30px", "30px");
                    page.endCell();
                page.endRow();
                /*
                page.startRow();
                    page.startCell("navbarcell","1","1","");
                        page.image("yellowdot.gif", "", "", "10px", "10px");
                        page.text("Company Profile", "sitemaptext", "", "", "company", "", "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("navbarcell","1","1","");
                        page.image("yellowdot.gif", "", "", "10px", "10px");
                        page.text("Contact Us", "sitemaptext", "", "", "contact", "", "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("navbarcell","1","1","");
                        page.image("yellowdot.gif", "", "", "10px", "10px");
                        page.text("Products", "sitemaptext", "", "", "products", "", "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("navbarcell","1","1","");
                        page.image("yellowdot.gif", "", "", "10px", "10px");
                        page.text("Services", "sitemaptext", "", "", "services", "", "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("navbarcell","1","1","");
                        page.image("yellowdot.gif", "", "", "10px", "10px");
                        page.text("Organization", "sitemaptext", "", "", "organization", "", "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("navbarcell","1","1","");
                        page.image("yellowdot.gif", "", "", "10px", "10px");
                        page.text("Sitemap", "sitemaptext", "", "", "sitemap", "", "");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("navbarcell","1","1","");
                        page.image("yellowdot.gif", "", "", "10px", "10px");
                        page.text("Help", "sitemaptext", "", "", "help", "", "");
                    page.endCell();
                page.endRow();
                 */
            page.endTable(true);
    }

}
