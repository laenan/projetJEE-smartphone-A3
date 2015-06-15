'use strict';

angular.module('puydufouApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('menu_Restaurant', {
                parent: 'entity',
                url: '/menu_Restaurant',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'puydufouApp.menu_Restaurant.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/menu_Restaurant/menu_Restaurants.html',
                        controller: 'Menu_RestaurantController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('menu_Restaurant');
                        return $translate.refresh();
                    }]
                }
            })
            .state('menu_RestaurantDetail', {
                parent: 'entity',
                url: '/menu_Restaurant/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'puydufouApp.menu_Restaurant.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/menu_Restaurant/menu_Restaurant-detail.html',
                        controller: 'Menu_RestaurantDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('menu_Restaurant');
                        return $translate.refresh();
                    }]
                }
            });
    });
