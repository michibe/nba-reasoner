   var similarityModule = angular.module("orgHhzNbareasonerSimilarityModule", ['ngRoute']);

   similarityModule.factory('orgHhzNbareasonerSimilarityGeneralService', function($http,$rootScope) {
    return {



getSimilarCases : function(caseId, cseCacheUrl,customWeightsPathVariables){



            var url = 'rest/cases/' + caseId +"/similarcases" ;

            if(cseCacheUrl != undefined)
            {
                url = url + "?caseCacheUrl=" + cseCacheUrl;
            }
            if(customWeightsPathVariables!= undefined)
            {
                url = url +customWeightsPathVariables;
            }

    if($rootScope.useMock) {
        url = "assets/mocks/similarCases.json"
    }
           return $http.get(url);


}


    	};
   });


   similarityModule.directive('orgHhzNbareasonerSimilarCasesDirective', function() {
     return {
       restrict: 'E',
       scope: {
                     similarCases: '='
                 },
       templateUrl: 'scripts/org/hhz/nbareasoner/v1/similarity/similarcases.directive.html',
       controller: function($scope,colorService)
       {

                $scope.colorService = colorService;

       }
     };
   });