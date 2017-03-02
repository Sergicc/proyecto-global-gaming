(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('Pais', Pais);

    Pais.$inject = ['$resource'];

    function Pais ($resource) {
        var resourceUrl =  'api/pais/:id';

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
