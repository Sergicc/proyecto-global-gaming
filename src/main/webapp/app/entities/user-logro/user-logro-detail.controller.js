(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('UserLogroDetailController', UserLogroDetailController);

    UserLogroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserLogro', 'User', 'Logro'];

    function UserLogroDetailController($scope, $rootScope, $stateParams, previousState, entity, UserLogro, User, Logro) {
        var vm = this;

        vm.userLogro = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:userLogroUpdate', function(event, result) {
            vm.userLogro = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
