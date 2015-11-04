 var nbaModule = angular.module("orgHhzNbareasonerNbaModule", ['ngRoute',"orgHhzNbareasonerCaseModule"]);




   nbaModule.factory('orgHhzNbareasonerNbaGeneralService', function($http,$rootScope) {
    return {



getNba : function(caseId,similarityCacheUrl,customWeightsPathVariables){


   var url = 'rest/cases/' + caseId +"/nba" ;

             if(similarityCacheUrl != undefined)
             {
                 url = url + "?similarityCacheUrl=" + similarityCacheUrl;
             }

    if(customWeightsPathVariables!= undefined)
    {
        url = url +customWeightsPathVariables;
    }

    if($rootScope.useMock) {
        url = "assets/mocks/nba.json";
    }

           return $http.get(url);
}


    	};
   });



nbaModule.directive('orgHhzNbareasonerNbasDirective', function() {
  return {
    restrict: 'E',
    scope: {
                  nbaProposals: '='
              },
    templateUrl: 'scripts/org/hhz/nbareasoner/v1/nba/nbas.directive.html',
    controller: function($scope,colorService)
    {

    $scope.colorService = colorService;

    }
  };
});



