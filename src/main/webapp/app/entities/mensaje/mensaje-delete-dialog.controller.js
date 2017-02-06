(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('MensajeDeleteController',MensajeDeleteController);

    MensajeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mensaje'];

    function MensajeDeleteController($uibModalInstance, entity, Mensaje) {
        var vm = this;

        vm.mensaje = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mensaje.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
