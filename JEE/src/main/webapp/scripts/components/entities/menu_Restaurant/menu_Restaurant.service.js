'use strict';

angular.module('puydufouApp')
    .factory('Menu_Restaurant', function ($resource, DateUtils) {
        return $resource('api/menu_Restaurants/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
