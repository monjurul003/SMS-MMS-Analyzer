/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gpit.es.cs.multiplesmsanalyzer;

import it.sauronsoftware.cron4j.Scheduler;
import java.io.File;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author monjurul.k
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        //Get application working directory for getting the configuiration of logging module 
        String userDir = System.getProperty("user.dir");

        //configure the DOMConfigurator from conf file. Please modify the appender1 of that file according to README.txt
        DOMConfigurator.configure(userDir + File.separator + "conf" + File.separator + "multipleSms-log4jConfig.xml");
       
        logger.info(" Application Started " + new Date(System.currentTimeMillis()));
        
        //Get the scheduling file from the app directory
        File scheduleFile = new File("schedule");
        // Creates the scheduler according to the schedule file. Details could be found in README.txt 
        Scheduler scheduler = new Scheduler();
        scheduler.scheduleFile(scheduleFile);
        // Starts the scheduler. So executeTask will be called as per schedule time and it takes the location of input file.
        
        scheduler.start();
    }
//This method will be called with the path of input.xml file provided in schedule file. Scheduler will call this method
    //based on the schedule time provided in the scchedule file
    public static void executeTask(String[] args) {
        // Prepares the task.
        ScheduleTask sTask = new ScheduleTask();
        try {
            sTask.execute(args[0]);
        } catch (Exception ex) {
            logger.fatal(ex.getMessage());
            ex.printStackTrace();
        }
    }

}
