(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('ValoracionJuegoDetailController', ValoracionJuegoDetailController);

    ValoracionJuegoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ValoracionJuego', 'User', 'Juego'];

    function ValoracionJuegoDetailController($scope, $rootScope, $stateParams, previousState, entity, ValoracionJuego, User, Juego) {
        var vm = this;

        vm.valoracionJuego = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:valoracionJuegoUpdate', function(event, result) {
            vm.valoracionJuego = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
