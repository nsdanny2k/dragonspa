/*
 * NewAccount.java
 * 
 * Created on Sep 15, 2007, 10:21:41 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dentalworx.controllers;

import com.dentalworx.view.PageView;
import com.ownx.util.WebController;
import com.ownx.webpages.ListBox;
import com.ownx.webpages.ListBoxItem;
import com.ownx.webpages.Page;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author nsdan2k
 */
public class NewAccount extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PageView view=new PageView(request,response);

        WebController ctr=new WebController();
        model.Login login=(model.Login)request.getSession().getAttribute("login");
        if (login==null) {
            return;
        }
        login.setLastForm("account");
        request.getSession().setAttribute("login", login);
        //if (!login.isAuthorized()) {
        //    ctr.forward("login", request, response);
        //    return;
        //}      
        
        Page page;
        //if (login.getAccountId().equals("system")) {
        //    page=view.initialize("Register New Account","global.css","System");    
        //} else {
            page=view.initialize("Register New Account","global.css");
        //}
        
        model.Account account=(model.Account)request.getSession().getAttribute("account");
        if (account==null) { account=new model.Account(); }
        
        String save=request.getParameter("save");
        //String id=login.getAccountId();
        //account.fetchMain(id);
        account.add();
        if ((save!=null)) {
            account.setFirstName(request.getParameter("firstName"));
            account.setMiddleName(request.getParameter("middleName"));
            account.setLastName(request.getParameter("lastName"));
            account.setGender(request.getParameter("gender"));
            account.setEmailAddress(request.getParameter("emailAddress"));
            account.setPhoneNumber(request.getParameter("phoneNumber"));
            account.setAddress1(request.getParameter("address1"));
            account.setAddress2(request.getParameter("address2"));
            account.setCity(request.getParameter("city"));
            account.setState(request.getParameter("state"));
            account.setCountry(request.getParameter("country"));
            account.setZip(request.getParameter("zip"));
            account.setPassword(request.getParameter("password"));
            account.setPartyId(request.getParameter("partyId"));
            account.setCardType(request.getParameter("cardType"));
            account.setCardNumber(request.getParameter("cardNumber"));
            account.setCardExpMonth(request.getParameter("cardExpMonth"));
            account.setCardExpYear(request.getParameter("cardExpYear"));
            account.setCardId(request.getParameter("cardId"));
            account.setCardHolderName(request.getParameter("cardHolderName"));
            account.setBillingAddress(request.getParameter("billingAddress"));
            account.setBillingCity(request.getParameter("billingCity"));
            account.setBillingState(request.getParameter("billingState"));
            account.setBillingZip(request.getParameter("billingZip"));
            account.setAccountNumber(1);
            account.setExtended(0);
        }
        //account.setAccountId(login.getAccountId());

        String errCode="";
        boolean bSaveSuccess2=false;
        boolean bSaveSuccess3=false;
        if ((save!=null)) {
            if (!(account.getFirstName().equals("")) && !(account.getLastName().equals(""))) {
                if (account.save()) {
                    if (errCode.equals("")) {
                        page.text("Register New Account", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
                        page.startTable("stdgrid","200px","210px","468px","30px","4");
                            page.startRow();
                                page.startCell("", "1", "1", "");
                                    page.text("Registration successfull.","attribtext");
                                page.endCell();
                            page.endRow();
                            //page.startRow();
                            //    page.startCell("", "1", "1", "");
                            //        page.text("Return to record","","insert.jpg","","newaccount","25px","25px");
                            //    page.endCell();
                            //page.endRow();
                        page.endTable(true);
                        page.flush();
                        view.finalize();
                    }
                }
            }
        }
        request.getSession().setAttribute("account",account);        
// ********************************
// ** TITLE **            
// ********************************            
            page.text("Register New Account", "titletext", "", "", "", "210px", "125px", "300px", "35px", "3", "", "");
       

            page.startForm("editaccount", "newaccount", "post");
            //page.startTable("stdgrid","200px","220px","468px","30px","4");
            //    page.startRow();
            //        page.startCell("extralinkcell", "1", "1", "");
            //            page.text("Save", "attribtext", "", "", "", "30px", "30px");
            //            page.inputImage("save", "saveit.jpg", "");
                        //page.text("Ok", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("ok", "check.jpg", "");
                        //page.text("Cancel", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("cancel", "cross.jpg", "");
            //        page.endCell();
            //    page.endRow();
            //page.endTable(true);
        
            //page.startTable("boxgrid","200px","270px","472px","300px","4");
            page.startTable("boxgrid","200px","175px","472px","300px","4");
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("First Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("firstName", ( account.getFirstName()==null)?"":account.getFirstName(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Middle Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("middleName", ( account.getMiddleName()==null)?"":account.getMiddleName(), "130px");
                    page.endCell();
                page.endRow();  
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Last Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("lastName", ( account.getLastName()==null)?"":account.getLastName(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Gender:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        ListBox listbox=new ListBox();
                        ListBoxItem item=new ListBoxItem();
                        item=new ListBoxItem("M","Male");
                        listbox.add(item);
                        item=new ListBoxItem("F","Female");
                        listbox.add(item);
                        page.inputSelect("gender", ( account.getGender()==null)?"M":account.getGender(), listbox,"135px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Email :", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("emailAddress", (account.getEmailAddress()==null)?"":account.getEmailAddress(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Phone#:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("phoneNumber", (account.getPhoneNumber()==null)?"":account.getPhoneNumber(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Address :", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("address1", (account.getAddress1()==null)?"":account.getAddress1(), "130px");
                    page.endCell();
                    page.startCell("","1","2","50%");
                        page.inputText("address2", (account.getAddress2()==null)?"":account.getAddress2(), "97%");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("City:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("city", (account.getCity()==null)?"":account.getCity(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("State / Province:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("state", (account.getState()==null)?"":account.getState(), "130px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Country:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        listbox=new ListBox();
                        item=new ListBoxItem();
                        model.Country country;
                        model.Countries countries=new model.Countries();
                        countries.fetch();
                        for (int i=0; i<countries.size(); i++) {
                            country=(model.Country)countries.get(i);                        
                            item=new ListBoxItem(country.getCode(),country.getDescription());
                            listbox.add(item);
                        }
                        page.inputSelect("country", ( account.getCountry()==null)?"US":account.getCountry(), listbox,"135px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Zip:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("zip", (account.getZip()==null)?"":account.getZip(), "130px");
                        page.inputHidden("command", "1");
                    page.endCell();
                page.endRow();

                page.startRow();
                    page.startCell("","1","4","100%");
                        page.space();
                    page.endCell();
                page.endRow();   

                page.startRow();
                    page.startCell("","1","4","100%");
                        page.text("Primary Account", "attribtext");
                    page.endCell();
                page.endRow();                 
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("partyId",account.getPartyId(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Password:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("password",account.getPassword(), "130px");
                    page.endCell();
                page.endRow();
                
                page.startRow();
                    page.startCell("","1","4","100%");
                        page.space();
                    page.endCell();
                page.endRow();                 
                page.startRow();
                    page.startCell("","1","4","100%");
                        page.text("Billing Information", "attribtext");
                    page.endCell();
                page.endRow();                     
                 page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Credit Card Type:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        listbox=new ListBox();
                        item=new ListBoxItem();
                        item=new ListBoxItem("Visa","Visa");
                        listbox.add(item);
                        item=new ListBoxItem("MasterCard","MasterCard");
                        listbox.add(item);
                        item=new ListBoxItem("AmericanExpress","AmericanExpress");
                        listbox.add(item);
                        item=new ListBoxItem("Discover","Discover");
                        listbox.add(item);
                        page.inputSelect("cardType", ( account.getCardType()==null)?"Visa":account.getCardType(), listbox,"135px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Credit Card Number:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("cardNumber", (account.getCardNumber()==null)?"":account.getCardNumber(), "130px");
                    page.endCell();
                 page.endRow();
                 page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Card Expiration:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        listbox=new ListBox();
                        item=new ListBoxItem();
                        item=new ListBoxItem("01","01");
                        listbox.add(item);
                        item=new ListBoxItem("02","02");
                        listbox.add(item);
                        item=new ListBoxItem("03","03");
                        listbox.add(item);
                        item=new ListBoxItem("04","04");
                        listbox.add(item);
                        item=new ListBoxItem("05","05");
                        listbox.add(item);
                        item=new ListBoxItem("06","06");
                        listbox.add(item);
                        item=new ListBoxItem("07","07");
                        listbox.add(item);
                        item=new ListBoxItem("08","08");
                        listbox.add(item);
                        item=new ListBoxItem("09","09");
                        listbox.add(item);
                        item=new ListBoxItem("10","10");
                        listbox.add(item);
                        item=new ListBoxItem("11","11");
                        listbox.add(item);
                        item=new ListBoxItem("12","12");
                        listbox.add(item);
                        page.inputSelect("cardExpMonth", ( account.getCardExpMonth()==null)?"01":account.getCardExpMonth(), listbox,"50px");
                        listbox=new ListBox();
                        item=new ListBoxItem();
                        item=new ListBoxItem("2007","2007");
                        listbox.add(item);
                        item=new ListBoxItem("2008","2008");
                        listbox.add(item);
                        item=new ListBoxItem("2009","2009");
                        listbox.add(item);
                        item=new ListBoxItem("2010","2010");
                        listbox.add(item);
                        item=new ListBoxItem("2011","2011");
                        listbox.add(item);
                        item=new ListBoxItem("2012","2012");
                        listbox.add(item);
                        item=new ListBoxItem("2013","2013");
                        listbox.add(item);
                        item=new ListBoxItem("2014","2014");
                        listbox.add(item);
                        item=new ListBoxItem("2015","2015");
                        listbox.add(item);
                        item=new ListBoxItem("2016","2016");
                        listbox.add(item);
                        item=new ListBoxItem("2017","2017");
                        listbox.add(item);
                        item=new ListBoxItem("2018","2018");
                        listbox.add(item);
                        page.inputSelect("cardExpYear", ( account.getCardExpYear()==null)?"2007":account.getCardExpYear(), listbox,"75px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Card ID:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("cardId", (account.getCardId()==null)?"":account.getCardId(), "130px");
                    page.endCell();
                page.endRow();
                 page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Card Holder Name:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        page.inputText("cardHolderName", (account.getCardHolderName()==null)?"":account.getCardHolderName(), "300px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Billling Address :", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        page.inputText("billingAddress", (account.getBillingAddress()==null)?"":account.getBillingAddress(), "300px");
                    page.endCell();
                page.endRow();
                page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Billing City:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("billingCity", (account.getBillingCity()==null)?"":account.getBillingCity(), "130px");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.text("Billing State / Province:", "attribtext");
                    page.endCell();
                    page.startCell("","1","1","25%");
                        page.inputText("billingState", (account.getBillingState()==null)?"":account.getBillingState(), "130px");
                    page.endCell();
                page.endRow();                
                 page.startRow();
                    page.startCell("","1","1","25%");
                        page.text("Billing Zip:", "attribtext");
                    page.endCell();
                    page.startCell("","1","3","75%");
                        page.inputText("billingZip", (account.getBillingZip()==null)?"":account.getBillingZip(), "130px");
                    page.endCell();
                page.endRow();
                if (!errCode.equals("")) {
                    page.startRow();
                        page.startCell("","1","4","100%");
                            page.text(errCode, "errortext");
                        page.endCell();
                    page.endRow();                
                }
                if (!account.getErrMessage().equals("")) {
                    page.startRow();
                        page.startCell("","1","4","100%");
                            page.text(account.getErrMessage(), "errortext");
                        page.endCell();
                    page.endRow();                
                }
                page.startRow();
                    page.startCell("extralinkcell", "1", "4", "");
                        //page.text("Save Record", "attribtext", "", "", "", "30px", "30px");
                        //page.inputImage("save", "saveit.jpg", "");
                        page.inputSubmit("save", "Process Credit Card");
                    page.endCell();
                page.endRow();                
            page.endTable(true);
            page.endForm();
        
        page.flush();
        view.finalize();
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
