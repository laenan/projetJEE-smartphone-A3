'use strict';

angular.module('puydufouApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('horaire', {
                parent: 'entity',
                url: '/horaire',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'puydufouApp.horaire.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/horaire/horaires.html',
                        controller: 'HoraireController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('horaire');
                        return $translate.refresh();
                    }]
                }
            })
            .state('horaireDetail', {
                parent: 'entity',
                url: '/horaire/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'puydufouApp.horaire.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/horaire/horaire-detail.html',
                        controller: 'HoraireDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('horaire');
                        return $translate.refresh();
                    }]
                }
            });
    });
