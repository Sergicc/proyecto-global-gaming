(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('ArticuloDeleteController',ArticuloDeleteController);

    ArticuloDeleteController.$inject = ['$uibModalInstance', 'entity', 'Articulo'];

    function ArticuloDeleteController($uibModalInstance, entity, Articulo) {
        var vm = this;

        vm.articulo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Articulo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
