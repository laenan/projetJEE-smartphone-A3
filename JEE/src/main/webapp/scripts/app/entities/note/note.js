'use strict';

angular.module('puydufouApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('note', {
                parent: 'entity',
                url: '/note',
                data: {
                    roles: [],
                    pageTitle: 'puydufouApp.note.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/note/notes.html',
                        controller: 'NoteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('note');
                        return $translate.refresh();
                    }]
                }
            })
            .state('noteDetail', {
                parent: 'entity',
                url: '/note/:id',
                data: {
                    roles: [],
                    pageTitle: 'puydufouApp.note.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/note/note-detail.html',
                        controller: 'NoteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('note');
                        return $translate.refresh();
                    }]
                }
            });
    });
