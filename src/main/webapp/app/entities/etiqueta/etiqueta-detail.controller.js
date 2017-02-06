(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('EtiquetaDetailController', EtiquetaDetailController);

    EtiquetaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Etiqueta', 'Articulo'];

    function EtiquetaDetailController($scope, $rootScope, $stateParams, previousState, entity, Etiqueta, Articulo) {
        var vm = this;

        vm.etiqueta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:etiquetaUpdate', function(event, result) {
            vm.etiqueta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
