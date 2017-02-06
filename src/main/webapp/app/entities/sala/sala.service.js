(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('Sala', Sala);

    Sala.$inject = ['$resource'];

    function Sala ($resource) {
        var resourceUrl =  'api/salas/:id';

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
