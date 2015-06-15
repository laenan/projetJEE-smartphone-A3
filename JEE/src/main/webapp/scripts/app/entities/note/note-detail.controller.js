'use strict';

angular.module('puydufouApp')
    .controller('NoteDetailController', function ($scope, $stateParams, Note, Activite) {
        $scope.note = {};
        $scope.load = function (id) {
            Note.get({id: id}, function(result) {
              $scope.note = result;
            });
        };
        $scope.load($stateParams.id);
    });
