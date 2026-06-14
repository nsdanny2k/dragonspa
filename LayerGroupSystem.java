/*
 * LayerGroupSystem.java
 * 
 * Created on Sep 9, 2007, 5:44:05 PM
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
public class LayerGroupSystem {

    public LayerGroupSystem(Page region,HttpServletRequest request, HttpServletResponse response) {
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
        LayerToolBarSystem tbr=new LayerToolBarSystem(page,request,response);
        tbr.render();
        LayerTitleBar ttl=new LayerTitleBar(page,request,response);
        ttl.render();
    }

}
