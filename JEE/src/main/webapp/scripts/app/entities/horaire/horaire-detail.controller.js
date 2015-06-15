'use strict';

angular.module('puydufouApp')
    .controller('HoraireDetailController', function ($scope, $stateParams, Horaire, Activite) {
        $scope.horaire = {};
        $scope.load = function (id) {
            Horaire.get({id: id}, function(result) {
              $scope.horaire = result;
            });
        };
        $scope.load($stateParams.id);
    });
