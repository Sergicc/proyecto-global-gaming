(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('LogroDetailController', LogroDetailController);

    LogroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Logro', 'UserLogro'];

    function LogroDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Logro, UserLogro) {
        var vm = this;

        vm.logro = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:logroUpdate', function(event, result) {
            vm.logro = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
