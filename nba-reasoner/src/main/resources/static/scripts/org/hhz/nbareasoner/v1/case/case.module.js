   var caseModule = angular.module("orgHhzNbareasonerCaseModule", ['ngRoute',"app"]);

   caseModule.factory('orgHhzNbareasonerCaseGeneralService', function($http,$rootScope) {
    return {



getCase : function(caseId){

            var url = "rest/cases/" + caseId;

            if($rootScope.useMock)
            {
                url = "assets/mocks/case.json";
            }


           return $http.get(url);


}


    	};
   });




caseModule.directive('orgHhzNbareasonerCaseDirective', function() {
  return {
    restrict: 'E',
    scope: {
                  case: '='
              },
    templateUrl: 'scripts/org/hhz/nbareasoner/v1/case/case.directive.html',
    controller: function($scope, colorService,orgHhzNbareasonerConfigGeneralService)
    {
        $scope.cbrConfig = orgHhzNbareasonerConfigGeneralService.getCbrConfig();
        $scope.colorService = colorService;

    }
  };
});


//With this filter it is possible to get one specific attribute of the attribute list of the case
caseModule.filter('attributesKeyFilter', function() {
  return function(attributeList,key) {

  for (var i = 0; i < attributeList.length; i++) {
          if (attributeList[i].key == key) {
              return attributeList[i].value;
          }

                  }

    return "no attribute value found for " +key;
  };
});

