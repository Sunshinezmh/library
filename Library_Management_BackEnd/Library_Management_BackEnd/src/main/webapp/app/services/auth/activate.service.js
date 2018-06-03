(function() {
    'use strict';

    angular
        .module('library')
        .factory('Activate', Activate);

    Activate.$inject = ['$resource'];

    function Activate ($resource) {
        var service = $resource('resources/api/activate', {}, {
            'get': { method: 'GET', params: {}, isArray: false}
        });

        return service;
    }
})();
