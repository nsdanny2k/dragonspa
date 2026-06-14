/*
 * Appointment.java
 * 
 * Created on Sep 14, 2007, 5:17:39 PM
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
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author nsdan2k
 */
public class Appointment {

    public Appointment() {
        this.setErrMessage("");
    }

    private int id;
    private Date transDate;
    private Time transTime;
    private int duration;
    private String patientId;
    private String accountId;
    private String remarks;
    private String errMessage;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Time getTransTime() {
        return transTime;
    }

    public void setTransTime(Time transTime) {
        this.transTime = transTime;
    }
    
    public boolean fetch(int id, String account) {
        boolean retval=false;
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        try {
            Connection c=db.getConnection();
            this.setErrMessage("");
            SQLHelper h=new SQLHelper();

            String sql="SELECT * FROM appointment WHERE ID="+id+" AND ACCOUNT_ID=\""+account+"\"";
            Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
            ResultSet r=s.executeQuery(sql);
            while (r.next()) {
                this.setId(id);
                this.setTransDate(r.getDate("TRANS_DATE"));
                this.setTransTime(r.getTime("TRANS_TIME"));
                this.setDuration(r.getInt("DURATION"));
                this.setPatientId(r.getString("PATIENT_ID"));
                this.setRemarks(r.getString("REMARKS"));
                this.setAccountId(r.getString("ACCOUNT_ID"));                
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
        this.setId(0);
        this.setTransDate(null);
        this.setTransTime(null);
        this.setDuration(30);
        this.setPatientId("");
        this.setRemarks("");
        this.setAccountId(""); 
        return true;
    }
    
    public boolean delete(int id) {
        boolean retval=false;
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        try {
            Connection c=db.getConnection();
            this.setErrMessage("");
            SQLHelper h=new SQLHelper();

            String sql="DELETE FROM appointment WHERE ID="+id;
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
            if (this.getId()==0) {
                sql="SELECT * FROM appointment";
                s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                r=s.executeQuery(sql);
                r.moveToInsertRow();
                ok=true;
            } else {
                sql="SELECT * FROM appointment WHERE ID="+this.getId()+"";
                s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                r=s.executeQuery(sql);
                ok=r.next();
            }
            if (ok) {
                r.updateDate("TRANS_DATE",new java.sql.Date(this.getTransDate().getTime()));
                r.updateTime("TRANS_TIME",this.getTransTime());
                r.updateInt("DURATION",this.getDuration());
                r.updateString("PATIENT_ID",this.getPatientId());
                r.updateString("REMARKS",this.getRemarks());
                r.updateString("ACCOUNT_ID", this.getAccountId());
                if (this.getId()==0) {
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
