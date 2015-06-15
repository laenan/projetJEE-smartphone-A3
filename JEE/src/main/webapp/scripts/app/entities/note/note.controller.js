'use strict';

angular.module('puydufouApp')
    .controller('NoteController', function ($scope, Note, Activite) {
        $scope.notes = [];
        $scope.activites = Activite.query();
        $scope.loadAll = function() {
            Note.query(function(result) {
               $scope.notes = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Note.get({id: id}, function(result) {
                $scope.note = result;
                $('#saveNoteModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.note.id != null) {
                Note.update($scope.note,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Note.save($scope.note,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Note.get({id: id}, function(result) {
                $scope.note = result;
                $('#deleteNoteConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Note.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteNoteConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveNoteModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.note = {keyAPI: null, note: null, commentaire_note: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
