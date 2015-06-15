'use strict';

angular.module('puydufouApp')
    .controller('Menu_RestaurantController', function ($scope, Menu_Restaurant, Activite) {
        $scope.menu_Restaurants = [];
        $scope.activites = Activite.query();
        $scope.loadAll = function() {
            Menu_Restaurant.query(function(result) {
               $scope.menu_Restaurants = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Menu_Restaurant.get({id: id}, function(result) {
                $scope.menu_Restaurant = result;
                $('#saveMenu_RestaurantModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.menu_Restaurant.id != null) {
                Menu_Restaurant.update($scope.menu_Restaurant,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Menu_Restaurant.save($scope.menu_Restaurant,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Menu_Restaurant.get({id: id}, function(result) {
                $scope.menu_Restaurant = result;
                $('#deleteMenu_RestaurantConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Menu_Restaurant.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMenu_RestaurantConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMenu_RestaurantModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.menu_Restaurant = {entree_nom: null, entree_photo: null, plat_nom: null, plat_photo: null, dessert_nom: null, dessert_photo: null, tarif_menu: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
