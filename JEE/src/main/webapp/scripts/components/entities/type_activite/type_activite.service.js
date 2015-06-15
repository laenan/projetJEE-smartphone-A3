'use strict';

angular.module('puydufouApp')
    .factory('Type_activite', function ($resource, DateUtils) {
        return $resource('api/type_activites/:id', {}, {
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
