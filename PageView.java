/*
 * PageView.java
 * 
 * Created on Aug 25, 2007, 6:12:48 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dentalworx.view;

import com.dentalworx.view.LayerGroupBasic;
import com.dentalworx.view.LayerGroupAll;
import com.ownx.webpages.Page;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nsdan2k
 */
public class PageView {

    public PageView(HttpServletRequest request, HttpServletResponse response) {
        this.response=response;
        this.request=request;
    }

    private HttpServletResponse response;
    private HttpServletRequest request;

    public Page initialize(String title, String stylesheet) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            Page page=new Page(title,stylesheet,out);
            page.initialize();        
            //LayerGroupBasic rgn=new LayerGroupBasic(page,request,response);
            //rgn.render();
            LayerGroupAll rgn=new LayerGroupAll(page,request,response);
            rgn.render();  
            return page;
        }
        catch (Exception e) {
            return null;
        }
    }

    public Page initialize(String title, String stylesheet,String view) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            Page page=new Page(title,stylesheet,out);
            page.initialize();        
            if (view.toUpperCase().equals("BASIC")) {
                LayerGroupBasic basic=new LayerGroupBasic(page,request,response);
                basic.render();
            } else if (view.toUpperCase().equals("FULL")) {
                LayerGroupAll full=new LayerGroupAll(page,request,response);
                full.render();
            } else if (view.toUpperCase().equals("SYSTEM")) {
                LayerGroupSystem full=new LayerGroupSystem(page,request,response);
                full.render();
            } else if (view.toUpperCase().equals("LONG")) {
                LayerGroupLong full=new LayerGroupLong(page,request,response);
                full.render();            
            } else if (view.toUpperCase().equals("SYSTEMLONG")) {
                LayerGroupSystemLong full=new LayerGroupSystemLong(page,request,response);
                full.render();            
            }            
            return page;
        }
        catch (Exception e) {
            return null;
        }

    }    
    
    public void finalize() {
    }
}
