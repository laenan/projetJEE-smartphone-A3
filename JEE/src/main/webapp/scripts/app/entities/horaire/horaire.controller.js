'use strict';

angular.module('puydufouApp')
    .controller('HoraireController', function ($scope, Horaire, Activite) {
        $scope.horaires = [];
        $scope.activites = Activite.query();
        $scope.loadAll = function() {
            Horaire.query(function(result) {
               $scope.horaires = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Horaire.get({id: id}, function(result) {
                $scope.horaire = result;
                $('#saveHoraireModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.horaire.id != null) {
                Horaire.update($scope.horaire,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Horaire.save($scope.horaire,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Horaire.get({id: id}, function(result) {
                $scope.horaire = result;
                $('#deleteHoraireConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Horaire.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteHoraireConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveHoraireModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.horaire = {date_debut: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
