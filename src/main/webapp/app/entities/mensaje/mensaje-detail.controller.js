(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('MensajeDetailController', MensajeDetailController);

    MensajeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Mensaje', 'Sala', 'User'];

    function MensajeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Mensaje, Sala, User) {
        var vm = this;

        vm.mensaje = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:mensajeUpdate', function(event, result) {
            vm.mensaje = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
