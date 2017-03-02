(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('ValoracionJuego', ValoracionJuego);

    ValoracionJuego.$inject = ['$resource', 'DateUtils'];

    function ValoracionJuego ($resource, DateUtils) {
        var resourceUrl =  'api/valoracion-juegos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.timeStamp = DateUtils.convertDateTimeFromServer(data.timeStamp);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
