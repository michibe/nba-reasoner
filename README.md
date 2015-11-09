# Next Best Action Reasoner

:heavy_exclamation_mark: This project is under development and therefore still a little bit buggy :heavy_exclamation_mark:


This repository consists out of the main framework [nba-reasoner](/nba-reasoner) and two sample projects ([nba-simple-insurance](/nba-simple-insurance), [nba-tic-tac-toe](/nba-tic-tac-toe)) using the framework to suggest next best actions.

## [nba-reasoner](/nba-reasoner)

This prototype results out of a thesis for generating a framework to suggest the next best action in the context of Adaptive Case Management for an insurance claim management.

#### Overview

The prototype is based on a concept named Case Based Reasoning. We used CBR in a slightly customized way. Instead of running the complete cycle and learn every time after using the proposed solution we ignore the retain phase until a case is completed.


#### Case Base Model Language (CBML)

The CBML is a format for storing cases with their events and activities. It has similarities to the [XES-Standard] (http://www.xes-standard.org/ but is more strict and has some additional features like the definition of data sources for the case-attributes.   
The main structure looks as follows:


```xml
<cbml>
    <caseBaseDefinition>
        ...
        <caseDefinition>
            ...
            <activityDefinition>
                ...
            </activityDefinition>
            <eventDefinition>
                ...
            </eventDefinition>
        </caseDefinition>
    </caseBaseDefinition>
    <caseBase>
        ...
        <case>
            ...
            <activity>
                ...
            </activity>
            <activity>
                ...
            </activity>
            <event>
                ...
            </event>        
        </case>
        <case>
            ...
        </case>
    </caseBase>
</cbml>
```

```xml
<cbml>
	<caseBaseDefinition> 
        <caseDefinition>      
            <loadedAttributeDefinition key="caseId" type="{Datentyp}" value="{Standardwert}">
                <loadingSourceClosedCase class="{FQN der Klasse}">
                    {Parameter}
                </loadingSourceClosedCase>						
            </loadedAttributeDefinition>
            <loadedAttributeDefinition key="lifecycleStatus" type="{Datentyp}" value="{Standardwert}">
                <loadingSourceClosedCase class="{FQN der Klasse}">
                    {Parameter}
                </loadingSourceClosedCase>
                <loadingSourceOpenedCase class="{FQN der Klasse}">
                    {Parameter}
                </loadingSourceOpenedCase>
            </loadedAttributeDefinition>
            <activityDefinition>
                <loadedAttributeDefinition key="incidentId" type="{Datentyp}" value="{Standardwert}">
                    <loadingSourceClosedCase class="{FQN der Klasse}">
                        {Parameter}
                    </loadingSourceClosedCase>
                    <loadingSourceOpenedCase class="{FQN der Klasse}">
                        {Parameter}
                    </loadingSourceOpenedCase>
                </loadedAttributeDefinition>
                <loadedAttributeDefinition key="name" type="{Datentyp}" value="{Standardwert}">
                    <loadingSourceClosedCase class="{FQN der Klasse}">
                        {Parameter}
                    </loadingSourceClosedCase>
                    <loadingSourceOpenedCase class="{FQN der Klasse}">
                        {Parameter}
                    </loadingSourceOpenedCase>
                </loadedAttributeDefinition>         
                <loadedAttributeDefinition key="timestamp" type="{Datentyp}" value="{Standardwert}">
                    <loadingSourceClosedCase class="{FQN der Klasse}">
                        {Parameter}
                    </loadingSourceClosedCase>
                    <loadingSourceOpenedCase class="{FQN der Klasse}">
                        {Parameter}
                    </loadingSourceOpenedCase>
                </loadedAttributeDefinition>
            </activityDefinition>
        </caseDefinition>
    </caseBaseDefinition>
    <caseBase> </caseBase>
</cbml>
```



#### Prepare and Configure

For using the reasoner you have to prepare a few files:

- The cbml.xml file for configuring the case base.

- The nba-reasoner-config.xml file for configuring the similarity-functions and the ratings.

- The application.properties file for configuring the application and the spring boot settings.







#### Using the reasoner

To use the reasoner in a project just add it's maven-dependency and the spring-boot-maven-plugin with the repackage-goal, so the main-class in the framework will be detected.

```xml
<dependency>
  <groupId>com.hhz</groupId>
  <artifactId>nbareasoner</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```


```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <version>1.1.3.RELEASE</version>
    <configuration>
        <mainClass>org.hhz.nbareasoner.init.App</mainClass>
        <layout>ZIP</layout>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

:heavy_exclamation_mark: The project is not available in the official maven-repository. To use the project as described above you have to clone it first and install it via maven in the local repository. :heavy_exclamation_mark:


#### Running the reasoner
#### Rest Interfaces

HTTP-Method | Produce-Type | URL | Short Description
------------ | ------------- | ------------- | ------------- 
GET | XML/JSON | /rest/casebase | Returns the casebase as XML/JSON.
GET | XML/JSON | /rest/casebase/definition | Returns the definition section of the casebase. 
GET | JSON | /rest/casebase/cases | returns all cases as list that are stored in the memory.
GET | XML/JSON | /rest/casebase/renew | Recreates the casebase with help of the cbml-file. 
GET | JSON | /rest/cases/{caseId} | Returns the case (closed as well as opened cases) with the given case-id.
GET | JSON | /rest/cases/{caseId}/similarcases | Runs the RETRIEVE-phase for the case with the given case-id and returns the similar cases with the used similar functions. 
GET | JSON | /rest/cases/{caseId}/nba | Runs the REUSE-phase for the case with the given case-id and returns the NBAs enriched with the results of the used ratings. 
GET | JSON | /rest/nba/config | Returns the ratings defined in the nba-reasoner-config.xml file.
GET | JSON | /rest/cbr/config | returns the similarity functions defined in the nba-reasoner-config.xml file.
GET | XML/JSON | /rest/reinit | Reinitialize the application by reload the cbml- and the nba-reasoner-config files. The casebase remains empty until the service /rest/casebase/renew is called.




## [nba-simple-insurance](/nba-simple-insurance)

The first use-case the reasoner was developed for, was the insurance claim management. This use-case is often used as an example in the context of adaptive case management. You can see the nba-reasoner as one of the ACM Building Blocks (Aiding Engine) introduced in the [ACM Poster](http://www.opitz-consulting.com/fileadmin/redaktion/pdf/sonstiges/acm-in-practice_poster-a3_sicher.pdf). 

### Java Project

The folder [nba-simple-insurance](/nba-simple-insurance/nba-simple-insurance) contains the java-project. 
For this running example no additional similarity functions or ratings were necessary, so the project is just an empty project with the pom-file described above.

### Workable Sample

The folder [working-example](/nba-simple-insurance/working-example) contains a complete running environment with all necessary files to run the nba-simple-insurance application. Just download the folder, open the directory in a terminal and run the application via *java -jar nba-simple-insurance.jar*.
After this you should be able to use the rest-urls or the gui under http://localhost:8080/nbareasoner.

![GUI Overview](/images/nba-simple-insurance-gui-1.PNG)

![GUI Overview](/images/nba-simple-insurance-gui-2.PNG)

![GUI Overview](/images/nba-simple-insurance-gui-3.PNG)


## [nba-tic-tac-toe](/nba-tic-tac-toe)

We tried to extend the use-case of the framework by apply it to a tic tac toe game. The reasoner is able to suggest the next field to play. It is also possible to play against a bot that is using the reasoner for the next step.
Of course the reasoner is just as good as the underlying casebase is. So if the casebase consists out of good games the bot will play good as well. 


### Java Project
The folder [nba-tic-tac-toe](/nba-tic-tac-toe) contains the java-project. 
For this example we had to build additional similarity functions and ratings which can be found under the src folder.

### Workable Sample

The folder [working-example](/nba-tic-tac-toe/working-example) contains a complete running environment with all necessary files to run the nba-tic-tac-toe application. Just download the folder, open the directory in a terminal and run the application via *java -jar nba-tic-tac-toe.jar*.
After this you should be able to use the rest-urls or the gui under http://localhost:8080/nbareasoner.

The tic-tac-toe gui is reachable under http://localhost:8080/nbareasoner/tictactoe.html.

![GUI Overview](/images/nba-tic-tac-toe-1.PNG)

![GUI Overview](/images/nba-tic-tac-toe-2.PNG)
