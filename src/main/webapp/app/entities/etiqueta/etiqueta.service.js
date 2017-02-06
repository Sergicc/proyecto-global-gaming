(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('Etiqueta', Etiqueta);

    Etiqueta.$inject = ['$resource'];

    function Etiqueta ($resource) {
        var resourceUrl =  'api/etiquetas/:id';

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
