(function () {
    'use strict';

    angular
        .module('library')
        .factory('JedLanguageService', JedLanguageService);

    JedLanguageService.$inject = ['$q', '$http', '$translate', 'LANGUAGES'];

    function JedLanguageService ($q, $http, $translate, LANGUAGES) {
        var service = {
            getAll: getAll,
            getCurrent: getCurrent
        };

        return service;

        function getAll () {
            var deferred = $q.defer();
            deferred.resolve(LANGUAGES);
            return deferred.promise;
        }

        function getCurrent () {
            var deferred = $q.defer();
            var language = $translate.storage().get('NG_TRANSLATE_LANG_KEY');

            deferred.resolve(language);

            return deferred.promise;
        }
    }
})();
