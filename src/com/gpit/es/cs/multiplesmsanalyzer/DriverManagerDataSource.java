/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gpit.es.cs.multiplesmsanalyzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author monjurul.k
 */
public class DriverManagerDataSource {
    static Logger logger=Logger.getLogger(DriverManagerDataSource.class);
    private String driver;
    private String url;
    private String username;
    private String password;
    private DataSource dataSource;

    public String getDriver(){
            return this.driver;
    }
    public void setDriver(String driver){
            this.driver = driver;
    }
    public String getUrl(){
            return this.url;
    }
    public void setUrl(String url){
            this.url = url;
    }
    public String getUsername(){
            return this.username;
    }
    public void setUsername(String username){
            this.username = username;
    }
    public String getPassword(){
            return this.password;
    }
    public void setPassword(String password){
            this.password = password;
    }
    public Connection getConnection(){
        Logger.getLogger(this.getClass().getName().toString()).log(Level.INFO," Database connection Started "+new Date(System.currentTimeMillis()));
        if (this.driver != null) {
            // If driver is specified, then load it and let it register itself with DriverManager.
            try {
                Class.forName(this.driver);
              
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL,ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            // Else assume URL as DataSource URL and lookup it in the JNDI.
            try {
                this.dataSource = (DataSource) new InitialContext().lookup(this.url);
            } catch (NamingException ex) {
                Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL,ex.getMessage());
                ex.printStackTrace();
            }
        }

        if (this.dataSource != null) {
            if (this.username != null) {
                try {
                    return this.dataSource.getConnection(this.username, this.password);
                } catch (SQLException ex) {
                    Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL,ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                try {
                    return this.dataSource.getConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL,ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } else {
            try {
                return DriverManager.getConnection(this.url, this.username, this.password);
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL,ex.getMessage());
                ex.printStackTrace();
            }
        }
        Logger.getLogger(this.getClass().getName().toString()).log(Level.INFO," Database connection completed");
        return null;
    }
}
