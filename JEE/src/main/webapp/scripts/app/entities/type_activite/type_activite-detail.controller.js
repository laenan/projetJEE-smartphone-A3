'use strict';

angular.module('puydufouApp')
    .controller('Type_activiteDetailController', function ($scope, $stateParams, Type_activite, Activite) {
        $scope.type_activite = {};
        $scope.load = function (id) {
            Type_activite.get({id: id}, function(result) {
              $scope.type_activite = result;
            });
        };
        $scope.load($stateParams.id);
    });
