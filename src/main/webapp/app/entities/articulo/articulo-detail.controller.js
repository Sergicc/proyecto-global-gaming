(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('ArticuloDetailController', ArticuloDetailController);

    ArticuloDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Articulo', 'Etiqueta', 'Foto'];

    function ArticuloDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Articulo, Etiqueta, Foto) {
        var vm = this;

        vm.articulo = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:articuloUpdate', function(event, result) {
            vm.articulo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
