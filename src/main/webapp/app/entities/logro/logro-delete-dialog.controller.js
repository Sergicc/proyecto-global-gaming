(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('LogroDeleteController',LogroDeleteController);

    LogroDeleteController.$inject = ['$uibModalInstance', 'entity', 'Logro'];

    function LogroDeleteController($uibModalInstance, entity, Logro) {
        var vm = this;

        vm.logro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Logro.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
