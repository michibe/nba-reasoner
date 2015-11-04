var nbaReasonerApp = angular.module("app", ["ngRoute","orgHhzNbareasonerCaseModule","orgHhzNbareasonerSimilarityModule","orgHhzNbareasonerNbaModule","orgHhzNbareasonerConfigModule"]);




nbaReasonerApp.config(['$routeProvider','$locationProvider',
    function($routeProvider,$locationProvider) {


    }]);

 nbaReasonerApp.controller("appController", function($location,$scope,$rootScope, $log,orgHhzNbareasonerCaseGeneralService,orgHhzNbareasonerSimilarityGeneralService,orgHhzNbareasonerNbaGeneralService,orgHhzNbareasonerConfigGeneralService,colorService)
 {



      $scope.colorService =colorService;


     if($location.search()['color1']!=undefined)
     {
         colorService.setColor1($location.search()['color1']);
     }
     if($location.search()['color2']!=undefined)
     {
         colorService.setColor1($location.search()['color2']);
     }
     if($location.search()['color3']!=undefined)
     {
         colorService.setColor1($location.search()['color3']);
     }if($location.search()['color4']!=undefined)
     {
         colorService.setColor1($location.search()['color4']);
     }
     if($location.search()['color5']!=undefined)
     {
         colorService.setColor1($location.search()['color5']);
     }

     $rootScope.useMock =false;


     $scope.reset = function()
     {
         $scope.activeCase = null;
         $scope.similarCases  = null;
         $scope.nbaProposals = null;
     }


                $scope.enterFunction = function(ev) {

                        if (ev.which == 13)
                        {
                        //$scope.removeSearchedCase();
                          //  $("#searched-case-container").find("h2").addClass("loading");
                            $scope.reset();
                            loadData($scope.caseSearchId);
                        }

                    };



    loadData = function(id)
    {
                orgHhzNbareasonerCaseGeneralService.getCase(id).success(
                function(data, status, headers, config) {

                    $scope.activeCase = data;

                    loadSimilarCases(id,config.url.replace(new RegExp("&", 'g'), "%26").replace(new RegExp("=", 'g'), "%3D"));

               }).error(function(data, status, headers, config) {
                    $log.error("Could not load active case due to: " + JSON.stringify(data));
               });
    };

    loadSimilarCases = function(id,caseCacheUrl)
        {

                    orgHhzNbareasonerSimilarityGeneralService.getSimilarCases(id,caseCacheUrl,orgHhzNbareasonerConfigGeneralService.getCbrConfigAsQueryString()).success(
                    function(data, status, headers, config) {

                  $scope.similarCases = data.similarCases;

                  loadNba(id,config.url.replace(new RegExp("&", 'g'), "%26").replace(new RegExp("=", 'g'), "%3D"));

                   }).error(function(data, status, headers, config) {
                        $log.error("Could not load similar cases due to: " + JSON.stringify(data));
                   });
        };

    loadNba = function(id,similarityCacheUrl)
    {

     orgHhzNbareasonerNbaGeneralService.getNba(id,similarityCacheUrl,orgHhzNbareasonerConfigGeneralService.getNbaConfigAsQueryString()).success(
                        function(data, status, headers, config) {

                      $scope.nbaProposals = data.nbaProposals;

                       }).error(function(data, status, headers, config) {
                            $log.error("Could not load nba due to: " + JSON.stringify(data));
                       });
    }









    //Suche starten

    //die directiven einbinden




 });


nbaReasonerApp.factory('colorService', function() {
    var colors = {
        color1: '#9BB8CF', //'#619DCD',
        color2: "rgba(106, 106, 106,100)",
        color3: "#9BB8CF",
        color4: "#67C866",
        color5: "orange"

    };

    colors.setColor1 = function(color) {
      this.color1 = color;
    };

    colors.getColor1 = function() {
        return this.color1;
    };

    colors.setColor2 = function(color) {
      this.color2 = color;
    };

    colors.getColor2 = function() {
        return this.color2;
    };
    colors.setColor3 = function(color) {
      this.color3 = color;
    };

    colors.getColor3 = function() {
        return this.color3;
    };
    colors.setColor4 = function(color) {
      this.color4 = color;
    };

    colors.getColor4 = function() {
        return this.color4;
    };

    colors.setColor5 = function(color) {
      this.color5 = color;
    };

    colors.getColor5 = function() {
        return this.color5;
    };



    return colors;
});







