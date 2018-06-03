(function() {
    'use strict';

    angular
        .module('library')
        .factory('PasswordResetFinish', PasswordResetFinish);

    PasswordResetFinish.$inject = ['$resource'];

    function PasswordResetFinish($resource) {
        var service = $resource('resources/api/account/reset_password/finish', {}, {});

        return service;
    }
})();
