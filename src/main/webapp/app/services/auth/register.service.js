(function () {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
