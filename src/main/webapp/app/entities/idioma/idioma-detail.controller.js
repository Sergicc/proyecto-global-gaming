(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('IdiomaDetailController', IdiomaDetailController);

    IdiomaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Idioma', 'Juego', 'Sala'];

    function IdiomaDetailController($scope, $rootScope, $stateParams, previousState, entity, Idioma, Juego, Sala) {
        var vm = this;

        vm.idioma = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:idiomaUpdate', function(event, result) {
            vm.idioma = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
