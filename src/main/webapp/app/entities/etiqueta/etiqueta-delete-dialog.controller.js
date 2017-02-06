(function() {
    'use strict';

    angular
        .module('proyectoGlobalGamingApp')
        .controller('EtiquetaDeleteController',EtiquetaDeleteController);

    EtiquetaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Etiqueta'];

    function EtiquetaDeleteController($uibModalInstance, entity, Etiqueta) {
        var vm = this;

        vm.etiqueta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Etiqueta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
