'use strict';

angular.module('puydufouApp')
    .controller('Menu_RestaurantDetailController', function ($scope, $stateParams, Menu_Restaurant, Activite) {
        $scope.menu_Restaurant = {};
        $scope.load = function (id) {
            Menu_Restaurant.get({id: id}, function(result) {
              $scope.menu_Restaurant = result;
            });
        };
        $scope.load($stateParams.id);
    });
