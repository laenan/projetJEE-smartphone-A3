'use strict';

angular.module('puydufouApp')
    .controller('ParcController', function ($scope, Parc, Activite) {
        $scope.parcs = [];
        $scope.activites = Activite.query();
        $scope.loadAll = function() {
            Parc.query(function(result) {
               $scope.parcs = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Parc.get({id: id}, function(result) {
                $scope.parc = result;
                $('#saveParcModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.parc.id != null) {
                Parc.update($scope.parc,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Parc.save($scope.parc,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Parc.get({id: id}, function(result) {
                $scope.parc = result;
                $('#deleteParcConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Parc.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteParcConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveParcModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.parc = {description_parc: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
