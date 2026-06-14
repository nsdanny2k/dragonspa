/*
 * LayerGroupAll.java
 * 
 * Created on Aug 25, 2007, 5:08:41 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dentalworx.view;

import com.dentalworx.controllers.*;
import com.dentalworx.view.LayerBackground;
import com.ownx.webpages.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nsdan2k
 */
public class LayerGroupAll {

    public LayerGroupAll(Page region,HttpServletRequest request, HttpServletResponse response) {
        this.response=response;
        this.request=request;
        page=region;
    }

    private HttpServletResponse response;
    private HttpServletRequest request;
    private Page page;
    
    public void render() {
        LayerTools tls=new LayerTools(page,request,response);
        tls.render();
        LayerLogo lgo=new LayerLogo(page,request,response);
        lgo.render();
        LayerBackground bkg=new LayerBackground(page,request,response);
        bkg.render();
        LayerNavBar nbr=new LayerNavBar(page,request,response);
        nbr.render();
        LayerSiteMap smp=new LayerSiteMap(page,request,response);
        smp.render();
        LayerToolBar tbr=new LayerToolBar(page,request,response);
        tbr.render();
        LayerTitleBar ttl=new LayerTitleBar(page,request,response);
        ttl.render();
    }

}
