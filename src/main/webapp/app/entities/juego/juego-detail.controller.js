(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('JuegoDetailController', JuegoDetailController);

    JuegoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Juego', 'Sala', 'Idioma'];

    function JuegoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Juego, Sala, Idioma) {
        var vm = this;

        vm.juego = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:juegoUpdate', function(event, result) {
            vm.juego = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
