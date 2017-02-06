(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('SalaDetailController', SalaDetailController);

    SalaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Sala', 'Juego', 'Mensaje', 'Idioma'];

    function SalaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Sala, Juego, Mensaje, Idioma) {
        var vm = this;

        vm.sala = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:salaUpdate', function(event, result) {
            vm.sala = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
