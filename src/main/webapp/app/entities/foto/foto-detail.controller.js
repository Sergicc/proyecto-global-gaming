(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('FotoDetailController', FotoDetailController);

    FotoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Foto', 'Articulo'];

    function FotoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Foto, Articulo) {
        var vm = this;

        vm.foto = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:fotoUpdate', function(event, result) {
            vm.foto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
