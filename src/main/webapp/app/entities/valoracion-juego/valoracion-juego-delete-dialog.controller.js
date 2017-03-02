(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('ValoracionJuegoDeleteController',ValoracionJuegoDeleteController);

    ValoracionJuegoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ValoracionJuego'];

    function ValoracionJuegoDeleteController($uibModalInstance, entity, ValoracionJuego) {
        var vm = this;

        vm.valoracionJuego = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ValoracionJuego.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
