(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('IdiomaDeleteController',IdiomaDeleteController);

    IdiomaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Idioma'];

    function IdiomaDeleteController($uibModalInstance, entity, Idioma) {
        var vm = this;

        vm.idioma = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Idioma.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
