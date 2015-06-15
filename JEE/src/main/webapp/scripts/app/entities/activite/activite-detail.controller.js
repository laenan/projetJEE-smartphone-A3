'use strict';

angular.module('puydufouApp')
    .controller('ActiviteDetailController', function ($scope, $stateParams, Activite, Parc, Note, Type_activite, Horaire, Menu_Restaurant) {
        $scope.activite = {};
        $scope.load = function (id) {
            Activite.get({id: id}, function(result) {
              $scope.activite = result;
            });
        };
        $scope.load($stateParams.id);
    });
