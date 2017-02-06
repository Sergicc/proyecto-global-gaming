(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('JuegoDeleteController',JuegoDeleteController);

    JuegoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Juego'];

    function JuegoDeleteController($uibModalInstance, entity, Juego) {
        var vm = this;

        vm.juego = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Juego.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
