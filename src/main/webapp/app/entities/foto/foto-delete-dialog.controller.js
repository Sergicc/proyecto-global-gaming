(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('FotoDeleteController',FotoDeleteController);

    FotoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Foto'];

    function FotoDeleteController($uibModalInstance, entity, Foto) {
        var vm = this;

        vm.foto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Foto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
