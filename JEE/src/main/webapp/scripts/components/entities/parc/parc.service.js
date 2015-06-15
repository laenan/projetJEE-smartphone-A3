'use strict';

angular.module('puydufouApp')
    .factory('Parc', function ($resource, DateUtils) {
        return $resource('api/parcs/:id', {}, {
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
