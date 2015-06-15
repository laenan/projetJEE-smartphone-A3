'use strict';

angular.module('puydufouApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('type_activite', {
                parent: 'entity',
                url: '/type_activite',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'puydufouApp.type_activite.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/type_activite/type_activites.html',
                        controller: 'Type_activiteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('type_activite');
                        return $translate.refresh();
                    }]
                }
            })
            .state('type_activiteDetail', {
                parent: 'entity',
                url: '/type_activite/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'puydufouApp.type_activite.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/type_activite/type_activite-detail.html',
                        controller: 'Type_activiteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('type_activite');
                        return $translate.refresh();
                    }]
                }
            });
    });
