
package org.hhz.nbareasoner.service;




import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseIncident;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarityResult;

import org.hhz.nbareasoner.cbr.impl.CbrRuntimeException;
import org.hhz.nbareasoner.cbr.model.phase.retain.CbrRetainAgent;
import org.hhz.nbareasoner.cbr.model.phase.retrieve.CbrRetrieveAgent;
import org.hhz.nbareasoner.cbr.model.phase.reuse.CbrReuseAgent;

import org.hhz.nbareasoner.cbr.impl.phase.reuse.nba.NbaResult;
import org.hhz.nbareasoner.cbml.model.base.data.CCase;
import org.hhz.nbareasoner.config.model.base.cbr.CbrConfig;
import org.hhz.nbareasoner.config.model.base.nba.NbaConfig;
import org.hhz.nbareasoner.init.App;
import org.hhz.nbareasoner.util.cache.UrlResponseCache;
import org.hhz.nbareasoner.util.cache.UrlResponseCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.*;
import javax.servlet.http.*;


import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.TimeUnit;



/**
 * Created by mbehr on 12.03.2015.
 */


@RestController
@RequestMapping("rest")
public class NbaReasonerRestService {

private final static Logger logger = LoggerFactory.getLogger(NbaReasonerRestService.class);

    private final static String getAllCasesPath = "casebase/cases";
    private final static String getCaseBasePath = "casebase";
    private final static String getCaseBaseRenewPath = "casebase/renew";
    private final static String getCasePath = "cases/{caseId}";
    private final static String getSimilarCasesForCasePath = "cases/{caseId}/similarcases";
    private final static String getNbaForCasePath = "cases/{caseId}/nba";
    private final static String getDefinitionPath = "casebase/definition";
    private final static String getNbaConfigPath = "nba/config";
    private final static String getCbrConfigPath = "cbr/config";
    private final static String getReInitPath = "reinit";


    @RequestMapping(method= RequestMethod.GET,value="test",produces = MediaType.APPLICATION_JSON_VALUE)
    public String test(HttpServletRequest httpServletRequest) throws CbrRuntimeException {


        return "{'test': 'successful'}";
    }

    @RequestMapping(method= RequestMethod.GET,value=getAllCasesPath,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CCase> getAllCasesAsJson() throws CbrRuntimeException {


        return App.getCbrManager().getCaseBaseCases();
    }


    @RequestMapping(method= RequestMethod.GET,value=getCaseBasePath,produces = MediaType.APPLICATION_XML_VALUE)
    public void getCaseBaseAsXML( HttpServletResponse response) throws IOException, JAXBException {


        App.getCbrManager().serializeCaseBaseToOutputStream(response.getOutputStream());
        response.getOutputStream().flush();


    }
    @RequestMapping(method= RequestMethod.GET,value=getCaseBasePath,produces = MediaType.APPLICATION_JSON_VALUE)
    public CCaseBase getCaseBaseAsJson( HttpServletResponse response) throws IOException, JAXBException {


       return App.getCbrManager().getCCaseBase();



    }




        //Gibt case zurück egal ob läuft oder nciht

    @RequestMapping(method= RequestMethod.GET,value=getCasePath,produces = MediaType.APPLICATION_JSON_VALUE)
    public CCase getCCase(@PathVariable("caseId") String caseId,@RequestParam(required = false) Boolean cache, HttpServletRequest httpServletRequest) {
     if(cache==null)
     {
         cache=true;
     }


        Long startTime = System.nanoTime();

        String queryString ="";

                if( httpServletRequest.getQueryString()!=null)
                {
                    queryString = queryString +"?" +httpServletRequest.getQueryString();
                }

        String url = httpServletRequest.getRequestURI().toString().replace(App.ctx.getEnvironment().getProperty("server.contextPath")+"/","") +queryString ;


        CCase cCase = null;

        if(cache==true)
        cCase = UrlResponseCacheManager.getInstance().<CCase>getCachedObject(url);

        if(cCase!=null)
        {
            logger.info("Retrieved case 'caseId' in milliseconds: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));

            return cCase;
        }
        else
        {
            cCase = App.getCbrManager().getCCase(caseId);

            UrlResponseCacheManager.getInstance().addResponseCache(new UrlResponseCache(System.nanoTime(),url,cCase));


            logger.info("Retrieved case 'caseId' in milliseconds: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));

            return cCase;
        }


    }



  //  parameter tabId requestId um dann die ähnlcihen fälle für diesen request zu holen ... speichern auf der servereite den gescuhten fall für die tabId ... wenn dann selber tab was will einfach daten holen... gheht schneller als den aktiven fall jedesmal neut zu laden wenn er benötigt wird.
   //Wenn casecacheId vorhanden dann wird erst einmal gecheckt ob chache vorhanden ist

    /**
     *
     * To this method customWeight parameter in the form smw-{attributeName}-{ratingClass}={Doubleweighvalue} can be send
     *
     * @param caseId
     * @param httpServletRequest
     * @return
     * @throws CbrRetrieveAgent.CbrRetrieveException
     * @throws CbrRuntimeException
     */
    @RequestMapping(method= RequestMethod.GET,value=getSimilarCasesForCasePath,produces = MediaType.APPLICATION_JSON_VALUE)
    public SimilarityResult getSimilarCasesForCase(@PathVariable("caseId") String caseId, @RequestParam(required = false) Boolean cache,  HttpServletRequest httpServletRequest) throws CbrRetrieveAgent.CbrRetrieveException, CbrRuntimeException {

        if(cache ==null)
        {
            cache=true;
        }

        //check if the similarityResult exists already in cache, if so give it back
        //create url for retrieveing and storing in cache... the url must be witouht the contextPath just rest/... because the client just sends this information

        Long startTime = System.nanoTime();

        String queryString ="";

        if( httpServletRequest.getQueryString()!=null)
        {
            queryString = queryString +"?" +httpServletRequest.getQueryString();
        }



        String url=null;
        if(httpServletRequest!=null)
            url = httpServletRequest.getRequestURI().toString().replace(App.ctx.getEnvironment().getProperty("server.contextPath")+"/","") + queryString;

        SimilarityResult similarityResult=null;
        if(cache==true)
        similarityResult   = UrlResponseCacheManager.getInstance().<SimilarityResult>getCachedObject(url);

        if(similarityResult!=null)
        {
            return similarityResult;
        }


        //if the similarityResult does not exist in cache we need first the related case for the Request
        //we get the case by creating a httpServletRequestMock and invoke the case service method
        String mockUrl = null;
        if(httpServletRequest.getParameterMap().get("caseCacheUrl")!=null) {
            mockUrl = httpServletRequest.getParameterMap().get("caseCacheUrl")[0];
        }

        //if mockUrl is null or the mockUrl does not fit with the related caseId form the request we need to build another requesturi for the mock
        if(mockUrl==null || !mockUrl.startsWith("rest/"+getCasePath.replace("{caseId}",caseId)))
        {
            mockUrl = App.ctx.getEnvironment().getProperty("server.contextPath")+"/rest/"+getCasePath.replace("{caseId}",caseId);
        }

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(mockUrl);
        CCase queryCase = this.getCCase(caseId,cache, mockHttpServletRequest);





            //Get the similarity Result from the cbrManager and store the result in the cache
            similarityResult = (SimilarityResult) App.getCbrManager().retrieve(queryCase, filterCustomSimilarityMeasureWeights(httpServletRequest.getParameterMap()));

            UrlResponseCacheManager.getInstance().addResponseCache(new UrlResponseCache(System.nanoTime(), url, similarityResult));

        logger.info("similar cases calculated in milliseconds: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));

            return similarityResult;

    }



    @RequestMapping(method= RequestMethod.GET,value=getNbaForCasePath,produces = MediaType.APPLICATION_JSON_VALUE)
    public NbaResult getNbaForCase(@PathVariable("caseId") String caseId, @RequestParam(required = false) Boolean cache, HttpServletRequest httpServletRequest) throws CbrRuntimeException, CbrRetrieveAgent.CbrRetrieveException, CbrReuseAgent.CbrReuseException {


        if(cache ==null)
        {
            cache=true;
        }

        Long startTime = System.nanoTime();
        //check if the NbaResult exists already in cache, if so give it back
        String queryString ="";

        if( httpServletRequest.getQueryString()!=null)
        {
            queryString = queryString +"?" +httpServletRequest.getQueryString();
        }


        String url=null;
        if(httpServletRequest!=null)
            url = httpServletRequest.getRequestURI().toString().replace(App.ctx.getEnvironment().getProperty("server.contextPath")+"/","") +queryString;


        NbaResult nbaResult = null;
        if(cache==true)
        nbaResult = UrlResponseCacheManager.getInstance().<NbaResult>getCachedObject(url);

        if(nbaResult!=null)
        {
            return nbaResult;
        }


        //if the nbaResult does not exist in cache we need to get the similarityResult
        //we get the similarityResult by creating a httpServletRequestMock and invoke the Similarity service method
        String mockUrl = null;
        if(httpServletRequest.getParameterMap().get("similarityCacheUrl")!=null) {
            mockUrl = httpServletRequest.getParameterMap().get("similarityCacheUrl")[0];

        }
        //if mockUrl is null or the mockUrl does not fit with the related caseId form the request we need to build another requesturi for the mock
        if(mockUrl==null || !mockUrl.startsWith("rest/"+getSimilarCasesForCasePath.replace("{caseId}",caseId)))
        {
            mockUrl = App.ctx.getEnvironment().getProperty("server.contextPath")+"/rest/"+getSimilarCasesForCasePath.replace("{caseId}",caseId)+queryString;
        }

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(mockUrl);
        SimilarityResult similarityResult = this.getSimilarCasesForCase(caseId,cache, mockHttpServletRequest);


        //Get the nba Result from the cbrManager and store the result in the cache
        nbaResult = (NbaResult) App.getCbrManager().reuse(similarityResult,filterCustomRatingWeights(httpServletRequest.getParameterMap()));

        UrlResponseCacheManager.getInstance().addResponseCache(new UrlResponseCache(System.nanoTime(),url,nbaResult));


        logger.info("nba calculated in milliseconds: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));

        return nbaResult;


    }




    @RequestMapping(method= RequestMethod.GET,value=getCaseBaseRenewPath)
    public void renew(HttpServletResponse response) throws CbrRetainAgent.CbrRetainException, IOException {

        App.getCbrManager().retain();

       // response.sendRedirect(App.ctx.getEnvironment().getProperty("server.contextPath") + "/rest/" + getCaseBasePath);

    }




    @RequestMapping(method= RequestMethod.GET,value=getReInitPath,produces = MediaType.APPLICATION_XML_VALUE)
    public void reInitXml(HttpServletResponse response) throws CbrRetainAgent.CbrRetainException, IOException {

        App.reinitialize();

        response.sendRedirect(App.ctx.getEnvironment().getProperty("server.contextPath") + "/rest/" + getDefinitionPath);
    }

    @RequestMapping(method= RequestMethod.GET,value=getReInitPath,produces = MediaType.APPLICATION_JSON_VALUE)
    public void reInitJson(HttpServletResponse response) throws CbrRetainAgent.CbrRetainException, IOException {

        App.reinitialize();

        response.sendRedirect(App.ctx.getEnvironment().getProperty("server.contextPath") + "/rest/" + getDefinitionPath);
    }


    @RequestMapping(method= RequestMethod.GET,value=getNbaConfigPath,produces = MediaType.APPLICATION_JSON_VALUE)
    public NbaConfig getNbaConfig()  {

       return App.getCbrManager().getNbaReasonerConfig().getNbaConfig();

    }

    @RequestMapping(method= RequestMethod.GET,value=getCbrConfigPath,produces = MediaType.APPLICATION_JSON_VALUE)
    public CbrConfig getCbrConfig()  {

       return App.getCbrManager().getNbaReasonerConfig().getCbrConfig();

    }



      @RequestMapping(method= RequestMethod.GET,value=getDefinitionPath,produces = MediaType.APPLICATION_XML_VALUE)
    public void getDefinitionAsXml( HttpServletResponse response) throws IOException, JAXBException {

        App.getCbrManager().serializeEDefinitionToOutputStream(response.getOutputStream());

        response.getOutputStream().flush();


    }

    @RequestMapping(method= RequestMethod.GET,value=getDefinitionPath,produces = MediaType.APPLICATION_JSON_VALUE)
    public CCaseBaseDefinition getDefinitionAsJson() throws IOException, JAXBException {

       return App.getCbrManager().getCbmlDefinition();


    }


    //weights must be in the form smw-{attributeName}-{SimilarityMeasureName}
    private static Map<String,Double> filterCustomSimilarityMeasureWeights(Map<String,String[]> parameterMap)
    {
        Map<String,Double> resultMap = new HashMap<>();
        for(Map.Entry<String,String[]> entry : parameterMap.entrySet())
        {
            if(entry.getKey().startsWith("smw-"))
            {
                try {
                    resultMap.put(entry.getKey(),Double.valueOf(entry.getValue()[0]));
                }catch (NumberFormatException e)
                {
                    throw new RuntimeException("Value " +entry.getValue()+" of url request parameter " + entry.getKey() +" is not parsable to a Double");
                }

            }
        }

        return resultMap;
    }

    private static Map<String,Double> filterCustomRatingWeights(Map<String,String[]> parameterMap)
    {


        Map<String,Double> resultMap = new HashMap<>();
        for(Map.Entry<String,String[]> entry : parameterMap.entrySet())
        {
            if(entry.getKey().startsWith("rw-"))
            {
                try {
                    resultMap.put(entry.getKey(),Double.valueOf(entry.getValue()[0]));
                }catch (NumberFormatException e)
                {
                    throw new RuntimeException("Value " +entry.getValue()+" of url request parameter " + entry.getKey() +" is not parsable to a Double");
                }

            }
        }



        return resultMap;
    }

    class MockHttpServletRequest implements HttpServletRequest
    {


        private final String  requestURI;
       private final String  queryString;
       Map<String,String[]> parameterMap = new HashMap<>();

        /**
         *
         * @param requestURIAndQuery in the form /{contextPath]/rest?query=123&da=asd
         */
        public MockHttpServletRequest(String  requestURIAndQuery) {

            String[] array = requestURIAndQuery.split("\\?");

            this.requestURI = array[0];

            if(array.length>1)
            {
                this.queryString = array[1];

                String[] parameters = queryString.split("&");

                for(String s: parameters)
                {
                    String[] parameterSplit =  s.split("=");
                    parameterMap.put(parameterSplit[0],new String[]{parameterSplit[1]});
                }
            }
            else {
                this.queryString = null;
            }



        }

        @Override
        public String toString() {



            return "MockHttpServletRequest{" +
                    "requestURI='" + requestURI + '\'' +
                    ", queryString='" + queryString + '\'' +
                    ", parameterMap=" + parameterMap.toString() +
                    '}';
        }

        @Override
        public String getAuthType() {
            return null;
        }

        @Override
        public Cookie[] getCookies() {
            return new Cookie[0];
        }

        @Override
        public long getDateHeader(String name) {
            return 0;
        }

        @Override
        public String getHeader(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            return null;
        }

        @Override
        public int getIntHeader(String name) {
            return 0;
        }

        @Override
        public String getMethod() {
            return null;
        }

        @Override
        public String getPathInfo() {
            return null;
        }

        @Override
        public String getPathTranslated() {
            return null;
        }

        @Override
        public String getContextPath() {
            return null;
        }

        @Override
        public String getQueryString() {
            return this.queryString;
        }

        @Override
        public String getRemoteUser() {
            return null;
        }

        @Override
        public boolean isUserInRole(String role) {
            return false;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public String getRequestedSessionId() {
            return null;
        }

        @Override
        public String getRequestURI() {
            return this.requestURI;
        }

        @Override
        public StringBuffer getRequestURL() {
            return null;
        }

        @Override
        public String getServletPath() {
            return null;
        }

        @Override
        public HttpSession getSession(boolean create) {
            return null;
        }

        @Override
        public HttpSession getSession() {
            return null;
        }

        @Override
        public String changeSessionId() {
            return null;
        }

        @Override
        public boolean isRequestedSessionIdValid() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromCookie() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromURL() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromUrl() {
            return false;
        }

        @Override
        public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
            return false;
        }

        @Override
        public void login(String username, String password) throws ServletException {

        }

        @Override
        public void logout() throws ServletException {

        }

        @Override
        public Collection<Part> getParts() throws IOException, ServletException {
            return null;
        }

        @Override
        public Part getPart(String name) throws IOException, ServletException {
            return null;
        }

        @Override
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass) throws IOException, ServletException {
            return null;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String getCharacterEncoding() {
            return null;
        }

        @Override
        public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

        }

        @Override
        public int getContentLength() {
            return 0;
        }

        @Override
        public long getContentLengthLong() {
            return 0;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public String getParameter(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return null;
        }

        @Override
        public String[] getParameterValues(String name) {
            return new String[0];
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return this.parameterMap;
        }

        @Override
        public String getProtocol() {
            return null;
        }

        @Override
        public String getScheme() {
            return null;
        }

        @Override
        public String getServerName() {
            return null;
        }

        @Override
        public int getServerPort() {
            return 0;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return null;
        }

        @Override
        public String getRemoteAddr() {
            return null;
        }

        @Override
        public String getRemoteHost() {
            return null;
        }

        @Override
        public void setAttribute(String name, Object o) {

        }

        @Override
        public void removeAttribute(String name) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public Enumeration<Locale> getLocales() {
            return null;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            return null;
        }

        @Override
        public String getRealPath(String path) {
            return null;
        }

        @Override
        public int getRemotePort() {
            return 0;
        }

        @Override
        public String getLocalName() {
            return null;
        }

        @Override
        public String getLocalAddr() {
            return null;
        }

        @Override
        public int getLocalPort() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public AsyncContext startAsync() throws IllegalStateException {
            return null;
        }

        @Override
        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            return null;
        }

        @Override
        public boolean isAsyncStarted() {
            return false;
        }

        @Override
        public boolean isAsyncSupported() {
            return false;
        }

        @Override
        public AsyncContext getAsyncContext() {
            return null;
        }

        @Override
        public DispatcherType getDispatcherType() {
            return null;
        }
    }


}

