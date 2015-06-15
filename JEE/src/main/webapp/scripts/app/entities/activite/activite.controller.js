'use strict';

angular.module('puydufouApp')
    .controller('ActiviteController', function ($scope, Activite, Parc, Note, Type_activite, Horaire, Menu_Restaurant) {
        $scope.activites = [];
        $scope.parcs = Parc.query();
        $scope.notes = Note.query();
        $scope.type_activites = Type_activite.query();
        $scope.horaires = Horaire.query();
        $scope.menu_restaurants = Menu_Restaurant.query();
        $scope.loadAll = function() {
            Activite.query(function(result) {
               $scope.activites = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Activite.get({id: id}, function(result) {
                $scope.activite = result;
                $('#saveActiviteModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.activite.id != null) {
                Activite.update($scope.activite,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Activite.save($scope.activite,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Activite.get({id: id}, function(result) {
                $scope.activite = result;
                $('#deleteActiviteConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Activite.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteActiviteConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveActiviteModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.activite = {nom_activite: null, description_activite: null, duree_activite: null, photo_activite: null, date_creation: null, nb_acteurs: null, evt_historique: null, position: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
