(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('Juego', Juego);

    Juego.$inject = ['$resource', 'DateUtils'];

    function Juego ($resource, DateUtils) {
        var resourceUrl =  'api/juegos/:id';
        ///juegos/{idJuego}/valoracion/{valoracion}
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'addValoracion': {
                method: 'POST',
                isArray: false,
                url: 'api/juegos/:idJuego/valoracion/:valoracion'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaLanzamiento = DateUtils.convertLocalDateFromServer(data.fechaLanzamiento);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaLanzamiento = DateUtils.convertLocalDateToServer(copy.fechaLanzamiento);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaLanzamiento = DateUtils.convertLocalDateToServer(copy.fechaLanzamiento);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
