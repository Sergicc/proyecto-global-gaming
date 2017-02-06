(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('RelacionUsuariosDeleteController',RelacionUsuariosDeleteController);

    RelacionUsuariosDeleteController.$inject = ['$uibModalInstance', 'entity', 'RelacionUsuarios'];

    function RelacionUsuariosDeleteController($uibModalInstance, entity, RelacionUsuarios) {
        var vm = this;

        vm.relacionUsuarios = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RelacionUsuarios.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
