(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('RelacionUsuariosDetailController', RelacionUsuariosDetailController);

    RelacionUsuariosDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RelacionUsuarios', 'User'];

    function RelacionUsuariosDetailController($scope, $rootScope, $stateParams, previousState, entity, RelacionUsuarios, User) {
        var vm = this;

        vm.relacionUsuarios = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:relacionUsuariosUpdate', function(event, result) {
            vm.relacionUsuarios = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
