'use strict';

angular.module('puydufouApp')
    .controller('ParcDetailController', function ($scope, $stateParams, Parc, Activite) {
        $scope.parc = {};
        $scope.load = function (id) {
            Parc.get({id: id}, function(result) {
              $scope.parc = result;
            });
        };
        $scope.load($stateParams.id);
    });
