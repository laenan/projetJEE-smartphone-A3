'use strict';

angular.module('puydufouApp')
    .factory('Horaire', function ($resource, DateUtils) {
        return $resource('api/horaires/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_debut = DateUtils.convertLocaleDateFromServer(data.date_debut);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_debut = DateUtils.convertLocaleDateToServer(data.date_debut);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_debut = DateUtils.convertLocaleDateToServer(data.date_debut);
                    return angular.toJson(data);
                }
            }
        });
    });
