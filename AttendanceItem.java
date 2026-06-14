/*
 * AttendanceItem.java
 * 
 * Created on Oct 20, 2007, 3:05:29 PM
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
import java.util.Date;

/**
 *
 * @author nsdan2k
 */
public class AttendanceItem {

    public AttendanceItem() {
         this.setErrMessage("");
    }
    
    private int doctorId;
    private String doctor;
    private double numDays;
    private Date transDate;
    private int id;
    private String errMessage;
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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

    public double getNumDays() {
        return numDays;
    }

    public void setNumDays(double numDays) {
        this.numDays = numDays;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }
    
    public boolean fetch(int id) {
        boolean retval=false;
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        try {
            Connection c=db.getConnection();
            this.setErrMessage("");
            SQLHelper h=new SQLHelper();

            String sql="SELECT * FROM attendance WHERE ID="+id;
            Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
            ResultSet r=s.executeQuery(sql);
            while (r.next()) {
                this.setId(id);
                this.setDoctorId(r.getInt("DOCTOR_ID"));
                this.setDoctor(r.getString("DOCTOR"));
                this.setNumDays(r.getDouble("NUM_DAYS"));
                this.setTransDate(r.getDate("TRANS_DATE"));
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
        this.setDoctorId(0);
        this.setDoctor("");
        this.setNumDays(1);
        this.setTransDate(ctr.BLANKDATE());
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

            String sql="DELETE FROM attendance WHERE ID="+id;
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
                sql="SELECT * FROM attendance";
                s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                r=s.executeQuery(sql);
                r.moveToInsertRow();
                ok=true;
            } else {
                sql="SELECT * FROM attendance WHERE ID="+this.getId()+"";
                s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
                r=s.executeQuery(sql);
                ok=r.next();
            }
            if (ok) {
                r.updateInt("DOCTOR_ID",this.getDoctorId());
                r.updateString("DOCTOR",this.getDoctor());
                r.updateDouble("NUM_DAYS",this.getNumDays());
                r.updateDate("TRANS_DATE",new java.sql.Date(this.getTransDate().getTime()));
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
