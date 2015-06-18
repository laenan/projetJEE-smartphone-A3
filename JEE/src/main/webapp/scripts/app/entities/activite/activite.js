'use strict';

angular.module('puydufouApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('activite', {
                parent: 'entity',
                url: '/activite',
                data: {
                    roles: [],
                    pageTitle: 'puydufouApp.activite.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/activite/activites.html',
                        controller: 'ActiviteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('activite');
                        return $translate.refresh();
                    }]
                }
            })
            .state('activiteDetail', {
                parent: 'entity',
                url: '/activite/:id',
                data: {
                    roles: [],
                    pageTitle: 'puydufouApp.activite.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/activite/activite-detail.html',
                        controller: 'ActiviteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('activite');
                        return $translate.refresh();
                    }]
                }
            });
    });
