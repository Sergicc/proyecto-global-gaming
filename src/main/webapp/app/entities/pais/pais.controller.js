(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('PaisController', PaisController);

    PaisController.$inject = ['$scope', '$state', 'DataUtils', 'Pais'];

    function PaisController ($scope, $state, DataUtils, Pais) {
        var vm = this;

        vm.pais = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Pais.query(function(result) {
                vm.pais = result;
                vm.searchQuery = null;
            });
        }
    }
})();
