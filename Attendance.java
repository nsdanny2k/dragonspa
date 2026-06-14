/*
 * Attendance.java
 * 
 * Created on Oct 20, 2007, 3:05:11 PM
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
public class Attendance extends Vector {

    public Attendance() {
    }
    private int sets;

    public int getSets() {
        return sets;
    }
    
    public int fetch(Date transDate, String account, int from, int count) {
        WebController ctr=new WebController();
        DBConnectionManager db=new DBConnectionManager();
        model.AttendanceItem detail;
        try {
            int i=0;
            int j=0;
            Connection c=db.getConnection();
            String sql="SELECT * FROM attendance WHERE TRANS_DATE=\""+ctr.convertDateToSQLString(transDate)+"\" AND ACCOUNT_ID = \""+account+"\" ORDER BY ID";
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
                        detail=new model.AttendanceItem();
                        detail.fetch(r.getInt("ID"));
                        this.add(detail);
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
