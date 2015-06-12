'use strict';

angular.module('puydufouApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


