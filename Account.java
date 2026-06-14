/*
 * Patient.java
 * 
 * Created on Sep 1, 2007, 9:49:24 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.ownx.db.SQLHelper;
import com.ownx.util.WebController;
import general.DBConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author nsdan2k
 */
public class Account {

    public Account() {
        this.setErrMessage("");
    }
    private String partyId;
    private String accountId;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String emailAddress;
    private String PhoneNumber;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String cardType;
    private String cardNumber;
    private String cardExpMonth;
    private String cardExpYear;
    private String cardId;
    private String cardHolderName;
    private String billingAddress;
    private String billingCity;
    private String billingState;
    private String billingZip;
    private int accountNumber;
    private String notation;
    private String errMessage;
    private boolean newrecord;
    private int extended;

    public int getExtended() {
        return extended;
    }

    public void setExtended(int extended) {
        this.extended = extended;
    }
    
    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public String getCardExpMonth() {
        return cardExpMonth;
    }

    public void setCardExpMonth(String cardExpMonth) {
        this.cardExpMonth = cardExpMonth;
    }

    public String getCardExpYear() {
        return cardExpYear;
    }

    public void setCardExpYear(String cardExpYear) {
        this.cardExpYear = cardExpYear;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public boolean search(String name) {
        return false;
    }

    
    public boolean fetchMain(String account) {
        boolean retval=false;
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        try {
            Connection c=db.getConnection();
            this.setErrMessage("");
            SQLHelper h=new SQLHelper();

            String sql="SELECT * FROM account WHERE ACCOUNT_ID=\""+account+"\" AND ACCOUNT_NUMBER=1";
            Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
            ResultSet r=s.executeQuery(sql);
            while (r.next()) {
                retval= fetch(r.getString("PARTY_ID"));
            }
            return retval;
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
            return false;
        }            
    }    
    
    public boolean fetchSecondary(String account) {
        boolean retval=false;
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();

        try {
            Connection c=db.getConnection();
            this.setErrMessage("");
            SQLHelper h=new SQLHelper();

            String sql="SELECT * FROM account WHERE ACCOUNT_ID=\""+account+"\" AND ACCOUNT_NUMBER=2";
            Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
            ResultSet r=s.executeQuery(sql);
            while (r.next()) {
                retval= fetch(r.getString("PARTY_ID"));
            }
            return retval;
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
            return false;
        }            
    }   

    public boolean fetchTertiary(String account) {
        boolean retval=false;
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        try {
            Connection c=db.getConnection();
            this.setErrMessage("");
            SQLHelper h=new SQLHelper();

            String sql="SELECT * FROM account WHERE ACCOUNT_ID=\""+account+"\" AND ACCOUNT_NUMBER=3";
            Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
            ResultSet r=s.executeQuery(sql);
            while (r.next()) {
                retval= fetch(r.getString("PARTY_ID"));
            }
            return retval;
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
            return false;
        }            
    }       
    public boolean fetch(String id) {
        boolean retval=false;
        newrecord=true;
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        try {
            Connection c=db.getConnection();
            this.setErrMessage("");
            SQLHelper h=new SQLHelper();

            String sql="SELECT * FROM account WHERE PARTY_ID=\""+id+"\"";
            Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
            ResultSet r=s.executeQuery(sql);
            while (r.next()) {
                this.setPartyId(r.getString("PARTY_ID"));
                this.setFirstName(r.getString("FIRST_NAME"));
                this.setMiddleName(r.getString("MIDDLE_NAME"));
                this.setLastName(r.getString("LAST_NAME"));
                this.setGender(r.getString("GENDER"));
                this.setPhoneNumber(r.getString("PHONE_NUMBER"));
                this.setAddress1(r.getString("ADDRESS1"));
                this.setAddress2(r.getString("ADDRESS2"));
                this.setCity(r.getString("CITY"));
                this.setState(r.getString("STATE"));
                this.setCountry(r.getString("COUNTRY"));
                this.setZip(r.getString("ZIP"));
                this.setCardType(r.getString("CARD_TYPE"));
                this.setCardNumber(r.getString("CARD_NUMBER"));
                this.setCardExpMonth(r.getString("CARD_EXP_MONTH"));
                this.setCardExpYear(r.getString("CARD_EXP_YEAR"));
                this.setCardId(r.getString("CARD_ID"));
                this.setCardHolderName(r.getString("CARD_HOLDER_NAME"));
                this.setBillingAddress(r.getString("BILLING_ADDRESS"));
                this.setBillingCity(r.getString("BILLING_CITY"));
                this.setBillingState(r.getString("BILLING_STATE"));
                this.setBillingZip(r.getString("BILLING_ZIP"));
                this.setAccountId(r.getString("ACCOUNT_ID"));      
                this.setPassword(r.getString("PASSWORD"));
                this.setAccountNumber(r.getInt("ACCOUNT_NUMBER"));
                this.setNotation(r.getString("NOTATION"));
                this.setExtended(r.getInt("EXTENDED"));
                newrecord=false;
                retval= true;
            }
            return retval;
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
            return false;
        }            
    }
    
    public boolean add() {
        this.setErrMessage("");
        WebController ctr=new WebController();
        this.setPartyId("");
        this.setFirstName("");
        this.setMiddleName("");
        this.setLastName("");
        this.setGender("M");
        this.setPhoneNumber("");
        this.setAddress1("");
        this.setAddress2("");
        this.setCity("");
        this.setState("");
        this.setCountry("US");
        this.setZip("");
        this.setCardType("Visa");
        this.setCardNumber("");
        this.setCardExpMonth("01");
        this.setCardExpYear("2007");
        this.setCardId("");
        this.setCardHolderName("");
        this.setBillingAddress("");
        this.setBillingCity("");
        this.setBillingState("");
        this.setBillingZip("");
        this.setAccountId(""); 
        this.setPassword("");
        this.setAccountNumber(0);
        this.setNotation("Universal");
        this.setExtended(0);
        newrecord=true;
        return true;
    }
    
    public boolean delete(String id) {
        this.setErrMessage("");
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        try {
            Connection c=db.getConnection();
            this.setErrMessage("");
            SQLHelper h=new SQLHelper();
            String sql="DELETE FROM account WHERE PARTY_ID = \'"+id+"\'";
            Statement s=c.createStatement();
            int count=s.executeUpdate(sql);            
            return (count>0);
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
            return false;
        }            
    }
    
    public boolean save() {
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        try {
            Connection c=db.getConnection();
            this.setErrMessage("");
            SQLHelper h=new SQLHelper();
            String sql;
            Statement s;
            ResultSet r;
            boolean ok;
            if (this.newrecord) {
                sql="SELECT * FROM account";
                s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                r=s.executeQuery(sql);
                r.moveToInsertRow();
                ok=true;
            } else {
                sql="SELECT * FROM account WHERE PARTY_ID=\""+this.getPartyId()+"\"";
                s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                r=s.executeQuery(sql);
                ok=r.next();
            }
            if (ok) {
                r.updateString("PARTY_ID", this.getPartyId());
                r.updateString("FIRST_NAME", this.getFirstName());
                r.updateString("MIDDLE_NAME", this.getMiddleName());
                r.updateString("LAST_NAME", this.getLastName());
                r.updateString("GENDER", this.getGender());
                r.updateString("PHONE_NUMBER",this.getPhoneNumber());
                r.updateString("ADDRESS1",this.getAddress1());
                r.updateString("ADDRESS2",this.getAddress2());
                r.updateString("CITY",this.getCity());
                r.updateString("STATE",this.getState());
                r.updateString("COUNTRY", this.getCountry());
                r.updateString("ZIP",this.getZip());
                r.updateString("CARD_TYPE",this.getCardType());
                r.updateString("CARD_NUMBER",this.getCardNumber());
                r.updateString("CARD_EXP_MONTH",this.getCardExpMonth());
                r.updateString("CARD_EXP_YEAR",this.getCardExpYear());
                r.updateString("CARD_ID",this.getCardId());
                r.updateString("CARD_HOLDER_NAME",this.getCardHolderName());
                r.updateString("BILLING_ADDRESS",this.getBillingAddress());
                r.updateString("BILLING_CITY",this.getBillingCity());
                r.updateString("BILLING_STATE",this.getBillingState());
                r.updateString("BILLING_ZIP",this.getBillingZip());
                r.updateString("ACCOUNT_ID", this.getAccountId());
                r.updateString("PASSWORD", this.getPassword());
                r.updateInt("ACCOUNT_NUMBER",this.getAccountNumber());
                r.updateString("NOTATION", this.getNotation());
                r.updateInt("EXTENDED",this.getExtended());
                if (this.newrecord) {
                    r.insertRow();
                } else {
                    r.updateRow();
                }
                return true;
            } else {
                return false; //not ok
            }
            
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
            return false;
        }
    }    
}
