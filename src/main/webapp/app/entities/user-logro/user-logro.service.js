(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('UserLogro', UserLogro);

    UserLogro.$inject = ['$resource', 'DateUtils'];

    function UserLogro ($resource, DateUtils) {
        var resourceUrl =  'api/user-logroes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
