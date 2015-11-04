
package org.hhz.nbareasoner.cbml.impl.advanced.loading.loader;


import org.hhz.nbareasoner.cbml.impl.advanced.CObligatedParameterImpl;
import org.hhz.nbareasoner.cbml.impl.advanced.loading.CAttributeLoaderImplAbs;
import org.hhz.nbareasoner.cbml.impl.advanced.loading.CLoadedCaseAttributeImpl;
import org.hhz.nbareasoner.cbml.impl.advanced.loading.CLoadedCaseBaseAttributeImpl;
import org.hhz.nbareasoner.cbml.impl.advanced.loading.CLoadedIncidentAttributeImpl;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCase;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.loading.CLoadingSourceDefinition;
import org.hhz.nbareasoner.cbml.model.advanced.CLoadingSourceException;
import org.hhz.nbareasoner.cbml.model.advanced.CNotApplicableException;
import org.hhz.nbareasoner.cbml.model.advanced.CObligatedParameter;
import org.hhz.nbareasoner.cbml.model.advanced.loading.*;
import org.hhz.nbareasoner.util.extern.UriHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.sql.*;
import java.util.*;


/**
 * Created by mbehr on 27.02.2015.
 */

public class AttributeLoaderCsvJdbc extends CAttributeLoaderImplAbs {

    private final static Logger logger = LoggerFactory.getLogger(AttributeLoaderCsvJdbc.class);

    public static Map<String,Connection> connectionPool = new HashMap<>();

    private Connection conn;

    public AttributeLoaderCsvJdbc(CLoadingSourceDefinition eLoadingSource) throws CLoadingSourceException {
        super(eLoadingSource);




        try {
           // Class.forName("org.relique.jdbc.csv.CsvDriver");
            //this.getClass().forName("org.relique.jdbc.csv.CsvDriver");

           // Thread.currentThread().getContextClassLoader().loadClass("org.relique.jdbc.csv.CsvDriver");

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Class.forName("org.relique.jdbc.csv.CsvDriver", true, cl);
        } catch (ClassNotFoundException e) {
           throw new CLoadingSourceException("Cannot load LoadingSource: org.relique.jdbc.csv.CsvDriver",e);
        }

        //Die replaceKeys aus dem sql select statement extrahieren... dadurch wird im späteren verlauf zeit gespart

     //   replaceLogKeys = StringHelper.splitStringBetween(sqlSelectStatement,"{log.","}");
     //   replaceTraceKeys = StringHelper.splitStringBetween(sqlSelectStatement,"{trace.","}");
     //   replaceEventKeys = StringHelper.splitStringBetween(sqlSelectStatement,"{event.","}");

    }


    public CLoadedCaseBaseAttribute loadCaseBaseAttributeValue() throws CLoadingSourceException {
        String sqlSelect = cLoadingSourceDefinition.getCLoadingSourceParameters().get("sqlSelect").getValue();
        logger.debug("Run SqlSelect '{}'",sqlSelect);
        try
        {
            Statement stmt = this.conn.createStatement();
            ResultSet results = stmt.executeQuery(sqlSelect);
            results.next();
            return new CLoadedCaseBaseAttributeImpl(results.getString("value"));
        }
        catch (Exception e)
        {
            throw new CLoadingSourceException("Could not load CaseBaseAttributeValue with sqlSelect-statement '" + sqlSelect +"' due to",e);
        }

    }


    // das select muss eine tabelle zurückgeben in der jede zeile eine caseId enthält und den entsprechenden wert als value
    public List<CLoadedCaseAttribute> loadCaseAttributeValues(List<? extends CCCase> alreadyLoaded) throws CLoadingSourceException {
        String sqlSelect = cLoadingSourceDefinition.getCLoadingSourceParameters().get("sqlSelect").getValue();
        logger.debug("Run SqlSelect '{}'",sqlSelect);
        try
        {
            Statement stmt = this.conn.createStatement();
            ResultSet results = stmt.executeQuery(sqlSelect);



            List<CLoadedCaseAttribute> resultList = new ArrayList<>();
            while(results.next())
            {
                resultList.add(new CLoadedCaseAttributeImpl(results.getString("caseId"),results.getString("value")));
            }

            return resultList;
        }
        catch (Exception e)
        {
            throw new CLoadingSourceException("Could not load CaseAttribute with sqlSelect-statement '" + sqlSelect +"' due to",e);
        }

    }




    public List<CLoadedIncidentAttribute> loadActivityAttributeValues(List<? extends CCCase> alreadyLoaded) throws CLoadingSourceException {

        String sqlSelect = cLoadingSourceDefinition.getCLoadingSourceParameters().get("sqlSelect").getValue();
        logger.debug("Run SqlSelect '{}'", sqlSelect);
        try
        {
            Statement stmt = this.conn.createStatement();
            ResultSet results = stmt.executeQuery(sqlSelect);

            List<CLoadedIncidentAttribute> resultList = new ArrayList<>();
            while(results.next())
            {
                resultList.add(new CLoadedIncidentAttributeImpl(results.getString("caseId"),results.getString("incidentId"),results.getString("value")));
            }

            return resultList;
        }
        catch (Exception e)
        {
            throw new CLoadingSourceException("Could not load ActivityAttribute with sqlSelect-statement '" + sqlSelect +"' due to",e);
        }


    }






    public List<CLoadedIncidentAttribute> loadEventAttributeValues(List<? extends CCCase> alreadyLoaded) throws CLoadingSourceException {
        String sqlSelect = cLoadingSourceDefinition.getCLoadingSourceParameters().get("sqlSelect").getValue();
        logger.debug("Run SqlSelect '{}'", sqlSelect);
        try
        {
            Statement stmt = this.conn.createStatement();
            ResultSet results = stmt.executeQuery(sqlSelect);

            List<CLoadedIncidentAttribute> resultList = new ArrayList<>();
            while(results.next())
            {
                resultList.add(new CLoadedIncidentAttributeImpl(results.getString("caseId"),results.getString("incidentId"),results.getString("value")));
            }

            return resultList;
        }
        catch (Exception e)
        {
            throw new CLoadingSourceException("Could not load EventAttribute with sqlSelect-statement '" + sqlSelect +"' due to",e);
        }

    }


    // das select muss eine tabelle zurückgeben in der jede zeile eine caseId enthält und den entsprechenden wert als value
    @Override
    public CLoadedCaseAttribute loadCaseAttributeValue(String openedCaseId) throws CLoadingSourceException {

        String sqlSelect = cLoadingSourceDefinition.getCLoadingSourceParameters().get("sqlSelect").getValue();
        sqlSelect = sqlSelect.replace("${openedCaseId}",openedCaseId);
        logger.debug("Run SqlSelect '{}'", sqlSelect);
        try
        {
            Statement stmt = this.conn.createStatement();
            ResultSet results = stmt.executeQuery(sqlSelect);


            results.next();

                return new CLoadedCaseAttributeImpl(results.getString("caseId"),results.getString("value"));



        }
        catch (Exception e)
        {
            throw new CLoadingSourceException("Could not load CaseAttribute with sqlSelect-statement '" + sqlSelect +"' due to",e);
        }

    }




    @Override
    public List<CLoadedIncidentAttribute> loadActivityAttributeValues(String openedCaseId) throws CLoadingSourceException {
       logger.debug("loadActivityAttributeValues started");
        String sqlSelect = cLoadingSourceDefinition.getCLoadingSourceParameters().get("sqlSelect").getValue();
        sqlSelect = sqlSelect.replace("${openedCaseId}",openedCaseId);
        logger.debug("Run SqlSelect '{}'", sqlSelect);
        try
        {
            Statement stmt = this.conn.createStatement();

            stmt.setFetchSize(100);


            logger.debug("Statement created");
            ResultSet results = stmt.executeQuery(sqlSelect);
            results.setFetchSize(100);


            //TODO    results.

                     logger.debug("Statement executed " + results.getFetchSize());
            List<CLoadedIncidentAttribute> resultList = new ArrayList<>();
            //TODO   hier bottleneck beim eintritt
            while(results.next())
            {
                CLoadedIncidentAttributeImpl cLoadedIncidentAttribute = new CLoadedIncidentAttributeImpl(results.getString("caseId"),results.getString("incidentId"),results.getString("value"));
                logger.debug("result next " + cLoadedIncidentAttribute);
                resultList.add(cLoadedIncidentAttribute);



            }

          //TODO  und hier bottleneck beim austritt

            //hier dieser schritt braucht zu lange
            //stmt.close();
           // results.close();

            logger.debug("loadActivityAttributeValues finished");
            return resultList;
        }
        catch (Exception e)
        {
            throw new CLoadingSourceException("Could not load ActivityAttribute with sqlSelect-statement '" + sqlSelect +"' due to",e);
        }



    }

    @Override
    public List<CLoadedIncidentAttribute> loadEventAttributeValues(String openedCaseId) throws CNotApplicableException {
        String sqlSelect = cLoadingSourceDefinition.getCLoadingSourceParameters().get("sqlSelect").getValue();
        sqlSelect = sqlSelect.replace("${openedCaseId}",openedCaseId);
        logger.debug("Run SqlSelect '{}'", sqlSelect);
        try
        {
            Statement stmt = this.conn.createStatement();
            ResultSet results = stmt.executeQuery(sqlSelect);


            List<CLoadedIncidentAttribute> resultList = new ArrayList<>();
            while(results.next())
            {
                resultList.add(new CLoadedIncidentAttributeImpl(results.getString("caseId"),results.getString("incidentId"),results.getString("value")));
            }

            return resultList;
        }
        catch (Exception e)
        {
            throw new CNotApplicableException("Could not load EventAttribute with sqlSelect-statement '" + sqlSelect +"' due to",e);
        }
    }

    @Override
    public void beforeLoading() throws CLoadingSourceException {
        logger.info("Open Loading Source started");
//iwie connection pooling erstellen dass die connection nicht ständig neu aufgebaut werden muss
       Properties props = new Properties();


        props.put("separator", cLoadingSourceDefinition.getCLoadingSourceParameters().get("separator").getValue());

        String conStr = "jdbc:relique:csv:" + UriHelper.getPathWithoutFileNameOfFileUri(URI.create(cLoadingSourceDefinition.getCLoadingSourceParameters().get("src").getValue().replace("\\","/")));

        String key = conStr + props.toString();

        // Load the driver.

        //Try to get connection from pool
        this.conn = connectionPool.get(key);

        if(conn ==null)
        {
            try {
                this.conn = DriverManager.getConnection(conStr, props);
                connectionPool.put(key,this.conn);
            } catch (SQLException e) {
                throw new CLoadingSourceException("Cannot open loading source " + this.toString(),e );
            }
        }
        logger.info("Open Loading Source finished");
    }

    @Override
    public void afterLoading() {

    }

    @Override
    public void afterLastInstanceRunLoading()
    {
        this.closePool();
    }

    @Override
    public void beforeFirstInstanceRunsLoading()
    {

    }


    private void closePool()
    {

        for(Connection conn : connectionPool.values())
        {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        connectionPool.clear();
    }

    @Override
    public String getDescription() {
        return "this Loader ... oder die beschreibung aus java doc entnehmen aus einem beschreibungstag";
    }



    @Override
    public List<CObligatedParameter> getObligatedParameters() {

        List<CObligatedParameter> cObligatedParameters = new ArrayList<>();
        cObligatedParameters.add(new CObligatedParameterImpl("separator"));
        cObligatedParameters.add(new CObligatedParameterImpl("src"));
        cObligatedParameters.add(new CObligatedParameterImpl("sqlSelect"));

        return cObligatedParameters;
    }




}

