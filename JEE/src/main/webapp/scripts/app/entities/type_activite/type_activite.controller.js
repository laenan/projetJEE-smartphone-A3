'use strict';

angular.module('puydufouApp')
    .controller('Type_activiteController', function ($scope, Type_activite, Activite) {
        $scope.type_activites = [];
        $scope.activites = Activite.query();
        $scope.loadAll = function() {
            Type_activite.query(function(result) {
               $scope.type_activites = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Type_activite.get({id: id}, function(result) {
                $scope.type_activite = result;
                $('#saveType_activiteModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.type_activite.id != null) {
                Type_activite.update($scope.type_activite,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Type_activite.save($scope.type_activite,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Type_activite.get({id: id}, function(result) {
                $scope.type_activite = result;
                $('#deleteType_activiteConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Type_activite.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteType_activiteConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveType_activiteModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.type_activite = {nom_type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
