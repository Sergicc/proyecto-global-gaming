(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('Idioma', Idioma);

    Idioma.$inject = ['$resource'];

    function Idioma ($resource) {
        var resourceUrl =  'api/idiomas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
