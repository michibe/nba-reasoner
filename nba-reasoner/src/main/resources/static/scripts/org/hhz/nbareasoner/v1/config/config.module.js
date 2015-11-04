 var configModule = angular.module("orgHhzNbareasonerConfigModule", ['ngRoute']);




 configModule.factory('orgHhzNbareasonerConfigGeneralService', function($http,$rootScope,$log) {

   var config={
       cbrConfig: undefined,
       nbaConfig: undefined
   };


    return {

        getCbrConfigAsQueryString : function()
        {
            var smwPathVariableString="";

            for(var i = 0; i<config.cbrConfig.caseSimilarityMeasureConfigs.length;i++)
            {
                var caseSimilarityMeasureConfig = config.cbrConfig.caseSimilarityMeasureConfigs[i];
                if(caseSimilarityMeasureConfig.weightDefault!=caseSimilarityMeasureConfig.weightCustom)
                smwPathVariableString = smwPathVariableString + "&smw-"+caseSimilarityMeasureConfig.attributeKey+"-"+caseSimilarityMeasureConfig.similarityMeasureClass+"="+caseSimilarityMeasureConfig.weightCustom;


            }



                $log.info("queryString " + smwPathVariableString);

            return smwPathVariableString;
        },

        getNbaConfigAsQueryString : function()
        {
            var rwPathVariableString="";

            for(var i = 0; i<config.nbaConfig.nbaRatingConfigs.length;i++)
            {
                var nbaRatingConfig = config.nbaConfig.nbaRatingConfigs[i];
                if(nbaRatingConfig.weightDefault!=nbaRatingConfig.weightCustom)
                    rwPathVariableString = rwPathVariableString + "&rw-"+nbaRatingConfig.ratingClass+"="+nbaRatingConfig.weightCustom;


            }



                $log.info("queryString " + rwPathVariableString);

            return rwPathVariableString;
        },


    setCbrConfig : function(cbrConfig)
     {
       config.cbrConfig = cbrConfig;

         for(var i = 0; i<config.cbrConfig.caseSimilarityMeasureConfigs.length;i++)
         {

             config.cbrConfig.caseSimilarityMeasureConfigs[i].weightCustom = config.cbrConfig.caseSimilarityMeasureConfigs[i].weightDefault;
         }

     },
        setNbaConfig : function(nbaConfig)
        {
            config.nbaConfig = nbaConfig;

            for(var i = 0; i<config.nbaConfig.nbaRatingConfigs.length;i++)
            {

                config.nbaConfig.nbaRatingConfigs[i].weightCustom = config.nbaConfig.nbaRatingConfigs[i].weightDefault;
            }

        },
        getCbrConfig : function()
        {
            return config.cbrConfig;
        },
        getNbaConfig : function()
        {
            return config.nbaConfig;
        }
,
    getNbaConfigFromServer : function(){


         var url = 'rest/nba/config' ;


        if($rootScope.useMock) {
            url = "assets/mocks/nbaconfig.json";
        }
           return $http.get(url);
},
        getCbrConfigFromServer : function(){


            var url = 'rest/cbr/config' ;

            if($rootScope.useMock) {
                url = "assets/mocks/cbrconfig.json";
            }


            return $http.get(url);
        }


    	};
   });



 configModule.directive('orgHhzNbareasonerConfigDirective', function() {
  return {
    restrict: 'E',
    scope: {

              },
    templateUrl: 'scripts/org/hhz/nbareasoner/v1/config/config.directive.html',
    controller: function($scope,colorService,orgHhzNbareasonerConfigGeneralService)
    {

       $scope.reset = $scope.$parent.reset;
        $scope.colorService = colorService;

        orgHhzNbareasonerConfigGeneralService.getCbrConfigFromServer().success(
            function(data, status, headers, config) {


                orgHhzNbareasonerConfigGeneralService.setCbrConfig(data);

                $scope.cbrConfig = orgHhzNbareasonerConfigGeneralService.getCbrConfig();



            }).error(function(data, status, headers, config) {
                $log.error("Could not load nba config case due to: " + JSON.stringify(data));
            });

        orgHhzNbareasonerConfigGeneralService.getNbaConfigFromServer().success(
            function(data, status, headers, config) {


                orgHhzNbareasonerConfigGeneralService.setNbaConfig(data);

                $scope.nbaConfig = orgHhzNbareasonerConfigGeneralService.getNbaConfig();



            }).error(function(data, status, headers, config) {
                $log.error("Could not load nba config case due to: " + JSON.stringify(data));
            });



    }
  };
});



