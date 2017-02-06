(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('RelacionUsuarios', RelacionUsuarios);

    RelacionUsuarios.$inject = ['$resource', 'DateUtils'];

    function RelacionUsuarios ($resource, DateUtils) {
        var resourceUrl =  'api/relacion-usuarios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaEmision = DateUtils.convertDateTimeFromServer(data.fechaEmision);
                        data.fechaResolucion = DateUtils.convertDateTimeFromServer(data.fechaResolucion);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
