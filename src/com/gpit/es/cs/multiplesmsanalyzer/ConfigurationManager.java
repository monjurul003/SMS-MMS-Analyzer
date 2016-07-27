/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gpit.es.cs.multiplesmsanalyzer;

import java.io.File;
import org.apache.log4j.Logger;
import org.apache.commons.digester.Digester;

/**
 *
 * @author monjurul.k
 */
public class ConfigurationManager {
    private DriverManagerDataSource ds, ds1;
    String sqlQuery, sqlQuery1,inputFile,outputFile,outputFileName,outputDirectory;
    private boolean isLocal;
    static Logger logger = Logger.getLogger(ConfigurationManager.class);
    
    
    /**
     * @return Returns the sqlQuery.
    */
    public String getSqlQuery() {
            return sqlQuery;
    }
    /**
     * @param sqlQuery The sqlQuery to set.
    */
    public void setSqlQuery(String sqlQuery) {
            this.sqlQuery = sqlQuery;
    }
    /**
     * @return Returns the inputFile.
    */
    public String getInputFile() {
            return inputFile;
    }
    /**
     * @param outputFile The outputFile to set.
    */
    public void setInputFile(String inputFile) {
            this.inputFile = inputFile;
    }
    /**
     * @return Returns the outputFile.
    */
    public String getOutputFile() {
            return outputFile;
    }
    /**
     * @param outputFile The outputFile to set.
    */
    public void setOutputFile(String outputFile) {
            this.outputFile = outputFile;
    }
    
    /**
     * @return Returns the outputFileName.
    */
    public String getOutputFileName() {
            return outputFileName;
    }
    /**
     * @param outputFileName The outputFileName to set.
    */
    public void setOutputFileName(String outputFileName) {
            this.outputFileName = outputFileName;
    }
    
    /**
     * @return Returns the outputDirectory.
    */
    public String getOutputDirectory() {
            return outputDirectory;
    }
    /**
     * @param outputDirectory The outputDirectory to set.
    */
    public void setOutputDirectory(String outputDirectory) {
            this.outputDirectory = outputDirectory;
    }
    
    /**
     * @return Returns the isLocal.
    */
    public boolean getIsLocal() {
            return isLocal;
    }
    /**
     * @param isLocal The isLocal to set.
    */
    public void setIsLocal(boolean isLocal) {
            this.isLocal = isLocal;
    }
    
    /**
     * @return Returns the DataSource.
    */
    public DriverManagerDataSource getDataSource() {
            return ds;
    }
    /**
     * @param DS, The DataSource to set.
    */
    public void setDataSource(DriverManagerDataSource ds) {
            this.ds = ds;
    }

    public String getSqlQuery1() {
        return sqlQuery1;
    }

    public void setSqlQuery1(String sqlQuery1) {
        this.sqlQuery1 = sqlQuery1;
    }

  
    public DriverManagerDataSource getDataSource1() {
        return ds1;
    }

    public void setDataSource1(DriverManagerDataSource ds1) {
        this.ds1 = ds1;
    }
    
    
    
    public ConfigurationManager getConfiguration(File confFile) throws Exception {
        Digester d = new Digester();
        ConfigurationManager cm = null;
        d.setValidating(false);

        logger.info("inside getConfiguration");
        d.addObjectCreate("Configuration", ConfigurationManager.class);
        d.addObjectCreate("Configuration/DataSource", DriverManagerDataSource.class);
        d.addObjectCreate("Configuration/DataSource1", DriverManagerDataSource.class);
                    
        d.addBeanPropertySetter("Configuration/DataSource/Driver", "driver");
        d.addBeanPropertySetter("Configuration/DataSource/Url", "url");
        d.addBeanPropertySetter("Configuration/DataSource/User", "username");
        d.addBeanPropertySetter("Configuration/DataSource/Password", "password");
        d.addSetNext("Configuration/DataSource", "setDataSource");
        
         d.addBeanPropertySetter("Configuration/DataSource1/Driver", "driver");
        d.addBeanPropertySetter("Configuration/DataSource1/Url", "url");
        d.addBeanPropertySetter("Configuration/DataSource1/User", "username");
        d.addBeanPropertySetter("Configuration/DataSource1/Password", "password");
        d.addSetNext("Configuration/DataSource1", "setDataSource1");
        
        d.addBeanPropertySetter("Configuration/SqlQuery", "sqlQuery");
        d.addBeanPropertySetter("Configuration/SqlQuery1", "sqlQuery1");
        d.addBeanPropertySetter("Configuration/OutputFileName", "outputFileName");
        d.addBeanPropertySetter("Configuration/OutputDirectory", "outputDirectory");
        d.addBeanPropertySetter("Configuration/IsLocal", "isLocal");
        

        cm = (ConfigurationManager) d.parse(confFile);

        return cm;
    }
}
