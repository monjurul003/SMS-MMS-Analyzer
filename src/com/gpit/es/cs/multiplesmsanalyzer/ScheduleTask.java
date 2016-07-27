/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gpit.es.cs.multiplesmsanalyzer;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
/**
 *
 * @author monjurul.k
 */

public class ScheduleTask {
    
    private ConfigurationManager cm;
//    private Properties properties = new Properties();  //I used a mail server for sending mail but ommit it later
    static Logger logger = Logger.getLogger(ScheduleTask.class);
   

    public void execute(String inputFile) throws Exception {
        //putting the log with time
        logger.info(" Scheduler Started "+new Date(System.currentTimeMillis()));
        try {
            logger.info(" Get input configuration");
            this.cm = new ConfigurationManager().getConfiguration(new File(inputFile));
            this.cm.setInputFile(inputFile);
            logger.info(" Prepare SQL query and write data into output file");
            WriteReportData wrData = new WriteReportData(this.cm);
            boolean status = false;
                       
            if (this.cm.getIsLocal()){
                status = wrData.getReport();
            }
          
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName().toString()).log(Level.FATAL,ex.getMessage());
            ex.printStackTrace();
        } finally {
            System.gc();
        }
        logger.info(" Scheduler completed");
    }
}
