(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('PaisDeleteController',PaisDeleteController);

    PaisDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pais'];

    function PaisDeleteController($uibModalInstance, entity, Pais) {
        var vm = this;

        vm.pais = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pais.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
