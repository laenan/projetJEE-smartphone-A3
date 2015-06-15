'use strict';

angular.module('puydufouApp')
    .factory('Activite', function ($resource, DateUtils) {
        return $resource('api/activites/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_creation = DateUtils.convertLocaleDateFromServer(data.date_creation);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_creation = DateUtils.convertLocaleDateToServer(data.date_creation);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_creation = DateUtils.convertLocaleDateToServer(data.date_creation);
                    return angular.toJson(data);
                }
            }
        });
    });
