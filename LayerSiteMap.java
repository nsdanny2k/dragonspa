/*
 * LayerSiteMap.java
 * 
 * Created on Aug 25, 2007, 5:08:06 PM
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
public class LayerSiteMap {

    public LayerSiteMap(Page region,HttpServletRequest request, HttpServletResponse response) {
        this.response=response;
        this.request=request;
        page=region;
    }

    private HttpServletResponse response;
    private HttpServletRequest request;
    private Page page;
    
    public void render() {
// ********************************
// ** SITEMAP **            
// ********************************            
            page.image("yellowline.gif","","","200px","840px","472px","4px","3","","");
            /*
            page.image("yellowline.gif","","","200px","860px","472px","4px","3","","");
            page.startTable("stdgrid","185px","835px","500px","10px","4");
                page.startRow();
                    page.startCell("tbarcell","1","1","");
                        page.text("Home", "sitemaptext", "", "", "home", "", "");
                        page.text("Company Profile", "sitemaptext", "", "", "company", "", "");
                        page.text("Contact Us", "sitemaptext", "", "", "contact", "", "");
                        page.text("Products", "sitemaptext", "", "", "products", "", "");
                        page.text("Services", "sitemaptext", "", "", "services", "", "");
                        page.text("Organization", "sitemaptext", "", "", "organization", "", "");
                        page.text("Sitemap", "sitemaptext", "", "", "sitemap", "", "");
                        page.text("Help", "sitemaptext", "", "", "help", "", "");
                    page.endCell();
                page.endRow();
            page.endTable(true);
            */
    }

}
