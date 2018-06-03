(function() {
    'use strict';

    angular
        .module('library')
        .controller('JedLanguageController', JedLanguageController);

    JedLanguageController.$inject = ['$translate', 'JedLanguageService', 'tmhDynamicLocale'];

    function JedLanguageController ($translate, JedLanguageService, tmhDynamicLocale) {
        var vm = this;

        vm.changeLanguage = changeLanguage;
        vm.languages = null;

        JedLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function changeLanguage (languageKey) {
            $translate.use(languageKey);
            tmhDynamicLocale.set(languageKey);
        }
    }
})();
