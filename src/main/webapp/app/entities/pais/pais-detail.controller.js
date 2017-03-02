(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('PaisDetailController', PaisDetailController);

    PaisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Pais'];

    function PaisDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Pais) {
        var vm = this;

        vm.pais = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('proyectoGlobalGamingApp:paisUpdate', function(event, result) {
            vm.pais = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
