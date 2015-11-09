package org.hhz.nba_tic_tac_toe;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by mbehr on 13.10.2015.
 */
@RestController
public class CsvRestService {

    @Autowired
    Environment environment;

    @RequestMapping(value = "tictactoe/activity", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST )
    public void addToActivityLog(@RequestBody ActivityLog activityLog) throws IOException {





        System.out.println("huhuuu " +activityLog);

       String path = this.environment.getProperty("user.dir");
      //String path = "C:/Users/mbehr/Desktop/project-nba/Thesis/CD/working_example_tic_tac_toe";

        FileWriter fileWriter = new FileWriter(path+"/activity-log.csv",true);

        fileWriter.append("\n");
        fileWriter.append(activityLog.getGameId()+";");
        fileWriter.append(activityLog.getTimestamp()+";");
        fileWriter.append(activityLog.getActivity()+";");
        fileWriter.append(activityLog.getOriginator().getName()+";");
        fileWriter.append(activityLog.getResult());

        fileWriter.flush();
        fileWriter.close();


    }


    @RequestMapping(value = "tictactoe/activities", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST )
    public void addToActivityLog(@RequestBody List<ActivityLog> activityLogs) throws IOException {







        String path = this.environment.getProperty("user.dir");
        //String path = "C:/Users/mbehr/Desktop/project-nba/Thesis/CD/working_example_tic_tac_toe";

        FileWriter fileWriter = new FileWriter(path+"/activity-log.csv",true);


        for(ActivityLog activityLog : activityLogs)
        {
            fileWriter.append("\n");
            fileWriter.append(activityLog.getGameId()+";");
            fileWriter.append(activityLog.getTimestamp()+";");
            fileWriter.append(activityLog.getActivity()+";");
            fileWriter.append(activityLog.getOriginator().getName()+";");
            fileWriter.append(activityLog.getResult());
        }



        fileWriter.flush();
        fileWriter.close();


    }


    @RequestMapping("tictactoe/activity/reset")
    public void resetActivityLog() throws IOException {

        String path = this.environment.getProperty("user.dir");
        //String path = "C:/Users/mbehr/Desktop/project-nba/Thesis/CD/working_example_tic_tac_toe";

        FileWriter fileWriter = new FileWriter(path+"/activity-log.csv",false);


        StringBuffer a = new StringBuffer();
        a.append("gameId"+";");
        a.append("timestamp"+";");
        a.append("activity"+";");
        a.append("originator"+";");
        a.append("result");

        fileWriter.write(a.toString());

        fileWriter.flush();
        fileWriter.close();



    }









}
