'use strict';

angular.module('puydufouApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
