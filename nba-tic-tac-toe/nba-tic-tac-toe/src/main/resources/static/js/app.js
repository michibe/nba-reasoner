
var ticTacToeApp = angular.module("ticTacToeApp", []);


 ticTacToeApp.controller("gameController", function($scope,$http,$log)
 {

    var activitiesLog = [];


    $scope.player1 = {name: "Player1", color: "#2253D8",aiEnabled : false};
    $scope.player2 = {name: "Player2", color: "#FCC200",aiEnabled : false};

    $scope.gameStatus = "SETTINGS";
    $scope.aiRepeatCount = 0;
    var moveNumber = 0;
    $scope.nbaEnabled = true;
    $scope.fastLearningEnabled = true;

    $scope.learningEnabled = true;

    $scope.startGame = function()
    {
        moveNumber = 0;
        activitiesLog = [];

        if($scope.player1.name!=null && $scope.player1.name!="" && $scope.player2.name!=null && $scope.player2.name!="" &&  $scope.player2.name != $scope.player1.name)
        {



                $scope.gameId = timestamp() +  Math.floor((Math.random() * 10000000) + 1);



            if($scope.player1.aiEnabled==true)
            {
                $scope.player1 = {name: "Artificial Intelligence1", color: "#2253D8",aiEnabled:true};
            }

            if($scope.player2.aiEnabled==true)
            {
                $scope.player2 = {name: "Artificial Intelligence2", color: "#FCC200",aiEnabled:true};
            }


          $scope.winner = "undefined";


                var players = [$scope.player1,$scope.player2];

            if($scope.random == true)
            {
                $scope.currentPlayer = players[Math.floor(Math.random() * players.length)];
            }
            else
            {
                $scope.currentPlayer = $scope.player1;
            }


                  $scope.board = [
                    [ { player: "undefined",id: "0|0",moveNumber: "",timestamp:"" }, { player: "undefined",id: "0|1",moveNumber: "",timestamp:""  }, { player: "undefined",id: "0|2",moveNumber: "",timestamp:""  } ],
                    [ { player: "undefined",id: "1|0",moveNumber: "",timestamp:""  }, { player: "undefined",id: "1|1",moveNumber: "",timestamp:""  }, { player: "undefined",id: "1|2",moveNumber: "",timestamp:""  } ],
                    [ { player: "undefined",id: "2|0",moveNumber: "" ,timestamp:"" }, { player: "undefined",id: "2|1",moveNumber: "",timestamp:""  }, { player: "undefined",id: "2|2",moveNumber: "",timestamp:""  } ]
                  ];


            $scope.gameStatus = "RUNNING";


            logActivity({'gameId':$scope.gameId,'timestamp':timestamp(),'originator':{'name':'game','color':'none'},'activity':'start game','result':'started'},function(){

                if(($scope.player2.aiEnabled ==true && $scope.currentPlayer==$scope.player2)|| ($scope.player1.aiEnabled ==true && $scope.currentPlayer==$scope.player1) )
                                        {
                                             invokeAIMove();
                                        }

            });





        }



    }


      $scope.restartGame = function()
        {
                $scope.startGame();
        }

     $scope.backToOptions = function()
     {
            $scope.gameStatus = "SETTINGS";
     }


    $scope.play = function(pawnField)
    {

        if($scope.winner=="undefined" && pawnField.player=="undefined" && (($scope.player2.aiEnabled==false && $scope.currentPlayer ==$scope.player2) || ($scope.player1.aiEnabled==false && $scope.currentPlayer ==$scope.player1) || ($scope.player1.aiEnabled==false && $scope.player2.aiEnabled==false ) ) )
        {

                moveNumber ++;
                pawnField.player =$scope.currentPlayer;
                pawnField.timestamp = timestamp();
                pawnField.moveNumber = moveNumber;




                logActivity( convertFieldToLog($scope.gameId,pawnField),function()
                {


                   if(checkGameStatus()==true)
                                        {
                                            setNextPlayer();
                                            if(($scope.player2.aiEnabled==true && $scope.currentPlayer ==$scope.player2) || ($scope.player1.aiEnabled==true && $scope.currentPlayer ==$scope.player1) )
                                           {
                                                invokeAIMove();
                                           }
                                        }
                });








        }


    };




    function invokeAIMove ()
    {


 var nextMove;

if($scope.nbaEnabled == true)
{


     $http.get("/nbareasoner/rest/cases/"+$scope.gameId+"/nba",{ params: {'foo': timestamp(),'cache':false}}).success(function(data, status) {






                if(data.nbaProposals.length == 0)
                {
                    //Alle freien felder
                    var emptyPawnFieldIds = [];
                    for(var i = 0; i < $scope.board.length; i++)
                    {
                        for(var a = 0; a < $scope.board[i].length; a++)
                        {
                            if($scope.board[i][a].player=='undefined')
                            {
                            $log.info("idd " + $scope.board[i][a].id);

                                emptyPawnFieldIds.push($scope.board[i][a].id);
                            }

                         }

                    }
                    nextMove =  emptyPawnFieldIds[Math.floor(Math.random() * emptyPawnFieldIds.length)];


                }
                else
                {

                     var possibleMoves = [];


                    for	(var index = 0; index < data.nbaProposals.length; index++) {
                         //Alle proposals die gleiches rate haben kommen in liste für einen move
                        if(data.nbaProposals[0].preparedRate == data.nbaProposals[index].preparedRate)
                        {

                            possibleMoves.push(data.nbaProposals[index]);
                        }

                    }


                     nextMove =   possibleMoves[Math.floor(Math.random() * possibleMoves.length)].toActivityName;

                      nextMove = nextMove.substring(nextMove.length-3);


                }


                aIMove(nextMove);

                }).error(function(data,status)
                { alert("error ");
                     alert("NBA Server not reachable. AI not able to play.")
                     $scope.gameStatus = "FINISHED";
                });
    }
    else
    {

                        //Alle freien felder
                        var emptyPawnFieldIds = [];
                        for(var i = 0; i < $scope.board.length; i++)
                        {
                            for(var a = 0; a < $scope.board[i].length; a++)
                            {
                                if($scope.board[i][a].player=='undefined')
                                {
                                $log.info("idd " + $scope.board[i][a].id);
                                    emptyPawnFieldIds.push($scope.board[i][a].id);
                                }

                             }

                        }
                        nextMove =  emptyPawnFieldIds[Math.floor(Math.random() * emptyPawnFieldIds.length)];

                        aIMove(nextMove);


    }




    }


        function aIMove(pawnFieldId)
        {


            var fields = pawnFieldId.split("|");

            var pawnField = $scope.board[fields[0]][fields[1]];

            moveNumber ++;
            pawnField.player =$scope.currentPlayer;
             pawnField.timestamp = timestamp();
            pawnField.moveNumber = moveNumber;

            logActivity(convertFieldToLog($scope.gameId,pawnField),function()
            {

               if(checkGameStatus())
                       {
                          setNextPlayer();

                          if($scope.currentPlayer.aiEnabled==true)
                          {
                              invokeAIMove();
                          }

                       }

            }

            );









        }



function convertFieldToLog(gameId,field)
{

return {'gameId':gameId,'timestamp':field.timestamp,'originator':field.player,'activity':"set pawn to " + field.id,'result':field.id};

}

function logActivity(activityToLog,successCallback)
    {

     $http.post("/nbareasoner/tictactoe/activity", activityToLog).success(function(data, status) {

                activitiesLog.push(activityToLog);

                successCallback();



                }).error(function(data,status)
                { alert("error ");
                     alert("Could not log activity on server. Game has to be canceled!");
                     $scope.gameStatus = "FINISHED";
                });
    }



    function logActivities(activitiesToLog,successCallback)
    {



        $http.post("/nbareasoner/tictactoe/activities", activitiesToLog).success(function(data, status) {

            successCallback();



            }).error(function(data,status)
            { alert("error ");
                 alert("Could not log activity on server. Game has to be canceled!");
                 $scope.gameStatus = "FINISHED";
            });
    };


    $scope.resetActivityLog = function()
    {



        $http.post("/nbareasoner/tictactoe/activity/reset").success(function(data, status) {



                   invokeLearning(function(){});



            }).error(function(data,status)
            { alert("error ");
                 alert("Could not reset activity log on server.");
                 $scope.gameStatus = "FINISHED";
            });
    };



function invokeLearning(callback)
    {


        if($scope.nbaEnabled)
        {
         $http.get("/nbareasoner/rest/casebase/renew").success(function(data, status) {

                   callback();



                    }).error(function(data,status)
                    { alert("error ");
                         $log.info("Could not invoke learning.");
                    callback();
                    });

        }
        else
        {
        callback();
        }

    };




    function setNextPlayer()
    {
        if($scope.currentPlayer==$scope.player1)
        {
        $scope.currentPlayer = $scope.player2;
        }
        else if($scope.currentPlayer==$scope.player2)
        {
         $scope.currentPlayer = $scope.player1;
        }


    };




    function checkGameStatus()
    {

           if( $scope.board[0][0].player != "undefined" && $scope.board[0][0].player == $scope.board[0][1].player && $scope.board[0][0].player == $scope.board[0][2].player)
           {

               finishGame($scope.board[0][0].player);

           }

           else if( $scope.board[1][0].player != "undefined" && $scope.board[1][0].player == $scope.board[1][1].player && $scope.board[1][0].player == $scope.board[1][2].player)
           {

                finishGame( $scope.board[1][0].player);
           }

           else if( $scope.board[2][0].player != "undefined" && $scope.board[2][0].player == $scope.board[2][1].player && $scope.board[2][0].player == $scope.board[2][2].player)
           {

                 finishGame($scope.board[2][0].player);
           }


           else if( $scope.board[0][0].player != "undefined" && $scope.board[0][0].player == $scope.board[1][1].player && $scope.board[0][0].player == $scope.board[2][2].player)
           {

                finishGame($scope.board[0][0].player);
           }

           else if( $scope.board[2][0].player != "undefined" && $scope.board[2][0].player == $scope.board[1][1].player && $scope.board[2][0].player == $scope.board[0][2].player)
           {

                finishGame($scope.board[2][0].player);
           }


           else if( $scope.board[0][0].player != "undefined" && $scope.board[0][0].player == $scope.board[1][0].player && $scope.board[0][0].player == $scope.board[2][0].player)
           {

                finishGame($scope.board[0][0].player);
           }

           else if( $scope.board[0][1].player != "undefined" && $scope.board[0][1].player == $scope.board[1][1].player && $scope.board[0][1].player == $scope.board[2][1].player)
           {

                finishGame($scope.board[0][1].player);
           }

           else if( $scope.board[0][2].player != "undefined" && $scope.board[0][2].player == $scope.board[1][2].player && $scope.board[0][2].player == $scope.board[2][2].player)
           {

                 finishGame($scope.board[0][2].player);
           }

           else if ($scope.board[0][0].player != "undefined" && $scope.board[0][1].player != "undefined"&& $scope.board[0][2].player != "undefined" && $scope.board[1][0].player != "undefined" && $scope.board[1][1].player != "undefined"&& $scope.board[1][2].player != "undefined"&& $scope.board[2][0].player != "undefined" && $scope.board[2][1].player != "undefined"&& $scope.board[2][2].player != "undefined" )
            {




                     finishGame({name:null});


            }
            else
            {
              return true;
            }



    };

    function finishGame(winner)
    {
        $scope.winner = winner;



        logActivity(  {'gameId':$scope.gameId,'timestamp':timestamp(),'originator':{'name':'game','color':'none'},'activity':'finish game','result':winner.name},function(){

        if($scope.fastLearningEnabled)
        {

                mirrorGame(function()
                {

                if($scope.learningEnabled)
                                {
                                        invokeLearning(function(){$scope.gameStatus = "FINISHED"





                                        if($scope.player1.aiEnabled == true && $scope.player2.aiEnabled == true)

                                                $scope.aiRepeatCount = $scope.aiRepeatCount -1;
                                                if($scope.aiRepeatCount >0)
                                                {
                                                    $scope.startGame();

                                                }

                                        });
                                }
                                else
                                {
                                    $scope.gameStatus = "FINISHED";
                                }


                });


        }
        else
        {

          if($scope.learningEnabled)
                {
                        invokeLearning(function(){$scope.gameStatus = "FINISHED"





                        if($scope.player1.aiEnabled == true && $scope.player2.aiEnabled == true)

                                $scope.aiRepeatCount = $scope.aiRepeatCount -1;
                                if($scope.aiRepeatCount >0)
                                {
                                    $scope.startGame();

                                }

                        });
                }
                else
                {
                    $scope.gameStatus = "FINISHED";
                }

        }






        });





    }






function round(number)
{
    return Math.round(number * 100) / 100;
};








function mirrorGame(successCallback)
{


var mirroredActivities =[];

//x-axis

        var boardx =  JSON.parse(JSON.stringify($scope.board));

        boardx[0][0].id="2|0";
        boardx[0][1].id="2|1";
        boardx[0][2].id="2|1";
        boardx[1][0].id="1|0";
        boardx[1][1].id="1|1";
        boardx[1][2].id="1|2";
        boardx[2][0].id="0|0";
        boardx[2][1].id="0|1";
        boardx[2][2].id="0|2";


         createMirroredActivitiesLog(mirroredActivities,boardx);

         var boardy =  JSON.parse(JSON.stringify($scope.board));

                 boardy[0][0].id="0|2";
                 boardy[0][1].id="0|1";
                 boardy[0][2].id="0|0";
                 boardy[1][0].id="1|2";
                 boardy[1][1].id="1|1";
                 boardy[1][2].id="1|0";
                 boardy[2][0].id="2|2";
                 boardy[2][1].id="2|1";
                 boardy[2][2].id="2|0";


         createMirroredActivitiesLog(mirroredActivities,boardy);


         var boarddiagonal1 =  JSON.parse(JSON.stringify($scope.board));

                 boarddiagonal1[0][0].id="0|0";
                 boarddiagonal1[0][1].id="1|0";
                 boarddiagonal1[0][2].id="2|0";
                 boarddiagonal1[1][0].id="0|1";
                 boarddiagonal1[1][1].id="1|1";
                 boarddiagonal1[1][2].id="2|1";
                 boarddiagonal1[2][0].id="0|2";
                 boarddiagonal1[2][1].id="1|2";
                 boarddiagonal1[2][2].id="2|2";


         createMirroredActivitiesLog(mirroredActivities,boarddiagonal1);


         var boarddiagonal2 =  JSON.parse(JSON.stringify($scope.board));

                 boarddiagonal2[2][2].id="0|0";
                 boarddiagonal2[1][2].id="1|0";
                 boarddiagonal2[0][2].id="2|0";
                 boarddiagonal2[2][1].id="0|1";
                 boarddiagonal2[1][1].id="1|1";
                 boarddiagonal2[0][1].id="2|1";
                 boarddiagonal2[2][0].id="0|2";
                 boarddiagonal2[1][0].id="1|2";
                 boarddiagonal2[0][0].id="2|2";


         createMirroredActivitiesLog(mirroredActivities,boarddiagonal2);





        logActivities(mirroredActivities,successCallback)



};



function createMirroredActivitiesLog(listMirroredActivities, boardToMirror)
{


        var mirrorGameId =  timestamp() + Math.floor((Math.random() * 10000000) + 1);


            var firstActivity = JSON.parse(JSON.stringify(activitiesLog[0]));


                $log.info(" normal" + JSON.stringify(activitiesLog[0]));
                $log.info(" firstActivity" + JSON.stringify(firstActivity));


            firstActivity.gameId = mirrorGameId;

             $log.info(" firstActivity" + JSON.stringify(firstActivity));

            listMirroredActivities.push(firstActivity);

            for (var i = 0; i<3;i++)
            {
                for (var a = 0; a<3;a++)
                            {
                            if(boardToMirror[i][a].player!="undefined")
                                 listMirroredActivities.push(convertFieldToLog(mirrorGameId,boardToMirror[i][a]));
                            }

            }

            var lastActivity = JSON.parse(JSON.stringify(activitiesLog[activitiesLog.length-1]));

                        lastActivity.gameId = mirrorGameId;

              listMirroredActivities.push(lastActivity);

};

function timestamp()
{
    if (!Date.now) {
        return new Date().getTime();
    }
    else
    {
    return Date.now();
    }
};



 });


