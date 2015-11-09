package org.hhz.nba_tic_tac_toe;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by mbehr on 20.10.2015.
 */
public class Game
{
    private final String id;
    private final Originator[][] board = new Originator[3][3];
    private final List<ActivityLog> activityLogList = new ArrayList<>();
    Originator activePlayer;
    private final Originator player1;
    private final Originator player2 ;

    private final int gameNumber;

    private     boolean gameFinished = false;
    static Map<String,Game> games = new HashMap<>();
    static int counter = 0;
    static int counterAusgabe = 0;

    public int getGameNumber() {
        return gameNumber;
    }

    public  Game(List<ActivityLog> startBasis,Originator player1,Originator player2)
    {





        this.player1 = player1;
        this.player2 = player2;


        activePlayer = player2;
        counter = counter +1;
        this.gameNumber =counter;

        Random randomGenerator = new Random();

        this.id = new Timestamp(System.currentTimeMillis()).getTime() + String.valueOf(randomGenerator.nextInt(1000000000)) ;
        ActivityLog activityToLog =new ActivityLog(this.id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"start game","started");
        //System.out.println(counter + " new " + activityToLog);
        activityLogList.add(activityToLog);

        for (ActivityLog activityLog : startBasis)
        {
            if(!activityLog.getActivity().equals("start game") && !activityLog.getActivity().equals("finish game") )
            {

                String [] result = activityLog.getResult().split("\\|");

                this.board[Integer.valueOf(result[0])][Integer.valueOf(result[1])] = activityLog.getOriginator();
                this.activityLogList.add(new ActivityLog(this.id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),activityLog.getOriginator(),activityLog.getActivity(),activityLog.getResult()));
            }

            activePlayer =activityLog.getOriginator();
        }






        games.put(this.id,this);


    }




    public static void printGames()
    {
        int counter = 0;

        String path = "C:/Users/mbehr/Desktop";
        //String path = "C:/Users/mbehr/Desktop/project-nba/Thesis/CD/working_example_tic_tac_toe";

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path+"/activity-log.csv",true);



            for(Map.Entry<String,Game> entry : games.entrySet()) {

                if(!entry.getKey().equals(entry.getValue().getActivityLogList().get(0).getGameId()))
                {
                    System.out.println(entry.getValue().getId());
                    counter ++;
                }



                for (ActivityLog activityLog : entry.getValue().getActivityLogList()) {
                    fileWriter.append("\n");
                    fileWriter.append(activityLog.getGameId() + ";");
                    fileWriter.append(activityLog.getTimestamp() + ";");
                    fileWriter.append(activityLog.getActivity() + ";");
                    fileWriter.append(activityLog.getOriginator().getName() + ";");
                    fileWriter.append(activityLog.getResult());

                }



            }
            fileWriter.flush();
            fileWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("counter " +counter);
    }





    private void startNewGameForOther(int row,int column)
    {
//System.out.println("start new game for " + row + "|" + column);

                    List<ActivityLog> activityLogsWithoutLastMove = new ArrayList<>(this.activityLogList);
                    activityLogsWithoutLastMove.remove(activityLogsWithoutLastMove.size()-1);

                    Game newGame = new Game(activityLogsWithoutLastMove,this.player1,this.player2);



        //wenn es ein spiel gibt, welches denselben status bereits abbildet, dann kein neues spiel starten


    newGame.play(row,column,false);




    }


    public void play(int row,int column,boolean startAlternatives)
    {
        if(this.gameFinished==false) {
            this.gameFinished = isGameFinished();
        }

        if(this.gameFinished==false) {


            nextPlayer();
            //setze auf das feld
            board[row][column] = activePlayer;
            ActivityLog activityToLog = new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()), activePlayer, "set pawn to " + row + "|" + column, row + "|" + column);
            activityLogList.add(activityToLog);




                    //pawn setzen
                    //alle alternativen spiele für diesen pawn starten
                    //in jedem alternativen spiel, sowie im angefangenenen:
                        //setze nächsten pawn


               if(startAlternatives)
               {
                   for (int i = 0; i < 3; i++) {
                       for (int a = 0; a < 3; a++) {
                           if (board[i][a] == null) {

                               startNewGameForOther(i,a);

                           }
                       }

                   }
               }



                    //spiele für nächstes freies feld
                    for (int i = 0; i < 3; i++) {
                        for (int a = 0; a < 3; a++) {
                            if (this.board[i][a] == null) {
                                try {
                                    TimeUnit.MILLISECONDS.sleep(1);
                                    this.play(i,a,true);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }




                }

            if(this.gameFinished==false) {
                this.gameFinished = isGameFinished();
            }





        }
        else
        {
          //  System.out.println("finished");
        }


    }


    private void nextPlayer()
    {

        if(activePlayer.getName().equals(player1.getName()))
        {
            activePlayer = this.player2;
        }
        else if (activePlayer.getName().equals(player2.getName()))
        {
            activePlayer = this.player1;
        }

    }

int gameFinishedCounter =0;





    public void finishAfterWards()
    {

       Originator[][] boardTmp = new Originator[3][3];
       List<ActivityLog> activityLogListTmp = new ArrayList<>(this.activityLogList);
       this.activityLogList.clear();


        for(ActivityLog activityLog : activityLogListTmp)
        {


            if(!activityLog.getActivity().equals("start game") && !activityLog.getActivity().equals("finish game") )
            {

                String [] result = activityLog.getResult().split("\\|");

                boardTmp[Integer.valueOf(result[0])][Integer.valueOf(result[1])] = activityLog.getOriginator();
            }

            this.activityLogList.add(activityLog);


          /*  if(isGameFinished(boardTmp))
            {
              break;
            }*/


        }



    }



    public boolean isGameFinished()
    {



        if( board[0][0] != null && board[0][0].equals(board[0][1]) && board[0][0].equals(board[0][2]))
        {
            activityLogList.add(new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"finish game",activePlayer.getName()));

            return true;

        }

        else if( board[1][0] != null && board[1][0].equals(board[1][1]) && board[1][0].equals(board[1][2]))
        {
            activityLogList.add(new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"finish game",activePlayer.getName()));

            return true;
        }

        else if( board[2][0] != null && board[2][0].equals(board[2][1]) && board[2][0].equals(board[2][2]))
        {
            activityLogList.add(new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"finish game",activePlayer.getName()));

            return true;
        }


        else if( board[0][0] != null && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]))
        {
            activityLogList.add(new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"finish game",activePlayer.getName()));

            return true;
        }

        else if( board[2][0] != null && board[2][0].equals(board[1][1]) && board[2][0].equals(board[0][2]))
        {
            activityLogList.add(new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"finish game",activePlayer.getName()));

            return true;
        }


        else if( board[0][0] != null && board[0][0].equals(board[1][0]) && board[0][0].equals(board[2][0]))
        {
            activityLogList.add(new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"finish game",activePlayer.getName()));

            return true;
        }

        else if( board[0][1] != null && board[0][1].equals(board[1][1]) && board[0][1].equals(board[2][1]))
        {
            activityLogList.add(new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"finish game",activePlayer.getName()));

            return true;
        }

        else if( board[0][2] != null && board[0][2].equals(board[1][2]) && board[0][2].equals(board[2][2]))
        {
            activityLogList.add(new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"finish game",activePlayer.getName()));

            return true;
        }

        else if (board[0][0] != null && board[0][1] != null && board[0][2] != null && board[1][0] != null && board[1][1] != null && board[1][2] != null && board[2][0] != null && board[2][1] != null && board[2][2] != null )
        {

            activityLogList.add(new ActivityLog(id,String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()),new Originator("game","blue"),"finish game","null"));



            return true;


        }
        else
        {
            return false;
        }

    }


    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", board=" + Arrays.toString(board) +
                ", activityLogList=" + activityLogList.toString() +
                '}';
    }

    public List<ActivityLog> getActivityLogList() {
        return activityLogList;
    }



    public static boolean existEqualGameStatus(Game game)
    {
        for(Game game1 : games.values())
        {
            if(!game1.equals(game))
            {
                if(game1.hasSameStatus(game))
                {
                    return true;
                }
            }


        }

        return false;
    }



    public boolean hasSameStatus(Game game)
    {
        for (int i = 0; i < 3; i++) {
            for (int a = 0; a < 3; a++) {
                if (board[i][a] == null && game.getBoard()[i][a]!=null)
                {

                    return false;

                }
                else if(board[i][a] != null && game.getBoard()[i][a]==null)
                {

                    return false;

                }
                else if(board[i][a] != null && game.getBoard()[i][a]!=null)
                {
                    if(!board[i][a].equals(game.getBoard()[i][a]))
                    {

                        return false;
                    }
                }
            }

        }
        return true;

    }


    public Originator[][] getBoard() {
        return board;
    }

    public String getId() {
        return id;
    }
}

