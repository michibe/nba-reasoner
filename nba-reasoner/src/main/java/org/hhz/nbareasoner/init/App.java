package org.hhz.nbareasoner.init;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.hhz.nbareasoner.cbr.impl.CbrManager;
import org.hhz.nbareasoner.cbr.model.phase.retain.CbrRetainAgent;
import org.hhz.nbareasoner.util.cache.UrlResponseCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;



/**
 * Created by mbehr on 04.08.2015.
 */
@SpringBootApplication
@ComponentScan("org.hhz")
public class App {

private static Logger logger = LoggerFactory.getLogger(App.class.getName());

    public  static ConfigurableApplicationContext ctx;

    private static CbrManager cbrManager;

    public static void main(String[] args) {

        Long start = System.nanoTime();

        SpringApplication app = new SpringApplication(App.class);

        Map<String,Object> defaultProperties = new HashMap<>();
        defaultProperties.put("server.contextPath", "/nbareasoner");
        defaultProperties.put("cbmlFilePath", "file:///${user.dir}/cbml.xml");
        defaultProperties.put("nbaReasonerConfigFilePath", "file:///${user.dir}/nba-reasoner-config.xml");

        app.setDefaultProperties(defaultProperties);





        ctx = app.run(args);




        //Initalize CBR Manager
        initCbrManager();


        //start the retain phase to get the cases form the sources
        try {
            cbrManager.retain();
        } catch (CbrRetainAgent.CbrRetainException e) {
            throw new RuntimeException("Could not retain cases when startup app due to: " , e);
        }


        logger.info("NBA-Reasoner started in " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-start) +" seconds");

    }


    private static void initCbrManager()
    {

        String cbmlFilePath =  ctx.getEnvironment().getProperty("cbmlFilePath").replace("\\","/");

        URI cbmlFilePathUri = URI.create(cbmlFilePath);


        String nbaReasonerConfigFilePath = ctx.getEnvironment().getProperty("nbaReasonerConfigFilePath").replace("\\", "/");


        URI nbaReasonerConfigFilePath1Uri =URI.create(nbaReasonerConfigFilePath);


        cbrManager = CbrManager.createCbrManager(cbmlFilePathUri,nbaReasonerConfigFilePath1Uri);


    }

    public static CbrManager getCbrManager() {
        return cbrManager;
    }

    public static void reinitialize()
    {

        initCbrManager();
    }


    public static String replaceExpressionsWithProperty(String text)
    {
        while(text.contains("${") && text.contains("}"))
        {

            String expression = text.substring(text.indexOf("${") + 2, text.indexOf("}"));


            //wegen testfall ohne appserver checken wenn ctx null dann aus system properteis wert holen
            String replaceValue;
            if(ctx!=null)
            {
                 replaceValue = ctx.getEnvironment().getProperty(expression);
            }
            else
            {
                 replaceValue = System.getProperty(expression);
            }






            if (replaceValue==null)
         {
             replaceValue = "&&DK"+expression+"&&K";
         }

            text = text.replace("${"+expression+"}",replaceValue);


        }

        //Die Ausdrücke für die keine Property gefunden wurde sollten beibehalten werden

        return text.replace("&&DK","${").replace("&&K","}");
    }












}
