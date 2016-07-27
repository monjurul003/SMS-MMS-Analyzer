/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gpit.es.cs.multiplesmsanalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author monjurul.k
 */
public class WriteReportData {

    public static Logger logger = Logger.getLogger(WriteReportData.class);
    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private Connection conn1;
    private PreparedStatement stm1;
    private ResultSet rs1;
    private int numColumns;
    private String sqlQuery;
    private String sqlQuery1;
    private String inputFile;
    public File outputFile;
    private String outputDirectory;
    private String outputFileName;
    private boolean isLocal;
    private BufferedWriter buffWriter = null;
    public File tmpOutputFile;

    public WriteReportData() {
    }

    public WriteReportData(ConfigurationManager cm) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date d = new Date();
        cm.setOutputFileName(cm.getOutputFileName() + "_" + sdf.format(d) + ".csv");
        this.conn = cm.getDataSource().getConnection();

        this.sqlQuery = cm.getSqlQuery();

        this.conn1 = cm.getDataSource1().getConnection();

        this.sqlQuery1 = cm.getSqlQuery1();
        this.inputFile = cm.getInputFile();
        this.outputFileName = cm.getOutputFileName();
        this.outputDirectory = cm.getOutputDirectory();
        this.outputFile = new File(this.outputDirectory + this.outputFileName);
        this.tmpOutputFile = new File(this.outputFileName);
        this.isLocal = cm.getIsLocal();
        System.out.println("WriteReportData:: output file name--" + this.outputFileName);
        this.stm = this.conn.prepareStatement(this.sqlQuery);
        this.rs = this.stm.executeQuery();
        // Get result set meta data 
        this.rsmd = rs.getMetaData();
        this.numColumns = rsmd.getColumnCount();
        System.out.println("Initialised");
    }

    public boolean getReport() {
        logger.info(" Report generatation Started " + new Date(System.currentTimeMillis()));

        System.out.println("Report generation started");
        try {
            try {
                this.buffWriter = new BufferedWriter(new FileWriter(this.outputFile));
                String str = "";
                for (int i = 1; i < this.numColumns + 1; i++) {
                    if (i == 1) {
//                        this.buffWriter.write(rsmd.getColumnName(i));
                        str += rsmd.getColumnName(i);
                    } else {
//                        this.buffWriter.write("," + rsmd.getColumnName(i));
                        str += "," + rsmd.getColumnName(i);
                    }
                }
                str += "," + "DatabaseCount";
                this.buffWriter.write(str);
                this.buffWriter.newLine();
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL, ex.getMessage());
                ex.printStackTrace();
            }
            while (this.rs.next()) {
                String tempQuery = this.sqlQuery1;
                String str = "";
                tempQuery = tempQuery.replace("?", "%" + rs.getString("reciever_msisdn"));
                tempQuery = tempQuery.replace("#", "%" + rs.getString("sender_msisdn"));
                System.out.println(tempQuery);
                logger.info(tempQuery);
                this.stm1 = this.conn1.prepareStatement(tempQuery);
                this.rs1 = this.stm1.executeQuery();

                for (int index = 1; index < this.numColumns + 1; index++) {
                    if (index == 1) {
//                            this.buffWriter.write(this.rs.getString(index));
                        str += this.rs.getString(index);
                        str =str.replace("00:00:00.0", "");
                        str = str.trim();
                    } else {
                        str += "," + this.rs.getString(index);
                    }
                }
                while (this.rs1.next()) {
                    str += "," + this.rs1.getString(1);
                }
                System.out.println(str);
                try {
                    this.buffWriter.write(str);
                    this.buffWriter.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL, ex.getMessage());
                    ex.printStackTrace();
                }
                this.rs1.close();
                logger.info(" Close recordset object");
                this.stm1.cancel();
                logger.info(" Close statement object");
            }
            try {
                this.buffWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL, ex.getMessage());
                ex.printStackTrace();
            }
            this.rs.close();
            logger.info(" Close recordset object");
            this.stm.cancel();
            logger.info(" Close statement object");
            this.conn.close();
            logger.info(" Close connection object");
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL, ex.getMessage());
            ex.printStackTrace();
        }
        logger.info(" Report Genaration completed");
        return true;
    }
}
