(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('Foto', Foto);

    Foto.$inject = ['$resource', 'DateUtils'];

    function Foto ($resource, DateUtils) {
        var resourceUrl =  'api/fotos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaCreacion = DateUtils.convertDateTimeFromServer(data.fechaCreacion);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
