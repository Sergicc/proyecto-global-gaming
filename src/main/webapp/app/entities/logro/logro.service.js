(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('Logro', Logro);

    Logro.$inject = ['$resource'];

    function Logro ($resource) {
        var resourceUrl =  'api/logroes/:id';

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
