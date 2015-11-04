# Next Best Action Reasoner

:heavy_exclamation_mark: This project is under development and therefore still a little bit buggy


This repository consists out of the main framework [nba-reasoner](/nba-reasoner) and two sample projects ([nba-simple-insurance](/nba-simple-insurance), [nba-tic-tac-toe](/nba-tic-tac-toe)) using the framework to suggest next best actions.

## [nba-reasoner](/nba-reasoner)

This prototype results out of a thesis for generating a framework to suggest the next best action in the context of Adaptive Case Management for an insurance claim management.

#### Overview

The prototype is based on a concept named Case Based Reasoning. We used CBR in a slightly customized way. Instead of running the complete cycle and learn every time after using the proposed solution we ignore the retain phase until a case is completed.



#### Prepare and Configure for use

For using the reasoner you have to prepare a few files:





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

:heavy_exclamation_mark: The project is not available in the official maven-repository. To use the project as described above you have to clone it first and install it via maven in the local repository.


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

The first use-case the reasoner was developed for, was the insurance claim management. This use-case is often used as an example in the context of adaptive case management.

The folder [nba-simple-insurance](/nba-simple-insurance/nba-simple-insurance) contains the java-project. 

The folder [working-example](/nba-simple-insurance/working-example) contains a complete running environment with all necessary files to run the nba-simple-insurance application. Just download the folder, open the directory in a terminal and run the application via *java -jar nba-simple-insurance.jar*.
After this you should be able to use the rest-urls or the gui under http://localhost:8080/nbareasoner/.

![GUI Overview](/images/nba-simple-insurance-gui-1.png)


## [nba-tic-tac-toe](/nba-tic-tac-toe)

We tried to extend the use-case of the framework by apply it to a tic tac toe game. The reasoner is able to suggest the next field to play. It is also possible to play against a bot that is using the reasoner for the next step.
Of course the reasoner is just as good as the underlying casebase is. So if the casebase consists out of good games the bot will play good as well. 