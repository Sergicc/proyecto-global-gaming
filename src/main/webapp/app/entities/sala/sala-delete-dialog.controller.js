(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('SalaDeleteController',SalaDeleteController);

    SalaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sala'];

    function SalaDeleteController($uibModalInstance, entity, Sala) {
        var vm = this;

        vm.sala = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sala.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
