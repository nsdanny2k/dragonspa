/*
 * Appointments.java
 * 
 * Created on Sep 14, 2007, 5:17:50 PM
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
import java.util.Vector;

/**
 *
 * @author nsdan2k
 */
public class Appointments extends Vector {

    public Appointments() {
    }
    
    private int sets;

    public int getSets() {
        return sets;
    }   
    
    public int fetch(Date transDate, String account, int from, int count) {
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        model.Appointment appointment;
        try {
            int i=0;
            int j=0;
            Connection c=db.getConnection();
            String sql="SELECT * FROM appointment WHERE TRANS_DATE=\'"+ctr.convertDateToSQLString(transDate)+"\' AND ACCOUNT_ID=\'"+account+"\' ORDER BY ID";
            Statement s=c.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE,java.sql.ResultSet.CONCUR_UPDATABLE);
            ResultSet r=s.executeQuery(sql);
            SQLHelper h=new SQLHelper();        
            while (r.next()) {

                i=i+1;
                if ((i<(from-1)*(count)+1))  {
                    //skip
                } else {
                    j=j+1;
                    if (j<=count) {
                        appointment=new model.Appointment();
                        appointment.fetch(r.getInt("ID"), account);
                        this.add(appointment);
                    }
                }
                
            }
            this.sets=i/count;
            if((i % count)>0) {
                this.sets=this.sets+1;
            }    
            return i;
        } catch (Exception e) {
            return 0;        
        }
    }
}
