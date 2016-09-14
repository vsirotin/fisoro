'use strict';

angular.module('NumberShop', []).run(function($rootScope){
    $rootScope.pathData = 'data/data.json';
    $rootScope.pathModel = 'data/model.json';
}); //End of run

