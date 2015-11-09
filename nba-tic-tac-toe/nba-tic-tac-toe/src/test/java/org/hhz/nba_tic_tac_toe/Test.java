package org.hhz.nba_tic_tac_toe;


import org.hhz.nbareasoner.cbml.model.base.CException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbehr on 08.05.2015.
 */
public class Test {

    @org.junit.Test
    public void simulateGames() throws IOException, JAXBException, CException {


        Originator player1 = new Originator("Player1","blue");
        Originator player2 = new Originator("Player2","orange");

        List<ActivityLog> alreadyGone = new ArrayList<>();
         Game game00 = new Game(alreadyGone,player1,player2);
        game00.play(1,1,false);


       //alreadyGone = new ArrayList<>();
        //game00 = new Game(alreadyGone,player1,player2);
       // game00.play(0,1,false);



        System.out.println("games: " + Game.games.values().size());






        //Game.printGames();


    }





}
