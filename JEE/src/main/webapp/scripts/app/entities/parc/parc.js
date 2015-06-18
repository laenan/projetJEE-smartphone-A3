'use strict';

angular.module('puydufouApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('parc', {
                parent: 'entity',
                url: '/parc',
                data: {
                    roles: [],
                    pageTitle: 'puydufouApp.parc.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/parc/parcs.html',
                        controller: 'ParcController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('parc');
                        return $translate.refresh();
                    }]
                }
            })
            .state('parcDetail', {
                parent: 'entity',
                url: '/parc/:id',
                data: {
                    roles: [],
                    pageTitle: 'puydufouApp.parc.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/parc/parc-detail.html',
                        controller: 'ParcDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('parc');
                        return $translate.refresh();
                    }]
                }
            });
    });
