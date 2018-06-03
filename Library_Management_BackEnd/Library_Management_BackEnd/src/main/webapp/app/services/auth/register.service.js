(function () {
    'use strict';

    angular
        .module('library')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('resources/api/register', {}, {});
    }
})();
