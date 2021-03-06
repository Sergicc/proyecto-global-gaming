(function() {
    'use strict';
    angular
        .module('proyectoGlobalGamingApp')
        .factory('UserExt', UserExt);

    UserExt.$inject = ['$resource'];

    function UserExt ($resource) {
        var resourceUrl =  'api/user-exts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'byFilters': { method: 'GET', isArray: true, url: 'api/userext/byfilters'},
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
